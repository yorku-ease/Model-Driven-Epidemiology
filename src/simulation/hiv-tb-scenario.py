# # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #

# Script used to setup parameters for the HIV-TB coinfection model
# See main for the overview of the steps

# # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #

from parameters_setting import *

def setup_compartments(scenario):
    select(scenario, criteria = []).set(0)

    # population of 38 million, remove 60k HIV and 1k TB
    N_NOT_INFECTED = TOTAL_POP - 61000
    # each of these .set(...) include a division by 2 for Male and Female
    susceptible_to_both_child = select(scenario, criteria = [
        select_scenario_compartment_contains('Alive', 'S-HIV', 'S-TB', 'Child'),
    ]).set(N_NOT_INFECTED * CHILD_POP / TOTAL_POP / 2)
    susceptible_to_both_adult1 = select(scenario, criteria = [
        select_scenario_compartment_contains('Alive', 'S-HIV', 'S-TB', 'Adult-18-to-50'),
    ]).set(N_NOT_INFECTED * ADULT_18_TO_50_POP / TOTAL_POP / 2)
    susceptible_to_both_adult2 = select(scenario, criteria = [
        select_scenario_compartment_contains('Alive', 'S-HIV', 'S-TB', 'Adult-51+'),
    ]).set(N_NOT_INFECTED * ADULT_51_POP / TOTAL_POP / 2)
    # 8 = M/F * Age
    assert susceptible_to_both_child == susceptible_to_both_adult1 == susceptible_to_both_adult2 == (0, 2)

    # 45k adult men will have HIV, 44k under treatment
    hiv_men_treatment = select(scenario, criteria = [
        select_scenario_compartment_contains('Alive', 'Under-Treatment-HIV', 'S-TB', 'Adult-18-to-50', 'M')
    ]).set(44000)
    print(f'{hiv_men_treatment=}')
    assert hiv_men_treatment == (0, 1)

    hiv_men = select(scenario, criteria = [
        select_scenario_compartment_contains('Alive', 'I-HIV', 'S-TB', 'Adult-18-to-50', 'M')
    ]).set(1000)
    print(f'{hiv_men=}')
    assert hiv_men == (0, 1)

    # 15k adult women will have HIV, 14.5k under treatment
    hiv_women_treatment = select(scenario, criteria = [
        select_scenario_compartment_contains('Alive', 'Under-Treatment-HIV', 'S-TB', 'Adult-18-to-50', 'F')
    ]).set(14500)
    print(f'{hiv_women_treatment=}')
    assert hiv_women_treatment == (0, 1)

    hiv_women = select(scenario, criteria = [
        select_scenario_compartment_contains('Alive', 'I-HIV', 'S-TB', 'Adult-18-to-50', 'F')
    ]).set(500)
    print(f'{hiv_women=}')
    assert hiv_women == (0, 1)

    # now we infect 1k with tuberculosis, 500 men and 500 women
    tb = select(scenario, criteria = [
        select_scenario_compartment_contains('Alive', 'I-TB', 'S-HIV', 'Adult-18-to-50')
    ]).set(500)
    print(f'{tb=}')
    assert tb == (0, 2)

    # make sure we have a population of 38.25 million
    pop = sum(v.value for _, v in scenario.items())
    assert pop == TOTAL_POP

def setup_parameters_tb_exposure(parameters):
    # the exposure equation is defined as such:
    # for each dimension orthogonal to the disease dimension there is a flow
    # from S_i to E_i = normalizing * susceptibility_i * sumproduct(`contagiousness_j, I_j, contact-mixing_i_j` for each infectious compartment I_j)

    # do think of:
    # ignoring infectious diseased people
    # different susceptibility or infectiousness by age
    # different contact-mixing by age, sex, infectiousness (people who know they have HIV for example might avoid contact), etc
    # contact-mixing_i_j is expected to equal contact-mixing_j_i

    # normalize by dividing by the total population, simplifying the other parameters
    # following parameters will offer variations based on other aspects of the susceptible and infectious characteristics
    parameters['(parameter normalizing Exposure-TB)'].value = 1 / TOTAL_POP

    select(
        parameters,
        criteria = [
            select_parameter_string_equals(0, 'contact-mixing-tb'),
        ]
    ).set(10) # each person with untreated HIV will contact around 10 people per day

    select(
        parameters,
        criteria = [
            select_parameter_string_equals(0, 'contact-mixing-tb'),
            select_parameter_compartment_contains(3, 'Deceased'),
        ]
    ).set(0) # nobody meets dead people

    select(
        parameters,
        criteria = [
            select_parameter_string_equals(0, 'contagiousness-tb'),
        ]
    ).set(0.1) # 10% probability to expose someone you meet, independent of characteristics

    select(
        parameters,
        criteria = [
            select_parameter_string_equals(0, 'susceptibility-tb'),
        ]
    ).set(0.1) # 10 % probability of TB exposure, independent of characteristics

    select(
        parameters,
        criteria = [
            select_parameter_string_equals(0, 'susceptibility-tb'),
            select_parameter_compartment_contains(2, 'I-HIV'),
        ]
    ).set(1) # 100 % probability of TB exposure if you have HIV


def setup_parameters_tb(parameters):
    setup_parameters_tb_exposure(parameters)
    parameters['(parameter normalizing Infection-TB)'].value = 1 # assume every exposed person becomes infected instantly
    parameters['(parameter normalizing immunity-TB)'].value = 5e-2 # assume 5% daily probability to go from I to R

def setup_parameters_hiv_exposure(parameters):
    # the exposure equation is defined as such:
    # for each dimension orthogonal to the disease dimension there is a flow
    # from S_i to E_i = normalizing * susceptibility_i * sumproduct(`contagiousness_j, I_j, contact-mixing_i_j` for each infectious compartment I_j)

    # do think of:
    # ignoring infectious diseased people
    # different susceptibility or infectiousness by age? unlike TB we will assume otherwise
    # different contact-mixing by age, sex, infectiousness (people who know they have HIV for example might avoid contact until treatment), etc
    # contact-mixing_i_j is expected to equal contact-mixing_j_i

    # start by dividing the parameter by 100 * 365 so we can express future values in percentage per year
    parameters['(parameter normalizing Exposure-HIV)'].value = 1 / 100 / 365
    
    select(
        parameters,
        criteria = [
            select_parameter_string_equals(0, 'contact-mixing-hiv'),
        ]
    ).set(0)

    select(
        parameters,
        criteria = [
            select_parameter_string_equals(0, 'contact-mixing-hiv'),
            select_parameter_string_contains(2, 'Adult-51+'),
            select_parameter_compartment_contains(3, 'Adult-51+'),
        ]
    ).set(1 / ADULT_51_POP)

    select(
        parameters,
        criteria = [
            select_parameter_string_equals(0, 'contact-mixing-hiv'),
            select_parameter_string_contains(2, 'Adult-51+'),
            select_parameter_compartment_contains(3, 'Adult-18-to-50'),
        ]
    ).set(1 / ADULT_18_TO_50_POP / 10)

    select(
        parameters,
        criteria = [
            select_parameter_string_equals(0, 'contact-mixing-hiv'),
            select_parameter_string_contains(2, 'Adult-18-to-50'),
            select_parameter_compartment_contains(3, 'Adult-18-to-50'),
        ]
    ).set(1 / ADULT_18_TO_50_POP)

    select(
        parameters,
        criteria = [
            select_parameter_string_equals(0, 'contact-mixing-hiv'),
            select_parameter_string_contains(2, 'Adult-18-to-50'),
            select_parameter_compartment_contains(3, 'Adult-51+'),
        ]
    ).set(1 / ADULT_51_POP / 10)

    select(
        parameters,
        criteria = [
            select_parameter_string_equals(0, 'contact-mixing-hiv'),
            select_parameter_compartment_contains(3, 'Deceased'),
        ]
    ).set(0) # nobody meets dead people

    select(
        parameters,
        criteria = [
            select_parameter_string_equals(0, 'contagiousness-hiv'),
        ]
    ).set(1)

    select(
        parameters,
        criteria = [
            select_parameter_string_equals(0, 'susceptibility-hiv'),
        ]
    ).set(1)



def setup_parameters_hiv(parameters):
    setup_parameters_hiv_exposure(parameters)
    parameters['(parameter normalizing Infection-HIV)'].value = 1 # every exposed person will become infected
    parameters['(parameter normalizing Treatment-HIV)'].value = 1/365 # takes around 1 year for someone infected with HIV to get treatment
    parameters['(parameter normalizing Waning-HIV)'].value = 0 # everyone stays under treatment



def setup_parameters_mortality(parameters):
    parameters['(parameter normalizing Death)'].value = 1

    select(
        parameters,
        criteria = [
            select_parameter_string_equals(0, 'death-coef'),
        ],
        expected = 128
    ).set(1e-5) # 0.001% probability to die per day

    select(
        parameters,
        criteria = [
            select_parameter_string_equals(0, 'death-coef'),
            select_parameter_compartment_contains(2, 'Adult-51+'),
        ]
    ).set(1e-4) # 0.01% probability to die per day if you are 51+

    select(
        parameters,
        criteria = [
            select_parameter_string_equals(0, 'death-coef'),
            select_parameter_compartment_does_not_contain(2, 'I-HIV'),
            select_parameter_compartment_contains(2, 'I-TB'),
        ]
    ).multiply(1e2) # 100 times more likely to die on a given day if you have TB but not HIV

    select(
        parameters,
        criteria = [
            select_parameter_string_equals(0, 'death-coef'),
            select_parameter_compartment_contains(2, 'I-HIV'),
            select_parameter_compartment_does_not_contain(2, 'I-TB'),
        ]
    ).multiply(1e2) # 100 times more likely to die on a given day if you have HIV but not TB

    select(
        parameters,
        criteria = [
            select_parameter_string_equals(0, 'death-coef'),
            select_parameter_compartment_contains(2, 'I-HIV'),
            select_parameter_compartment_contains(2, 'I-TB'),
        ]
    ).multiply(1e3) # 1000 times more likely to die on a given day if you have both HIV and TB


def create_file_without_deceased_equations_and_exit_if_needs_to_be_copied(fn):
    # remove flows which have a Deceased compartment as source to speed up computation
    # (doesn't change result since the weights would be zero but now we just skip them)

    equation_file_lines = list(readlines(f'{fn}.equations.txt')) + ['\n']
    number_of_lines_per_flow_in_file = 5
    nl = number_of_lines_per_flow_in_file
    n_eq = int(len(equation_file_lines) / nl)
    lines_of_each_eq = [equation_file_lines[i * nl:(i + 1) * nl] for i in range(n_eq)]

    equations_file_is_already_clear_of_useless_equations = True
    for lines in lines_of_each_eq:
        source: Compartment = parse(read(lines[1])[0])
        if 'Deceased' in source.labels:
            equations_file_is_already_clear_of_useless_equations = False
            break
    
    fn2 = f'{fn}.non-deceased-equations.txt'
    if equations_file_is_already_clear_of_useless_equations:
        if exists(fn2):
            remove(fn2)
        return
    else:
        with open(fn2, 'w+') as f:
            for lines in lines_of_each_eq:
                source: Compartment = parse(read(lines[1])[0])
                if 'Deceased' not in source.labels:
                    f.writelines(lines)
        # technically we could directly write to f'{fn}.equations.txt' BUT if someone is bothered by this step
        # it is an indicator that they didn't read this file and don't understand why some equations are being removed
        # its like the box you click to agree to terms and conditions, you don't have to read all of it but 
        print(f'Writing equations to `{fn2}`, you are expected to replace the content of `{fn}.equations.txt` with it')
        exit(1)

TOTAL_POP = 38.25e6
CHILD_POP = TOTAL_POP * 0.3
ADULT_18_TO_50_POP = TOTAL_POP * 0.4
ADULT_51_POP = TOTAL_POP * 0.3

def main():
    project_name = 'HIV-TB-Coinfection'
    folder = f'C:/Users/Bruno/Desktop/Model-Driven-Epidemiology/compiled_models/{project_name}/'
    parameters = {}
    compartments, _ = identify_required_parameters(folder, project_name, parameters)
    scenario = read_initial_conditions(folder, project_name, compartments)

    create_file_without_deceased_equations_and_exit_if_needs_to_be_copied(folder + project_name)

    setup_compartments(scenario)
    save_scenario(folder + project_name, scenario)

    setup_parameters_tb(parameters)
    setup_parameters_hiv(parameters)
    setup_parameters_mortality(parameters)

    m = select(parameters, [], len(parameters)).get_missing()
    if len(m) != 0:
        raise Exception("Missing Parameters")
    
    save_parameters(folder + project_name, parameters)

if __name__ == "__main__":
    main()
