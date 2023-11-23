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

def derivative(state, equation_filter, do_index_remap):
    indices = list(range(16)) if not do_index_remap else [params.our_order.index(params.tuite_order[i]) for i in range(16)]

    s = state[indices[0]]
    v = state[indices[1]]
    e = state[indices[2]]
    q = state[indices[3]]
    a = state[indices[4]]
    w = state[indices[5]]
    b = state[indices[6]]
    c = state[indices[7]]
    y = state[indices[8]]
    z = state[indices[9]]
    h = state[indices[10]]
    icua = state[indices[11]]
    icub = state[indices[12]]
    icuc = state[indices[13]]
    r = state[indices[14]]
    d = state[indices[15]]

    pop = (s+e+q+a+w+b+c+y+z+h+icua+icub+icuc+r)
    l_h = beta * (a+b+c + rr_i*(w+y+z)) / pop
    _lambda = np.sum(theta * l_h, axis=1)
    
    _lambda  = _lambda       if equation_filter('exposure')        else _lambda * 0
    _delta_e = delta_e       if equation_filter('exposure')        else delta_e * 0
    _epsilon = epsilon       if equation_filter('infection')       else epsilon * 0
    _gamma_p = gamma_p       if equation_filter('symptoms')        else gamma_p * 0
    _sigma_s = sigma_s       if equation_filter('symptoms')        else sigma_s * 0
    _gamma_s = gamma_s       if equation_filter('hospitalization') else gamma_s * 0
    _sigma_i = sigma_i       if equation_filter('hospitalization') else sigma_i * 0
    _gamma_m = gamma_m       if equation_filter('recovery')        else gamma_m * 0
    _aging    = params.aging  if equation_filter('Aging')           else params.aging * 0
    if equation_filter('Aging') and not equation_filter('births'):
        _aging[15][15] = 0#- params.births[0][15]
        _aging[31][31] = 0#- params.births[16][31]
    _births   = params.births if equation_filter('births')          else params.births * 0
    _delta_s = delta_s       if equation_filter('quarantine')      else delta_s * 0
    _rho     = rho           if equation_filter('quarantine')      else rho * 0
    _pi_a    = pi_a          if equation_filter('enter-icu')       else pi_a * 0
    _pi_b    = pi_b          if equation_filter('exit-icu')        else pi_b * 0
    _pi_c    = pi_c          if equation_filter('recovery-h')      else pi_c * 0
    _psi    = psi            if equation_filter('recovery-h')      else psi * 0

    _cfr_icu = 0 # undocumented flow (not in diagram) from icu_b to recovered if this isnt 0

    dsdt = -_lambda * s - _delta_s * s + _aging.dot(s) + _births.dot(pop) + _rho * v
    dvdt = _delta_s * s - rho * v + _aging.dot(v)
    dedt = (1-_delta_e) * _lambda * s - _epsilon * e + _aging.dot(e)
    dqdt = _delta_e * _lambda * s - _epsilon * q + _aging.dot(q)
    dadt = _epsilon * e - _gamma_p * a + _aging.dot(a)
    dwdt = _epsilon * q - _gamma_p * w + _aging.dot(w)
    dbdt = (1-_sigma_s) * _gamma_p * a - _gamma_m * b + _aging.dot(b)# - gamma_d * b
    dcdt = _sigma_s * _gamma_p * a - _gamma_s * c + _aging.dot(c)
    dydt = (1-_sigma_s) * _gamma_p * w - _gamma_m * y + _aging.dot(y)
    dzdt = _sigma_s * _gamma_p * w - _gamma_s * z + _aging.dot(z)
    # dgdt = gamma_d * b - gamma_i * g + _aging.dot(g)
    dhdt =  (1-_sigma_i) * _gamma_s * (c+z) - _psi * h + _aging.dot(h)
    dicuadt = _sigma_i * _gamma_s * (c+z) - _pi_a * icua + _aging.dot(icua)
    dicubdt = _pi_a * icua - _pi_b * icub + _aging.dot(icub)
    dicucdt =(1-_cfr_icu) * _pi_b * icub - _pi_c * icuc +  _aging.dot(icuc)
    drdt = _gamma_m * (b+y) + _psi * h + _cfr_icu * _pi_b * icub + _pi_c * icuc + _aging.dot(r) # + gamma_i * g
    dddt =  _cfr_icu * _pi_b * icub # to keep deaths as absorbing state and pop size constant, moving all hosp back to recovered/removed state

    res = np.array([
        dsdt, dvdt, dedt, dqdt, dadt, dwdt, dbdt, dcdt, dydt, dzdt,# dgdt,
        dhdt, dicuadt, dicubdt, dicucdt, drdt, dddt
    ])
    return res if not do_index_remap else np.array([res[params.tuite_order.index(params.our_order[i])] for i in range(16)])

if __name__ == "__main__":
    print(all(derivative(params.initial_population, lambda x: True, False)[3]\
        ==derivative(params.our_initial_population, lambda x: True, True)[2]))
