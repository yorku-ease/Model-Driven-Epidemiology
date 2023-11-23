import covid_scenario
import test_derivative
import tuite_eq
import params
import numpy as np

def main():
    covid_scenario.main()
    
    project_name = 'Tuite-Covid-Model-Stratified'
    folder = f'C:/Users/Bruno/Desktop/Model-Driven-Epidemiology/compiled_models/{project_name}/'

    # aging = test_derivative.derivative(folder, project_name, lambda x: x == 'Aging')
    # exposure = test_derivative.derivative(folder, project_name, lambda x: x == 'exposure')
    filters = ['Aging', 'exposure']
    for f in filters:
        print(f)
        ours = np.array(test_derivative.derivative(folder, project_name, lambda x: x == f)).reshape((16,32))
        expected = tuite_eq.derivative(params.our_initial_population, lambda x: x == f, True)

        equals = np.all(np.isclose(ours, expected, rtol = 1e-3))
        if equals:
            print('ok')
        else:
            print(np.isclose(ours, expected, rtol = 1e-3))
            print('failed')

if __name__ == "__main__":
    main()
