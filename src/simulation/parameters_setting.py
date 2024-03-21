from simulation import *
from os import remove
from os.path import exists

class Selection:
    def __init__(self, container, keys) -> None:
        self.keys = keys
        self.container = container
    
    # the sum of the first term of the result over multiple calls is the number of values changed from None to not None
    # this is useful in order to verify if you expect 256 parameters to be set, that your code corresponds to that
    # the second term, c_replace, is not as useful but can be for debugging if the first term is unexpected
    def set(self, value: float):
        '''
        sets values and returns (the number of None changed to value, number of values replaced by value)

        if the provided value is none instead return (-number of values replaced by None, 0)
        '''
        c_new = 0
        c_replaced = 0

        if value is None:
            for key in self.keys:
                if self.container[key].value is not None:
                    c_new -= 1
                    self.container[key].value = None
        else:
            for key in self.keys:
                if self.container[key].value is None:
                    c_new += 1
                else:
                    c_replaced += 1
                self.container[key].value = float(value)

        return c_new, c_replaced

    def set_array(self, values: [float]):
        '''
        TODO doc just works like set (same reasoning with return value of (new, replaced))
        '''
        if len(self.keys) != len(values):
            raise Exception(f"Expected {len(self.keys)} values but received {len(values)}")
        
        c_new = 0
        c_replaced = 0

        for key, value in zip(self.keys, values):
            if self.container[key].value is None:
                c_new += 1
            else:
                c_replaced += 1
            self.container[key].value = float(value)
        
        return c_new, c_replaced


    def multiply(self, factor: float):
        '''
        multiplies values and returns (0, number of values changed)
        '''
        c_replace = 0
        for key in self.keys:
            if self.container[key].value is None:
                raise Exception(f"Cannot multiply parameter at {key}, it is currently None")
            c_replace += 1
            self.container[key].value *= float(factor)
        
        return 0, c_replace

    def get_missing(self):
        return [key for key in self.keys if self.container[key].value is None]

    def get_non_missing(self):
        return [key for key in self.keys if self.container[key].value is not None]

def select_parameter_string_equals(index, s):
    return lambda parameter: (len(parameter.operands) > index) and isinstance(parameter.operands[index], WordOrSymbol) and (parameter.operands[index].value == s)

def select_parameter_string_contains(index, s):
    return lambda parameter: (len(parameter.operands) > index) and isinstance(parameter.operands[index], WordOrSymbol) and (s in parameter.operands[index].value)

def select_parameter_compartment_contains(index, s):
    return lambda parameter: (len(parameter.operands) > index) and isinstance(parameter.operands[index], Compartment) and (s in parameter.operands[index].labels)

def select_parameter_compartment_does_not_contain(index, s):
    return lambda parameter: (len(parameter.operands) > index) and isinstance(parameter.operands[index], Compartment) and (s not in parameter.operands[index].labels)

def select(container, criteria, n_expected: int):
    keys = []
    for k, v in container.items():
        if all(criterion(v) for criterion in criteria):
            keys += [k]
    if len(keys) == 0:
        raise Exception("Empty selection")
    if len(keys) != n_expected:
        raise Exception(f"Invalid selection, expected {n_expected}, found {len(keys)}")
    return Selection(container, keys)

def save_parameters(filename_without_extension, parameters):
    s = select(parameters, [], len(parameters))
    m  = s.get_missing()
    nm = s.get_non_missing()

    fn = f'{filename_without_extension}.missing-parameters.txt'
    if len(m):
        print(f'writing missing parameters to `{fn}`')
        with open(fn, 'w') as f:
            for p in m:
                f.write(p + ' = 0\n')
    else:
        print(f'no missing parameters, erasing `{fn}`')
        if exists(fn):
            remove(fn)
    
    if len(nm):
        fn = f'{filename_without_extension}.parameters.txt'
        print(f'writing existing parameters to `{fn}`')

        with open(fn, 'w') as f:
            for p in nm:
                f.write(p + f' = {parameters[p].value}\n')

def select_scenario_compartment_contains(first, *rest):
    return lambda compartment: all(x in compartment.compartment.labels for x in [first, *rest])

def select_scenario_compartment_does_not_contain(first, *rest):
    return lambda compartment: all(x not in compartment.compartment.labels for x in [first, *rest])

def save_scenario(filename_without_extension, scenario):
    s = select(scenario, [], len(scenario))
    m  = s.get_missing()
    nm = s.get_non_missing()

    fn = f'{filename_without_extension}.missing-scenario-compartments.txt'
    if len(m):
        print(f'writing missing compartments to `{fn}`')
        with open(fn, 'w') as f:
            for p in m:
                f.write(p + ' = 0\n')
    else:
        print(f'no missing compartments, erasing `{fn}`')
        if exists(fn):
            remove(fn)
    
    if len(nm):
        fn = f'{filename_without_extension}.scenario-compartments.txt'
        print(f'writing existing compartments to `{fn}`')
        with open(fn, 'w') as f:
            for p in nm:
                f.write(p + f' = {scenario[p].value}\n')
