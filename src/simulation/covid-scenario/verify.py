import covid_scenario
import test_derivative
import tuite_eq
import params
import numpy as np

def main():
    # give each compartment some value so that we don't end up with
    # equations not tested because they output zero because some compartment has value zero
    params.initial_population += np.ones((16, 32)) * 7

    covid_scenario.main()

    project_name = 'Tuite-Covid-Model-Stratified'
    folder = f'C:/Users/Bruno/Desktop/Model-Driven-Epidemiology/compiled_models/{project_name}/'

    # aging = test_derivative.derivative(folder, project_name, lambda x: x == 'Aging')
    # exposure = test_derivative.derivative(folder, project_name, lambda x: x == 'exposure')
    filters = ['Aging', 'exposure', 'infection', 'symptoms', 'hospitalization', 'recovery', 'recovery-h', 'enter-icu', 'exit-icu', 'death']
    for f in filters:
        ours = np.array(test_derivative.derivative(folder, project_name, lambda x: x == f)).reshape((16,32))
        print(f, ours[0][0])
        print(f, ours[0][15])
        print(f, ours[0][16])
        print(f, ours[0][31])
        expected = tuite_eq.derivative(params.our_initial_population, lambda x: x == f, True)
        equals = np.all(np.isclose(ours, expected, rtol = 1e-3))
        if equals:
            print(f, 'ok')
        else:
            print(f, 'failed')
            for i in range(16):
                if np.all(np.isclose(ours[i], expected[i], rtol = 1e-3)):
                    print(i, params.our_order[i], 'ok')
                else:
                    print(i, params.our_order[i], 'failed')
                    print(f'{ours[i]=}')
                    print(f'{expected[i]=}')
            exit(1)

    r = np.array(test_derivative.derivative(folder, project_name, lambda x: True)).reshape((16,32))
    e = tuite_eq.derivative(params.our_initial_population, lambda x: x != 'births', True)
    print(r[0])
    print(e[0])
    if np.all(np.isclose(r, e, rtol = 1e-10)):
        print('all equations work')
    else:
        print('every individually tested equations work but there seems to be a difference in the complete model')


if __name__ == "__main__":
    main()
