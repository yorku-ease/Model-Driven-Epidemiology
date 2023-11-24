import sys
sys.path.append('..')
from parameters_setting import *
import params

# parameterization of the model following https://www.cmaj.ca/content/cmaj/suppl/2020/04/08/cmaj.200476.DC1/200476-res-1-at.pdf

def setup_compartments(scenario):
    pop = params.our_initial_population
    select(scenario, [], len(scenario)).set_array(pop.reshape((len(scenario),)))

#not used
def setup_parameters_testing(parameters):
    select(parameters, [
        select_parameter_string_equals(1, 'testing')
    ], N_AGE_GROUPS * N_COMORBIDITY * N_EI).set(0)

def setup_parameters_symptoms(parameters):
    psev = params.p_severe / params.inf_period_presymp
    pmild = (np.ones(32) - params.p_severe) / params.inf_period_presymp
    for iso in ['isolated', 'not-isolated']:
        select(parameters, [
            select_parameter_string_equals(1, 'symptoms'),
            select_parameter_string_equals(0, 'severe-symptoms-parameter'),
            select_parameter_compartment_contains(2, iso),
        ], N_AGE_GROUPS * N_COMORBIDITY).set_array(psev)
        select(parameters, [
            select_parameter_string_equals(1, 'symptoms'),
            select_parameter_string_equals(0, 'mild-symptoms-parameter'),
            select_parameter_compartment_contains(2, iso),
        ], N_AGE_GROUPS * N_COMORBIDITY).set_array(pmild)

def setup_parameters_infection(parameters):
    select(parameters, [
        select_parameter_string_equals(1, 'infection')
    ], N_AGE_GROUPS * N_COMORBIDITY * N_ISO).set(1 / params.latent_period)

def setup_parameters_icu(parameters):
    select(parameters, [
        select_parameter_string_equals(1, 'enter-icu')
    ], N_AGE_GROUPS * N_COMORBIDITY).set(1 / params.icu_a_dur)
    select(parameters, [
        select_parameter_string_equals(1, 'exit-icu')
    ], N_AGE_GROUPS * N_COMORBIDITY).set(1 / params.icu_b_dur)

def setup_parameters_exposure(parameters):
    '''
    https://www.cmaj.ca/content/cmaj/suppl/2020/04/08/cmaj.200476.DC1/200476-res-1-at.pdf:

    The rate at which susceptible individuals are infected (λi,j(t)) depends on the number of daily contacts
    (cijkl), the transmission probability (β), volatility in the transmission parameter (ω(t)), the reduction in
    transmission associated with isolation and quarantine (rri), and the reduction in contacts associated with
    social distancing (rrci), where i and k represent the age groups, and j and l the health status groups of the
    susceptible and infectious populations, respectively:

    λi,j(t) = βω(t) * sum[k=1,16](
        cik * rrci * (Ak,l + Bk,l + Ck,l + rri(Gk,l + Wk,l + Yk,l + Zk,l) / Nk,l
    )

    infectious = pre, mild, severe
    [Ak,l + Bk,l + Ck,l] = Infectious not isolated
    [Gk,l + Wk,l + Yk,l + Zk,l] = Infectious isolated
        * note that we assumed that G is the same compartment as Y when reproducing the model
        * i believe the distinction is not in their behavior but rather their previous compartment
        * so by separating G and Y they can see proportions of infected people that were quarantined
        * vs non quarantined
    '''

    select(parameters, [
        select_parameter_string_equals(0, 'contagiousness'),
        select_parameter_string_equals(1, 'exposure')
    ], N_AGE_GROUPS * N_COMORBIDITY * N_ISO * N_SYMPTOMS_OR_PRE).set(1)
    select(parameters, [
        select_parameter_string_equals(0, 'susceptibility'),
        select_parameter_string_equals(1, 'exposure')
    ], N_AGE_GROUPS * N_COMORBIDITY).set(1)
    select(parameters, [
        select_parameter_string_equals(0, 'p_quarantine'),
        select_parameter_string_equals(1, 'exposure'),
        select_parameter_compartment_contains(2, 'isolated')
    ], N_AGE_GROUPS * N_COMORBIDITY).set(params.p_quarantine_e)
    select(parameters, [
        select_parameter_string_equals(0, 'p_quarantine'),
        select_parameter_string_equals(1, 'exposure'),
        select_parameter_compartment_contains(2, 'not-isolated')
    ], N_AGE_GROUPS * N_COMORBIDITY).set(1 - params.p_quarantine_e)

    for i, como in enumerate(['None', 'Some']):
        for j, age in enumerate(AGE_GROUPS):
            base_criteria = [
                select_parameter_string_equals(0, 'contact-mixing'),
                select_parameter_string_equals(1, 'exposure'),
                select_parameter_string_contains(2, age + '-'),
                select_parameter_string_contains(2, como),
            ]
            u = params.cm[N_AGE_GROUPS * i + j] * params.beta / np.sum(params.initial_population, axis=0)
            # the contact mixing parameter is the same for the three infectious types and divides by 10 if isolated
            for t in ['presym', 'mild', 'severe']:
                select(
                    parameters,
                    base_criteria + [
                        select_parameter_compartment_contains(3, 'not-isolated'),
                        select_parameter_compartment_contains(3, t)
                    ],
                    N_COMPARTMENTS_BEFORE_STRATIFICATION * N_COMORBIDITY
                ).set_array(u)
                select(
                    parameters,
                    base_criteria + [
                        select_parameter_compartment_contains(3, 'isolated'),
                        select_parameter_compartment_contains(3, t)
                    ],
                    N_COMPARTMENTS_BEFORE_STRATIFICATION * N_COMORBIDITY
                ).set_array(u / 10)

def setup_parameters_hospitalization(parameters):
    # There is a bug in epimodel!
    # There should be twice as many equations here
    # AKA 1 more multiplication by N_ISO
    # iso/not-iso is a group in a product
    # and by pointing from outside the product directly to "severe"
    # which is inside a group adjacent to the iso group
    # it seems to not stratify using the product
    p_icu = params.p_icu
    p_no_icu = np.ones(32) - p_icu
    select(parameters, [
        select_parameter_string_equals(0, 'hospitalization-parameter')
    ], N_AGE_GROUPS * N_COMORBIDITY * N_ISO).set(1 / params.inf_period_severe)
    select(parameters, [
        select_parameter_string_equals(0, 'p-icu'),
        select_parameter_compartment_contains(2, 'no-icu')
    ], N_AGE_GROUPS * N_COMORBIDITY).set_array(p_no_icu)
    select(parameters, [
        select_parameter_string_equals(0, 'p-icu'),
        select_parameter_compartment_contains(2, 'pre-icu')
    ], N_AGE_GROUPS * N_COMORBIDITY).set_array(p_icu)

def setup_parameters_death(parameters):
    select(parameters, [
        select_parameter_string_equals(1, 'death')
    ], N_AGE_GROUPS * N_COMORBIDITY).set(0)

def setup_parameters_recovery(parameters):
    select(parameters, [
        select_parameter_string_equals(1, 'recovery-h'),
        select_parameter_compartment_contains(2, 'no-icu')
    ], N_AGE_GROUPS * N_COMORBIDITY).set(1 / params.hosp_dur)
    select(parameters, [
        select_parameter_string_equals(1, 'recovery-h'),
        select_parameter_compartment_contains(2, 'post-icu')
    ], N_AGE_GROUPS * N_COMORBIDITY).set(1 / params.icu_c_dur)
    select(parameters, [
        select_parameter_string_equals(1, 'recovery')
    ], N_AGE_GROUPS * N_COMORBIDITY * N_SYMPTOMS).set(1 / params.inf_period_mild)

def setup_parameters_aging(parameters):
    coefs_none = [
    0.0005479452,
    0.0005222729,
    0.0005064852,
    0.0004759807,
    0.0004011662,
    0.0003803185,
    0.0004060783,
    0.0004450018,
    0.0004837521,
    0.0005303459,
    0.0005156689,
    0.0005355185,
    0.0007465564,
    0.001028422,
    0.001470658,
    # -0.0009813918,
    ]
    coefs_some = [
    0.0005479452,
    0.0005222684,
    0.0005064831,
    0.0003783264,
    0.0002904377,
    0.0003763754,
    0.0003227668,
    0.0002584941,
    0.000239285,
    0.0001586431,
    0.0001421318,
    0.0001060358,
    9.273183e-05,
    9.880281e-05,
    0.0001061808,
    # -5.899185e-05
    ]
    
    if len(AGE_GROUPS) - 1 != len(coefs_none) or len(AGE_GROUPS) - 1 != len(coefs_some):
        raise Exception(f"lengths were {len(AGE_GROUPS)}, {len(coefs_none)}, {len(coefs_some)}")
    for age, coef_none, coef_some in zip(AGE_GROUPS, coefs_none, coefs_some):
        select(parameters, [
            select_parameter_string_equals(1, 'Aging'),
        ], 1).set(1)
        select(parameters, [
            select_parameter_string_equals(0, 'Aging'),
            select_parameter_compartment_contains(1, 'None'),
            select_parameter_compartment_contains(1, age),
        ], N_COMPARTMENTS_BEFORE_STRATIFICATION).set(coef_none)
        select(parameters, [
            select_parameter_string_equals(0, 'Aging'),
            select_parameter_compartment_contains(1, 'Some'),
            select_parameter_compartment_contains(1, age),
        ], N_COMPARTMENTS_BEFORE_STRATIFICATION).set(coef_some)

AGE_GROUPS = ['0-4', '5-9', '10-14', '15-19', '20-24', '25-29', '30-34', '35-39', '40-44', '45-49', '50-54', '55-59', '60-64', '65-69', '70-74', '75+']
N_COMPARTMENTS_BEFORE_STRATIFICATION = 16
N_AGE_GROUPS = len(AGE_GROUPS)
N_COMORBIDITY = 2
N_ISO = 2
N_SYMPTOMS = 2
N_SYMPTOMS_OR_PRE = N_SYMPTOMS + 1
N_EI = 4 # E + presym + mild + severe
N_BRANCH_ICU = 2 # no icu or icu
N_HOSPITALIZED = 4 # no icu, pre, icu, post
N_DEATH = N_S = N_R = 1

def main():
    project_name = 'Tuite-Covid-Model-Stratified'
    folder = f'C:/Users/Bruno/Desktop/Model-Driven-Epidemiology/compiled_models/{project_name}/'
    parameters = {}
    compartments, flows = identify_required_parameters(folder, project_name, parameters)
    scenario = create_initial_conditions(folder, project_name, compartments)

    setup_compartments(scenario)
    save_scenario(folder + project_name, scenario)

    setup_parameters_testing(parameters) # not used
    setup_parameters_symptoms(parameters)
    setup_parameters_infection(parameters)
    setup_parameters_icu(parameters)
    setup_parameters_exposure(parameters)
    setup_parameters_hospitalization(parameters)
    setup_parameters_death(parameters)
    setup_parameters_recovery(parameters)
    setup_parameters_aging(parameters)

    # s = select(parameters, [], len(parameters))
    # m = s.get_missing()
    # if len(m) != 0:
    #     raise Exception(f"Missing {len(m)} Parameters, example: " + str(m[0]))
    
    save_parameters(folder + project_name, parameters)

if __name__ == "__main__":
    main()
