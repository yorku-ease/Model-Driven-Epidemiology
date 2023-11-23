from simulation import Parameter, Flow, Compartment, CompartmentValue, parse_fn, parse_compartment, readlines, derivative_for, model_derivative

def _derivative(folder, project_name, compartments, flows):
    flowsbycompartment = [[[], []] for _ in compartments]
    for flow in flows:
        source = compartments.index(flow.source.labels)
        sink = compartments.index(flow.sink.labels)
        _in,_out = 0,1
        flowsbycompartment[source][_out] += [flow]
        flowsbycompartment[sink  ][_in ] += [flow]

    derivatives = [derivative_for(flowsbycompartment[i]) for i in range(len(compartments))]
    initial_conditions = { Compartment(c).as_key() : CompartmentValue(Compartment(c), None) for c in compartments }
    for line in readlines(f'{folder}{project_name}.scenario-compartments.txt'):
        k, v = line.split('=')
        k = k.strip()
        initial_conditions[k] = CompartmentValue(parse_compartment(k.strip()), float(v))

    state = [initial_conditions[Compartment(c).as_key()].value for c in compartments]
    return model_derivative(derivatives, state, 0)

def identify_required_parameters(folder, project_name, provided_parameters, equation_filter):
    compartments = [parse_compartment(line[:-1]).labels for line in readlines(f'{folder}{project_name}.compartments.txt')]
    number_of_lines_per_flow_in_file = 5
    nl = number_of_lines_per_flow_in_file

    equation_file_lines = list(readlines(f'{folder}{project_name}.equations.txt')) + ['\n']
    n_eq = int(len(equation_file_lines) / nl)
    # nl = 5 -> [[0,1,2,3,4],[5,6,7,8,9],...]
    lines_of_each_eq = [equation_file_lines[i * nl:(i + 1) * nl] for i in range(n_eq)]
    if len(lines_of_each_eq) == 0:
        raise Exception('model has no equations')
    lines_of_each_eq = list(filter(lambda eq: equation_filter(eq[3].replace('\n', '')), lines_of_each_eq))
    if len(lines_of_each_eq) == 0:
        raise Exception('Filter rejected all equations')
    flows = [Flow(lines_of_eq, provided_parameters, compartments) for lines_of_eq in lines_of_each_eq]

    return compartments, flows

def derivative(folder, project_name, equation_filter = lambda x: True):
    runLocal = True
    project_name = 'Tuite-Covid-Model-Stratified'
    # project_name = 'test-contact'
    folder = f'C:/Users/Bruno/Desktop/Model-Driven-Epidemiology/compiled_models/{project_name}/' if runLocal else ''
    parameters = {k.strip(): Parameter(parse_fn(k).operands, float(v)) for k, v in [s.split('=') for s in readlines(f'{folder}{project_name}.parameters.txt')]}

    compartments, flows = identify_required_parameters(folder, project_name, parameters, equation_filter)

    missing = [k for k, v in parameters.items() if v.value is None]
    if len(missing) != 0:
        raise Exception("Missing parameters")
    
    return _derivative(folder, project_name, compartments, flows)
