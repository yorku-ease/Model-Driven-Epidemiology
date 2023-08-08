class Flow:
    def __init__(self, lines, parameters, compartments):
        self.equation = lines[0].replace('\n', '').replace("$0", lines[1][:-1]).replace("$1", lines[2][:-1])#[:-1]
        self.source = parse(read(lines[1])[0])#[:-1]
        self.sink = parse(read(lines[2])[0])#[:-1]
        self.compiled_equation = compile(parse(read(self.equation)[0]), parameters, compartments)

    def __repr__(self):
        return f'Flow: equation{self.equation}'

class Function:
    def __init__(self, operator, operands) -> None:
        self.operator = operator
        self.operands = operands

    def __repr__(self):
        if isinstance(self.operator, WordOrSymbol) and self.operator.value == 'parameter':
            return f'parameter: {self.operands})'
        else:
            return f'Function: ({self.operator} {" ".join([str(o) for o in self.operands])})'

class Compartment:
    def __init__(self, labels) -> None:
        self.labels = labels

    def __repr__(self):
        return 'Compartment' + str(self.labels)

class WordOrSymbol:
    def __init__(self, value: str) -> None:
        self.value = value

    def __repr__(self):
        return self.value

class ReadResult:
    def __init__(self, string_value: str, object_type: type) -> None:
        self.string_value = string_value
        self.object_type = object_type

def read(string: str, i: int = 0):
    string = string.replace('\n', ' ').replace('\t', ' ')
    beg = string[i]
    while string[i] == ' ':
        i += 1
    if beg == '(':
        return read_fn(string, i)
    elif beg == '[':
        return read_compartment(string, i)
    else:
        return read_word_or_symbol(string, i)

def read_fn(string: str, i: int):
    start = i
    depth = 0
    while True:
        if string[i] == '(':
            depth += 1
        elif string[i] == ')':
            depth -= 1
        i += 1
        if depth == 0:
            break
    return ReadResult(string[start:i], Function), i + 1

def read_compartment(string: str, i: int):
    start = i
    while string[i] != ']':
        i += 1
    # include the closing bracket
    i += 1
    return ReadResult(string[start:i], Compartment), i + 1

def read_word_or_symbol(string: str, i: int):
    start = i
    while i < len(string) and string[i] != ' ' and string[i] != '[' and string[i] != ')' and string[i] != '(':
        i += 1
    return ReadResult(string[start:i], WordOrSymbol), i + 1

def parse(read_result: ReadResult):
    parser = {
        Function: parse_fn,
        Compartment: parse_compartment,
        WordOrSymbol: parse_word_or_symbol
    }
    return parser[read_result.object_type](read_result.string_value)

def parse_fn(string: str):
    string = string[1:-1] # take `...` from `(...)`
    terms = []
    i = 0
    while i < len(string):
        if string[i] == ' ':
            i += 1
            continue
        r, i = read(string, i)
        terms += [parse(r)]
    return Function(terms[0], terms[1:])

def parse_compartment(string: str):
    if string[-1] != ']':
        print(string, string[-1])
        raise 1
    string = string[1:-1] # take `...` from `[...]`
    return Compartment([s.strip() for s in string.split(',')])

def parse_word_or_symbol(string: str):
    return WordOrSymbol(string)

def compile(parse_result, parameters, compartments):
    if isinstance(parse_result, Function):
        return compile_equation(parse_result, parameters, compartments)
    if isinstance(parse_result, Compartment):
        return compile_compartment(parse_result, compartments)
    if isinstance(parse_result, WordOrSymbol):
        return compile_word_or_symbol(parse_result)
    return parse_result

def compile_compartment(compartment: Compartment, compartments):
    s = set(compartment.labels)
    indices = [i for i, c in enumerate(compartments) if set(c).issuperset(s)]
    if len(indices) == 1:
        return lambda state: state[indices[0]]
    else:
        return lambda state: [state[i] for i in indices]

def compile_word_or_symbol(word_or_symbol: WordOrSymbol,):
    return word_or_symbol

def evaluate_if_fn(x, parameters, compartments):
    if isinstance(x, Function):
        return evaluate_if_fn(compile_equation(x, parameters, compartments), parameters, compartments)
    else:
        return x

def compile_equation(equation: Function, parameters, compartments):
    if isinstance(equation.operator, WordOrSymbol):
        fn = equation.operator.value
        equation.operands = [evaluate_if_fn(x, parameters, compartments) for x in equation.operands]
        # parameters are a special function and could have their own
        # element in the grammar (such as the use of brackets) to denote them
        # but for now they are denoted using `(parameter ...)` like other functions
        if fn == 'parameter':
            prepare_parameter(equation.operands, parameters, compartments)
            parameters = get_parameters(equation.operands, parameters, compartments)
            return lambda state: [parameter.evaluate() for parameter in parameters]
        else:
            if fn == '*':
                compiled_operands = [compile(operand, parameters, compartments) for operand in equation.operands]
                return lambda state: product([operand(state) for operand in compiled_operands])
            elif fn == 'sumproduct':
                compiled_operands = [compile(operand, parameters, compartments) for operand in equation.operands]
                return lambda state: sumproduct([operand(state) for operand in compiled_operands])
            elif fn == 'join':
                join_using, join_seq = equation.operands[0], equation.operands[1]
                if not isinstance(join_using, WordOrSymbol):
                    raise Exception("Join first argument has to be a WordOrSymbol")
                if isinstance(join_seq, Compartment):
                    return WordOrSymbol(join_using.value.join(join_seq.labels))
                else:
                    # todo maybe something makes sense to implement here
                    raise Exception("Join second argument has to be a Compartment")
            elif fn == 'intersection':
                c1, c2 = equation.operands[0], equation.operands[1]
                if not isinstance(c1, Compartment):
                    raise Exception("Join first argument has to be a Compartment")
                if not isinstance(c2, Compartment):
                    raise Exception("Join second argument has to be a Compartment")
                return Compartment([x for x in c1.labels if x in c2.labels])
            else:
                raise Exception(f"Unknown function `{fn}`")
    else:
        raise Exception(f"Expected operator of type WordOrSymbol, received {type(equation.operator)}")

def product(terms):
    p = 1
    for t in terms:
        if isinstance(t, list):
            p *= sum(t)
        else:
            p *= t
    return p

def sumproduct(values):
    expected_len = len(values[0])
    for v in values:
        if len(v) != expected_len:
            raise Exception(f'could not broadcast matrices or vectors of sizes `{[len(v) for v in values]}` ({values})')
    return sum([product([value[i] for value in values]) for i in range(len(values[0]))])

def parse_compartments(line):
    return [parse_compartment(c) for c in line.replace('[[', '[').replace(']]', ']').replace('\n', '').split('], ')]

class Parameter:
    def __init__(self, name, value) -> None:
        self.name = name
        self.value = value

    def evaluate(self):
        if self.value is None:
            raise Exception(f"None valued parameter '{self.name}'")
        else:
            return self.value

def prepare_parameter(operands, parameters, compartments):
    for i, operand in enumerate(operands):
        if isinstance(operand, Compartment):
            is_full_compartment_specification = operand.labels in compartments
            if not is_full_compartment_specification:
                s = set(operand.labels)
                subsets_of_partial_specialization = [Compartment(c) for c in compartments if set(c).issuperset(s)]
                before, after = operands[:i], operands [i+1:]
                for c in subsets_of_partial_specialization:
                    prepare_parameter(before + [c] + after, parameters, compartments)
                return
        else:
          if '[' in str(operand):
            print(f'not compartment {operand}')
            raise 1
    key = '(parameter'
    for op in operands:
        if isinstance(op, WordOrSymbol):
            key += ' ' + op.value
        if isinstance(op, Compartment):
            key += ' ' + str(op.labels).replace('"', '').replace("'", '')
    key += ')'
    if key not in parameters:
        parameters[key] = Parameter(operands, None)

def get_parameters(operands, parameters, compartments):
    for i, operand in enumerate(operands):
        if isinstance(operand, Compartment):
            is_full_compartment_specification = operand.labels in compartments
            if not is_full_compartment_specification:
                s = set(operand.labels)
                subsets_of_partial_specialization = [Compartment(c) for c in compartments if set(c).issuperset(s)]
                before, after = operands[:i], operands [i+1:]
                res = []
                for c in subsets_of_partial_specialization:
                    params = get_parameters(before + [c] + after, parameters, compartments)
                    if isinstance(params, list):
                        res.extend(params)
                    else:
                        res.extend([params])
                return res
    key = '(parameter'
    for op in operands:
        if isinstance(op, WordOrSymbol):
            key += ' ' + op.value
        if isinstance(op, Compartment):
            key += ' ' + str(op.labels).replace('"', '').replace("'", '')
    key += ')'
    return [parameters[key]]

def evalDelta(flows, state):
    res = 0
    for flow in flows:
        res += flow.compiled_equation(state)
    return res

def derivativeForCompartmentIndex(flowsbycompartment, i):
    return lambda state: evalDelta(flowsbycompartment[i][0], state) - evalDelta(flowsbycompartment[i][1], state)

def model_derivative(derivatives, state, t):
    print(f'\r{t}                                                         ', end='')
    state = [x if x > 0 else 0 for x in state]
    d = [derivatives[i](state) for i in range(len(state))]
    return d

def test_required_parameters(parameters):
    return [name for name, parameter in parameters.items() if parameter.value is None]

def set_parameters(parameters, labels, value):
    c_new = 0
    c_replace = 0
    for k, v in parameters.items():
        if all(f"'{label}'" in k for label in labels):
            if v.value is None:
                c_new += 1
            else:
                c_replace += 1
            parameters[k].value = float(value)
    print(f'set {c_new} values and modified {c_replace} values')

def count_missing_parameters(parameters, labels):
    c = 0
    for k, v in parameters.items():
        if v.value is None and all(f"'{label}'" in k for label in labels):
            c += 1
    print(f'missing {c} values')

def list_unique_missing_labels_parameters(parameters, labels):
    missing = set()
    for k, v in parameters.items():
        if v.value is None and all(f"'{label}'" in k for label in labels):
            for label in parse_compartment(k).labels:
                missing.add(label)
    print([label for label in missing if label not in labels])

def validate_all_parameters_exist(folder, project_name):
    compartments = [parse_compartment(line[:-1]).labels for line in open(f'{folder}{project_name}.compartments.txt').readlines()]
    number_of_lines_per_flow_in_file = 4
    flows = list(open(f'{folder}{project_name}.equations.txt').readlines()) + ['\n', '\n'] # add 2 empty lines just in case the file is not generated with them
    flows = [flows[i * number_of_lines_per_flow_in_file:(i + 1) * number_of_lines_per_flow_in_file] for i in range(int(len(flows) / number_of_lines_per_flow_in_file))]
    provided_parameters = {k.strip(): Parameter(k, float(v)) for k, v in [s.split('=') for s in open(f'{folder}{project_name}.parameters.txt').readlines()]}
    flows = [Flow(lines, provided_parameters, compartments) for lines in flows]

    # each compartment has an in and an out list
    flowsbycompartment = [[[], []] for _ in compartments]
    for flow in flows:
        source = compartments.index(flow.source.labels)
        sink = compartments.index(flow.sink.labels)
        _in,_out = 0,1
        flowsbycompartment[source][_out] += [flow]
        flowsbycompartment[sink  ][_in ] += [flow]

    print(f'model compartments = {len(compartments)}')
    print(f'model equations = {len(flows)}')

    missing_parameters = test_required_parameters(provided_parameters)

    return len(missing_parameters) == 0

def run_sim(folder, project_name):
    compartments = [parse_compartment(line[:-1]).labels for line in open(f'{folder}{project_name}.compartments.txt').readlines()]
    number_of_lines_per_flow_in_file = 4
    flows = list(open(f'{folder}{project_name}.equations.txt').readlines()) + ['\n', '\n'] # add 2 empty lines just in case the file is not generated with them
    flows = [flows[i * number_of_lines_per_flow_in_file:(i + 1) * number_of_lines_per_flow_in_file] for i in range(int(len(flows) / number_of_lines_per_flow_in_file))]
    provided_parameters = {k.strip(): Parameter(k, float(v)) for k, v in [s.split('=') for s in open(f'{folder}{project_name}.parameters.txt').readlines()]}
    flows = [Flow(lines, provided_parameters, compartments) for lines in flows]

    # each compartment has an in and an out list
    flowsbycompartment = [[[], []] for _ in compartments]
    for flow in flows:
        source = compartments.index(flow.source.labels)
        sink = compartments.index(flow.sink.labels)
        _in,_out = 0,1
        flowsbycompartment[source][_out] += [flow]
        flowsbycompartment[sink  ][_in ] += [flow]

    print(f'model compartments = {len(compartments)}')
    print(f'model equations = {len(flows)}')

    missing_parameters = test_required_parameters(provided_parameters)

    if len(missing_parameters) > 0:
        print("Missing Parameters:")
        for parameter in missing_parameters:
            print(parameter)
        raise Exception("Missing Parameters")
    else:
        derivatives = [derivativeForCompartmentIndex(flowsbycompartment, i) for i in range(len(compartments))]
        initial_conditions = {str(c) : v for c, v in zip(compartments, [float(line.split('=')[1]) for line in open(f'{folder}{project_name}.scenario.txt').readlines()])}
        print('d(model)/dt | t=0; = ', model_derivative(derivatives, [initial_conditions[str(c)] for c in compartments], 0))

        # min, max = 0, 100
        # steps = 10
        # t_span = np.linspace(min, max, num=steps)

        # md = lambda state, t: model_derivative(derivatives, state, t)
        # solution = np.array([[initial_conditions[str(c)] for c in compartments]])#odeint(md, [initial_conditions[str(c)] for c in compartments], t_span)
        # return solution

project_name = 'HIV-TB-Coinfection'
runLocal = True
folder = f'C:/Users/Bruno/Desktop/Model-Driven-Epidemiology/compiled_models/{project_name}/' if runLocal else ''
# project_name = 'test-contact'
solution = run_sim(folder, project_name)

len(solution), len(solution[0])

shape = (4, 4, 4, 2, 2)
# shape = (2, 2)

# def plot_by_labels(list_of_list_of_labels):
#     for list_of_labels in list_of_list_of_labels:
#         curves = []
#         sub = set(list_of_labels)
#         for i, c in enumerate(compartments):
#             if set(c).issuperset(sub):
#                 curves += [solution[:, i]]
#         res = np.sum(np.array(curves), axis = 0)
#         l, = plt.plot(res)
#         l.set_label(list_of_labels)
#     plt.legend()
#     plt.show()

# # plot_by_labels([['S'], ['I']])
# # plot_by_labels([['S', 'Child'], ['I', 'Child']])
# # plot_by_labels([['S', 'Adult'], ['I', 'Adult']])
# # plot_by_labels([['I', 'Child'], ['I', 'Adult']])

# # plot_by_labels([['S-HIV'], ['I-HIV']])
# # plot_by_labels([['S-HIV', 'Child'], ['I-HIV', 'Child']])
# # plot_by_labels([['S-HIV', 'Adult-51+'], ['I-HIV', 'Adult-51+']])
# # plot_by_labels([['I-HIV', 'Child'], ['I-HIV', 'Adult-51+']])

# def set_initial_conditions(initial_conditions, labels, value):
#     c = 0
#     for k, _ in initial_conditions.items():
#         if all(f"'{label}'" in k for label in labels):
#             c += 1
#         initial_conditions[k] = float(value)
#     print(f'modified {c} values')

# def count_missing_initial_conditions(initial_conditions, labels):
#     c = 0
#     for k, v in initial_conditions.items():
#         if v.value == None and all(f"'{label}'" in k for label in labels):
#             c += 1
#     print(f'missing {c} values')

# def list_unique_missing_labels_initial_conditions(initial_conditions, labels):
#     missing = set()
#     for k, v in initial_conditions.items():
#         if v.value == None and all(f"'{label}'" in k for label in labels):
#             for label in parse_compartment(k).labels:
#                 missing.add(label)
#     print([label for label in missing if label not in labels])

# count_missing_initial_conditions(initial_conditions, ['R-TB'])

# list_unique_missing_labels_initial_conditions(initial_conditions, ['R-TB'])

# # set_initial_conditions(initial_conditions, [], 0)
# # set_initial_conditions(initial_conditions, ['Alive', 'S-HIV'], 999)
# # set_initial_conditions(initial_conditions, ['Alive', 'S-TB'], 999)
# # set_initial_conditions(initial_conditions, ['Alive', 'I-HIV', 'S-TB'], 1)
# # set_initial_conditions(initial_conditions, ['Alive', 'S-HIV', 'I-TB'], 1)
# # set_initial_conditions(initial_conditions, ['Alive', 'S-HIV', 'I-TB'], 1)

# initial_conditions

