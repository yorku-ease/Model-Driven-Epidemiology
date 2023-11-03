import sys
sys.path.append('..')
from parameters_setting import *
from mycsv import CSV
import read_cchs # will create cchs-out.csv if cchs1.csv is found

# parameterization of the model following https://www.cmaj.ca/content/cmaj/suppl/2020/04/08/cmaj.200476.DC1/200476-res-1-at.pdf

def get_pop_comorbidity_contact():
        
    def can_gov_ages_to_range(ages):
        if 'over' in ages or 'older' in ages:
            return [int(ages.split(' ')[0]), 0]
        else:
            ages = ages.split('years')[0].split(' to ')
            return [int(ages[0]), int(ages[1])]

    def polymod_age_to_range(ages):
        if '+' in ages:
            return [int(ages.split('+')[0]), 0]
        else:
            ages = ages.split('-')
            return [int(ages[0]), int(ages[1])]

    # https://www150.statcan.gc.ca/n1/en/pub/91-215-x/91-215-x2019001-eng.pdf?st=QscDWW2B
    pop_csv = CSV('pop-2019.csv', ',', [can_gov_ages_to_range, int])

    # https://www150.statcan.gc.ca/n1/en/catalogue/82M0013X2016001
    comorbidity_csv = CSV('cchs-out.csv', ',', [can_gov_ages_to_range, float]) 

    # https://github.com/epiforecasts/socialmixr/issues/1
    # we transpose it to access by column since the proposed layout is by row
    # using transpose csv[age] = contacts of people of that age
    contact_csv = CSV('polymod_matrix_t.csv', ',', [float] * 15)
    contact_csv.headers=list(map(polymod_age_to_range, contact_csv.headers))

    # now we need to reshape the data into the same groups used by the researchers
    # "The population was divided into 5-year age groups using 2019 population estimates for Ontario
    #  (Statistics Canada 2019). The last age group (i = 16) included the population aged 75 and older."

    # first lets do the contacts matrix
    # we need to:
    #   1. rename 70+ to 70-74
    contact_csv.headers[-1] = [70, 74]
    #   2. add column 75+
    contact_csv.headers += [[75, 0]]
    for row in contact_csv.data:
        row += [0]
    #   3. add row 75+, duplicate the contacts from 70+ to 75+ (we will simply assume they follow the same contact pattern)
    contact_csv.data += [list(contact_csv.data[-1])]
    #   5. divide contacts into the last column using the correct population ratio
    counts_70_plus = list(pop_csv.get_simple('count'))[-7:]
    p_70_75_given_over_70 = counts_70_plus[0] / sum(counts_70_plus)
    for row in contact_csv.data:
        row[-2]
        row[-1]
        row[-2], row[-1] = (p_70_75_given_over_70 * row[-2], (1 - p_70_75_given_over_70) * row[-2])

    # then we will do the comorbidity table
    # we need a weighted average of comorbidity for our 2 data points being 75 to 80 and 80+
    # p(comorbidity given 75+) = (p(comorbidity given 80+) * p(80+) + p(comorbidity given 75-80) * p(75-80))/p(75+)
    p_comorbidity_given_80_plus = comorbidity_csv.data[-1][1]
    p_comorbidity_given_75_80 = comorbidity_csv.data[-2][1]
    # we will use absolute values instead of probabilities for population of a certain age, as it cancels out
    p_80_plus = sum(list(pop_csv.get_simple('count'))[-6:])
    p_75_80 = list(pop_csv.get_simple('count'))[-6]
    p_comorbidity_given_75_plus = (p_comorbidity_given_80_plus * p_80_plus + p_comorbidity_given_75_80 * p_75_80)/(p_75_80 + p_80_plus)
    # change 75-79 to 75+, set new value, remove 80+
    comorbidity_csv.data = comorbidity_csv.data[:-2] + [[[75, 0], p_comorbidity_given_75_plus]]
    # and then we need to merge 15-17 and 18-19, we will average since we don't have fine grained population data
    comorbidity_csv.data = comorbidity_csv.data[:1] +\
        [[[15, 19], (comorbidity_csv.data[1][1] + comorbidity_csv.data[2][1]) / 2]] +\
        comorbidity_csv.data[3:]
    # then we need to use data from another source:
    # "For younger age groups (< 12 yr), we used estimates from Moran and colleagues"
    # they produced: Prevalence of chronic conditions (2–11 years): Present(weighted) = 10.7%
    # we will use this value for ages 0-4, 5-9 and average it with our current data at 12-14 for 10-14
    comorbidity_csv.data = [[[0, 4], 0.107], [[5, 9], 0.107],
        [[10, 14], 0.107 * 0.4 + comorbidity_csv.data[0][1] * 0.6]
    ] + comorbidity_csv.data[1:]


    # finally we will do the population table
    count_75_plus = sum(list(pop_csv.get_simple('count'))[-6:])
    pop_csv.data = pop_csv.data[:-6]
    pop_csv.data += [[[75, 0], count_75_plus]]

    print(contact_csv)
    print(comorbidity_csv)
    print(pop_csv)

    return pop_csv, comorbidity_csv, contact_csv

def setup_compartments(scenario, pop: CSV, comorbidity: CSV):
    ages = list(pop.get_simple('age'))
    if ages != list(comorbidity.get_simple('age')):
        raise Exception("invalid data")
    went_through = False
    target = '25-29'
    for age, pop, p_comorbidity in zip(ages, pop.get_simple('count'), comorbidity.get_simple('p-comorbidity')):
        age = str(age[0]) + (('-' + str(age[1])) if age[1] > 0 else '+')
        print(age, pop, p_comorbidity)

        # 1 person aged 25 to 29 without comorbidity will be infected
        # could not find in the article what the researchers did to start the epidemic
        if age == target:
            went_through = True
            print(age)
            select(scenario, [
                select_scenario_compartment_contains(age, 'None', 'S')
            ], 1).set(pop * (1 - p_comorbidity) - 1) # remove one here
            select(scenario, [
                select_scenario_compartment_contains(age, 'None', 'I', 'not-isolated',  'mild')
            ], 1).set(1) # place 1 here
        else:
            select(scenario, [
                select_scenario_compartment_contains(age, 'None', 'S')
            ], 1).set(pop * (1 - p_comorbidity))
        
        select(scenario, [
            select_scenario_compartment_contains(age, 'Some', 'S')
        ], 1).set(pop * p_comorbidity)

    if not went_through:
        raise Exception(f"Data format must have changed, missing age `{target}`")

def setup_parameters_testing(parameters):
    select(parameters, [
        select_parameter_string_equals(1, 'testing')
    ], N_AGE_GROUPS * N_COMORBIDITY * N_EI).set(0)

def setup_parameters_symptoms(parameters):
    select(parameters, [
        select_parameter_string_equals(1, 'mild-symptoms')
    ], N_AGE_GROUPS * N_COMORBIDITY * N_ISO).set(0)
    select(parameters, [
        select_parameter_string_equals(1, 'severe-symptoms')
    ], N_AGE_GROUPS * N_COMORBIDITY * N_ISO).set(0)

def setup_parameters_infection(parameters):
    select(parameters, [
        select_parameter_string_equals(1, 'infection')
    ], N_AGE_GROUPS * N_COMORBIDITY * N_ISO).set(0)

def setup_parameters_icu(parameters):
    select(parameters, [
        select_parameter_string_equals(1, 'enter-icu')
    ], N_AGE_GROUPS * N_COMORBIDITY).set(0)
    select(parameters, [
        select_parameter_string_equals(1, 'exit-icu')
    ], N_AGE_GROUPS * N_COMORBIDITY).set(0)

def setup_parameters_exposure(parameters, pop: CSV, comorbidity: CSV, contact_mixing: CSV):
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

    ages = list(pop.get_simple('age'))
    if ages != list(contact_mixing.headers):
        raise Exception("invalid data")
    if ages != list(comorbidity.get_simple('age')):
        raise Exception("invalid data")

    age2str = lambda age: f'{age[0]}-{age[1]}' if age[1] != 0 else f'{age[0]}+'

    p_comorbidity = list(comorbidity.get_simple('p-comorbidity'))
    population_counts = list(pop.get_simple('count'))

    for age_of_exposed, comorbidity_of_exposed in zip(ages, p_comorbidity):
        contacts = contact_mixing.get_simple(age_of_exposed)
        for age_of_contact, population_of_contact, comorbidity_of_contact, contacts_per_day in zip(ages, population_counts, p_comorbidity, contacts):
            base_criteria = [
                select_parameter_string_equals(0, 'contact-mixing'),
                select_parameter_string_equals(1, 'exposure'),
                select_parameter_string_contains(2, age2str(age_of_exposed) + '-'),
                select_parameter_compartment_contains(3, age2str(age_of_contact)),
            ]
            select(
                parameters,
                base_criteria,
                (N_COMORBIDITY ** 2) * N_ISO * N_SYMPTOMS_OR_PRE
            ).set(contacts_per_day / population_of_contact)
            # weight for absence and presence of comorbidity of exposed and contact
            # select(
            #     parameters,
            #     base_criteria + [select_parameter_string_contains(2, 'Some')],
            #     N_COMORBIDITY * N_ISO * N_SYMPTOMS_OR_PRE
            # ).multiply(comorbidity_of_exposed)
            # select(
            #     parameters,
            #     base_criteria + [select_parameter_string_contains(2, 'None')],
            #     N_COMORBIDITY * N_ISO * N_SYMPTOMS_OR_PRE
            # ).multiply(1 - comorbidity_of_exposed)
            # select(
            #     parameters,
            #     base_criteria + [select_parameter_compartment_contains(3, 'Some')],
            #     N_COMORBIDITY * N_ISO * N_SYMPTOMS_OR_PRE
            # ).multiply(comorbidity_of_contact)
            # select(
            #     parameters,
            #     base_criteria + [select_parameter_compartment_contains(3, 'None')],
            #     N_COMORBIDITY * N_ISO * N_SYMPTOMS_OR_PRE
            # ).multiply(1 - comorbidity_of_contact)

def setup_parameters_hospitalization(parameters):
    select(parameters, [
        select_parameter_string_equals(1, 'hospitalization')
    ], N_AGE_GROUPS * N_COMORBIDITY * N_ISO * N_BRANCH_ICU).set(0)

def setup_parameters_death(parameters):
    select(parameters, [
        select_parameter_string_equals(1, 'death')
    ], N_AGE_GROUPS * N_COMORBIDITY).set(0)

def setup_parameters_recovery(parameters):
    select(parameters, [
        select_parameter_string_equals(1, 'recovery-h')
    ], N_AGE_GROUPS * N_COMORBIDITY * N_BRANCH_ICU).set(0)
    select(parameters, [
        select_parameter_string_equals(1, 'recovery')
    ], N_AGE_GROUPS * N_COMORBIDITY * N_SYMPTOMS).set(0)

def setup_parameters_aging(parameters):
    select(parameters, [
        select_parameter_string_equals(1, 'Aging')
    ], 1).set(0)
    select(parameters, [
        select_parameter_string_equals(0, 'Aging')
    ], (N_AGE_GROUPS - 1) * N_COMORBIDITY * (N_S + N_ISO * N_EI + N_HOSPITALIZED + N_DEATH + N_R)).set(0)


N_AGE_GROUPS = 16
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
    compartments, _ = identify_required_parameters(folder, project_name, parameters)
    scenario = create_initial_conditions(folder, project_name, compartments)

    pop_csv, comorbidity_csv, contact_csv = get_pop_comorbidity_contact()

    setup_compartments(scenario, pop_csv, comorbidity_csv)
    save_scenario(folder + project_name, scenario)

    setup_parameters_testing(parameters)
    setup_parameters_symptoms(parameters)
    setup_parameters_infection(parameters)
    setup_parameters_icu(parameters)
    setup_parameters_exposure(parameters, pop_csv, comorbidity_csv, contact_csv)
    setup_parameters_hospitalization(parameters)
    setup_parameters_death(parameters)
    setup_parameters_recovery(parameters)
    setup_parameters_aging(parameters)

    s = select(parameters, [], len(parameters))
    m = s.get_missing()
    if len(m) != 0:
        raise Exception(f"Missing {len(m)} Parameters, example: " + str(m[0]))
    
    save_parameters(folder + project_name, parameters)

if __name__ == "__main__": main()
