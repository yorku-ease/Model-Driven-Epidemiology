import numpy as np
import params

interv_on = False
t = 0

rho = 1 / params.quarantine_dur
epsilon = 1 / params.latent_period
gamma_p = 1 / params.inf_period_presymp
gamma_s = 1 / params.inf_period_severe
gamma_m = 1 / params.inf_period_mild 
time_to_detect = params.time_to_detect_interv if interv_on else params.time_to_detect
#rate of entry to isolated compartment
gamma_d = 1 / time_to_detect
# rate of exit from isolated (and infectious) compartment
gamma_i = np.array([
    1 / (params.inf_period_mild - ttd) if ttd < params.inf_period_mild else 1 for ttd in time_to_detect
    # 1 / (params.inf_period_mild - time_to_detect) if time_to_detect < params.inf_period_mild else 1
])
beta = params.beta
rr_i = params.rr_isolate_interv if interv_on  else params.rr_isolate
delta_s = params.p_quarantine_s_interv if interv_on  else params.p_quarantine_s
delta_e = params.p_quarantine_e_interv if interv_on else params.p_quarantine_e
sigma_s = params.p_severe
sigma_i = params.p_icu
psi = 1 / params.hosp_dur
pi_a = 1 / params.icu_a_dur
pi_b = 1 / params.icu_b_dur
pi_c = 1 / params.icu_c_dur
cfr_icu = params.cfr_icu
theta = params.cm * params.rr_contact_interv if interv_on else params.cm * params.rr_contact

def derivative(state):
    s = state[0]
    v = state[1]
    e = state[2]
    q = state[3]
    a = state[4]
    w = state[5]
    b = state[6]
    c = state[7]
    y = state[8]
    z = state[9]
    h = state[10]
    icua = state[11]
    icub = state[12]
    icuc = state[13]
    r = state[14]
    d = state[15]

    # print(f'{beta=}')
    # print(f'{(a + rr_i*w)=}')
    # print(f"{b=}")
    # print(f"{rr_i=}")
    # print(f"{g=}")
    # print(f"{y=}")
    # print(f'{(b+rr_i*(g+y))=}')
    # print(f'{(c+rr_i*z)=}')
    # print(f'{(s+v+e+q+a+w+b+c+y+z+g+h+icua+icub+icuc+r)=}')
    # print(f'{np.multiply(theta, l_h)=}')

    #calculate force of infection
    pop = (s+e+q+a+w+b+c+y+z+h+icua+icub+icuc+r)
    l_h = beta * (a + rr_i*w + b+rr_i*(y) + c+rr_i*z)/pop
    l_h2 = beta * (a+b+c + rr_i*(w+y+z)) / pop
    _lambda = np.sum(theta * l_h, axis=1)
    print(f'{_lambda=}')
    # print(f'{theta[0] * l_h=}')
    # print(f'{theta[:,0] * l_h=}')
    # print(f'{(theta * l_h)[:,0]=}')
    # print(_lambda[0] == theta[0] * l_h)
    effect_of_mild = np.sum(params.cm * beta * b / pop, axis=1)
    effect_of_mild_on_S = effect_of_mild * s
    print('effect of mild on S'  , effect_of_mild_on_S)
    print('(effect of mild on S)[0]'  , effect_of_mild_on_S[0])
    print('mild 0', b[0])
    print(f'{_lambda[0] * s[0]=}')
    print(f'{np.sum(params.cm * l_h, axis=1)[0] * s[0]=}')
    print(f'{np.sum(params.cm * beta * b / pop, axis=1)[0] * s[0]=}')
    print(f'{np.sum(params.cm / pop, axis=1) * beta * b[0]=}')
    print(f'{np.sum(params.cm[0]) / pop[0] * beta * b[0]=}')

    np.random.seed(14641)
    d = 2
    test_cm = np.random.rand(d, d)
    test_b = np.random.rand(d)
    test_pop = np.random.rand(d)

    print()
    print()
    print(f'{test_cm=}')
    print(f'{test_pop=}')
    print()
    # print(f'{test_cm / test_pop=}')
    # print(test_cm[0][0] / test_pop[0], test_cm[0][1] / test_pop[1], test_cm[1][0] / test_pop[0], test_cm[1][1] / test_pop[1])
    # print(f'{np.sum(test_cm / test_pop, axis=1)=}')
    # print(f'{np.sum(test_cm[0] / test_pop)=}')
    # print(f'{test_cm[0][0] / test_pop[0] + test_cm[0][1] / test_pop[1]=}')

    print(f'{test_cm * test_b=}')
    print(test_cm[0][0] * test_b[0], test_cm[0][1] * test_b[1], test_cm[1][0] * test_b[0], test_cm[1][1] * test_b[1])
    print(f'{np.sum(test_cm * test_b, axis=1)=}')
    print(f'{np.sum(test_cm[0] * test_b)=}')
    print(f'{test_cm[0][0] * test_b[0] + test_cm[0][1] * test_b[1]=}')

    # cm is 32*32
    # beta is 1
    # b is 32
    # s+v+... is 32
    # s is 32
    # np.sum(32x32 * 1 * 32 / 32, axis=1) * 32
    # np.sum(32x32) * 32
    # 32 * 32
    # _cm = np.array([[1, 2], [3, 4]])
    # _b = np.array([7, 8])
    # _s = np.array([5, 9])
    # print(f'{np.sum(_cm)=}')
    # print(f'{np.sum(_cm, axis=0)=}')
    # print(f'{np.sum(_cm, axis=1)=}')
    # print(f'{np.sum(_cm.T, axis=1)=}')
    # print(f'{np.sum(_cm.T, axis=0)=}')
    # print('_cm * _b * _s', np.sum(_cm * _b, axis=1) * _s)
    # print('_cm * _b * _s', [5 * (7 * 1 + 8 * 2),     9 * (7 * 3  + 8 * 4)   ])
    # print('_cm * _b * _s', [5 * np.sum(_cm[0] * _b), 9 * np.sum(_cm[1] * _b)])
    # _t = np.sum(_cm * _b, axis=1)
    # print('_cm * _b * _s', [5 * _t[0], 9 * _t[1]])

    # print('severe', np.sum(params.cm * beta * c / (s+v+e+q+a+w+b+c+y+z+g+h+icua+icub+icuc+r), axis=1) * s)
    # print(_lambda * s)
    # print(_lambda[0] * s[0])
    # print(sum((theta * l_h)[0]) * s[0])
    # print(params.cm * beta * c / (s+v+e+q+a+w+b+c+y+z+g+h+icua+icub+icuc+r))
    # print(np.sum(params.cm * beta * c / (s+v+e+q+a+w+b+c+y+z+g+h+icua+icub+icuc+r), axis=1))
    
    # print(f'{l_h=}')
    # print(f'{_lambda=}')
    print(f'{-_lambda * s=}')
    # print('expected dSdt = ', params.aging.dot(s) -_lambda * s)
    # print('expected dSdt[aging] = ', params.aging.dot(s))
    # print('expected dSdt[exposure] = ',  -_lambda * s)
    dsdt = -_lambda * s - delta_s * s + params.aging.dot(s) + params.births.dot(pop)# + rho * v
    dvdt = delta_s * s - rho * v + params.aging.dot(v)
    dedt = (1-delta_e) * _lambda * s - epsilon * e + params.aging.dot(e)
    dqdt = delta_e * _lambda * s - epsilon * q + params.aging.dot(q)
    dadt = epsilon * e - gamma_p * a + params.aging.dot(a)
    dwdt = epsilon * q - gamma_p * w + params.aging.dot(w)
    dbdt = (1-sigma_s) * gamma_p * a - gamma_m * b - gamma_d * b + params.aging.dot(b)
    dcdt = sigma_s * gamma_p * a - gamma_s * c + params.aging.dot(c)
    dydt = (1-sigma_s) * gamma_p * w - gamma_m * y + params.aging.dot(y)
    dzdt = sigma_s * gamma_p * w - gamma_s * z + params.aging.dot(z)
    # dgdt = gamma_d * b - gamma_i * g + params.aging.dot(g)
    dhdt =  (1-sigma_i) * gamma_s * (c+z) - psi * h + params.aging.dot(h)
    dicuadt = sigma_i * gamma_s * (c+z) - pi_a * icua + params.aging.dot(icua)
    dicubdt = pi_a * icua - pi_b * icub + params.aging.dot(icub)
    dicucdt =(1-cfr_icu) * pi_b * icub - pi_c * icuc +  params.aging.dot(icuc)
    drdt = gamma_m * (b+y) + psi * h + cfr_icu * pi_b * icub + pi_c * icuc + params.aging.dot(r) # + gamma_i * g
    dddt =  cfr_icu * pi_b * icub # to keep deaths as absorbing state and pop size constant, moving all hosp back to recovered/removed state

    return np.array([
        dsdt, dvdt, dedt, dqdt, dadt, dwdt, dbdt, dcdt, dydt, dzdt,# dgdt,
        dhdt, dicuadt, dicubdt, dicucdt, drdt, dddt
    ])
    # dincdt = _lambda*s
    # dkdt = gamma_d*b
    # djdt = gamma_s*(c+z)

d = derivative(params.initial_population)
# print('expected dS=', d[0])
# print(d[0])
# print(d[2])
