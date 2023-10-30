from parameters_setting import *

def transform(point, transforms):
    if transforms == []:
        return point
    else:
        return [f(p) for f, p in zip(transforms, point)]

class CSV:
    def __init__(self, lines, delim, transforms=[]):
        self.headers = list(map(lambda header: header.strip(), lines[0].split(delim)))
        self.data = list(map(lambda line: transform(line.split(delim), transforms), lines[1:]))
    
    def get(self, *cols):
        indices = [i for i, col in enumerate(self.headers) if col in cols]
        for point in self.data:
            yield [point[i] for i in indices]
    
    def get_simple(self, col):
        for point in self.get(col):
            yield point[0]

def can_gov_ages_to_range(ages):
    if 'over' in ages:
        return [int(ages.split(' ')[0]), 0]
    else:
        ages = ages.split('years')[0].split(' to ')
        return [int(ages[0]), int(ages[1])]

def can_gov_count_to_int(count):
    return int(count.strip().replace(',', ''))

polymod_last_age_delim = 70
n_age_grouped_by_5 = int(polymod_last_age_delim / 5)

can_gov_csv = CSV(open('pop.csv').readlines(), '\t', [can_gov_ages_to_range, can_gov_count_to_int])
can_gov_csv.data = can_gov_csv.data[:n_age_grouped_by_5] +\
    [[[polymod_last_age_delim, 0], sum(list(can_gov_csv.get_simple('count'))[n_age_grouped_by_5:])]]

contact_csv = CSV(open('polymod/matrix.csv').readlines(), ',', [float] * (n_age_grouped_by_5 + 1)) # https://github.com/epiforecasts/socialmixr/issues/1
TOTAL_POP = sum(map(int, can_gov_csv.get_simple('count')))
# https://www.cmaj.ca/content/cmaj/suppl/2020/04/08/cmaj.200476.DC1/200476-res-1-at.pdf

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


if __name__ == "__main__":
    main()
