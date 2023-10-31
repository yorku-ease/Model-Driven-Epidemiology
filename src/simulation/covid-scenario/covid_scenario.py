from parameters_setting import *
from mycsv import CSV
import read_cchs

# parameterization of the model following https://www.cmaj.ca/content/cmaj/suppl/2020/04/08/cmaj.200476.DC1/200476-res-1-at.pdf

def can_gov_ages_to_range(ages):
    if 'over' in ages or 'older' in ages:
        return [int(ages.split(' ')[0]), 0]
    else:
        ages = ages.split('years')[0].split(' to ')
        return [int(ages[0]), int(ages[1])]

contact_last_age_delim = 70
pop_last_age_delim = 100

# https://www150.statcan.gc.ca/n1/en/pub/91-215-x/91-215-x2019001-eng.pdf?st=QscDWW2B
pop_csv = CSV('pop-2019.csv', ',', [can_gov_ages_to_range, int])

 # https://www150.statcan.gc.ca/n1/en/catalogue/82M0013X2016001
comorbidity_csv = CSV('cchs-out.csv', ',', [can_gov_ages_to_range, float]) 

# https://github.com/epiforecasts/socialmixr/issues/1
contact_csv = CSV('polymod_matrix.csv', ',', [float] * (int(contact_last_age_delim / 5) + 1)) 

# now we need to reshape the data into the same groups used by the researchers
# The last age group (i = 16) included the population aged 75 and older

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

# finally we will do the population table
count_75_plus = sum(list(pop_csv.get_simple('count'))[-6:])
pop_csv.data = pop_csv.data[:-6]
pop_csv.data += [[[75, 0], count_75_plus]]


def main():
    project_name = 'covid'
    folder = f'C:/Users/Bruno/Desktop/Model-Driven-Epidemiology/compiled_models/{project_name}/'
    parameters = {}
    compartments, _ = identify_required_parameters(folder, project_name, parameters)
    scenario = read_initial_conditions(folder, project_name, compartments)

    # setup_compartments(scenario)
    # save_scenario(folder + project_name, scenario)

    # setup_parameters_tb(parameters)
    # setup_parameters_hiv(parameters)
    # setup_parameters_mortality(parameters)

    m = select(parameters, []).get_missing()
    if len(m) != 0:
        raise Exception("Missing Parameters")
    
    save_parameters(folder + project_name, parameters)


# if __name__ == "__main__": main()
