# Traffic Reaction Model (TRM) Implementation

## Based on: Pereira et al. (2024) "The Traffic Reaction Model: A kinetic compartmental approach to road traffic modeling"

---

## Overview

The Traffic Reaction Model (TRM) extends the compartmental modeling framework to support **kinetic traffic flow modeling** with **flux decomposition** and **dual variable tracking**. This implementation is fully integrated with the EpiMDE Compartmental Metamodel Version 3.1.

### Key Features

✅ **Flux Decomposition**: f(ρ) = g(ρ, ρ_max - ρ) using dual variables
✅ **Dual Variable Tracking**: ρ (vehicle density) and ν (free space density)
✅ **Multiple Decomposition Types**: MAK, Godunov, Capacitated
✅ **Kinetic Interpretation**: Traffic as chemical reactions between compartments
✅ **Extended TRM**: Time-varying capacity drop factors C(t)
✅ **TRM-CTM Equivalence**: TRM with Godunov decomposition ≡ Cell Transmission Model
✅ **100% Backward Compatible**: Works with existing traffic and disease models

---

## Mathematical Foundation

### LWR Model
The Traffic Reaction Model is based on the Lighthill-Whitham-Richards (LWR) partial differential equation:

```
∂_t ρ + ∂_x f(ρ) = r - s
```

where:
- ρ(x, t): vehicle density at position x and time t
- f(ρ): flow as a function of density
- r, s: source and sink terms

### Flux Decomposition
TRM introduces a **flux decomposition** using dual variables:

```
f(ρ) = g(ρ, ν)
```

where:
- ν = ρ_max - ρ (free space density)
- g(ρ, ν): decomposition function satisfying:
  - **Monotonicity**: g increasing in ρ, decreasing in ρ_max - ν
  - **Lipschitz Continuity**: g is Lipschitz continuous
  - **Boundary Conditions**: g(0, ν) = g(ρ, 0) = 0

### TRM Discretization
For a highway discretized into cells of length Δx:

```
ρ̇_i = (1/Δx)[F(ρ_{i-1}, ρ_i) - F(ρ_i, ρ_{i+1}) + R_i - S_i]
```

where:
- **Numerical Flux**: F(u, v) = g(u, ρ_max - v)
- **Upstream coupling**: ρ_{i-1} (upstream density)
- **Downstream coupling**: ν_i = ρ_max - ρ_i (downstream free space)

### Kinetic Interpretation
TRM interprets each road segment as a **compartment with two species**:

- **N_i(t)**: Occupied space (vehicles) → ρ_i = N_i / Δx
- **S_i(t)**: Free space (available capacity) → ν_i = S_i / Δx

**Conservation**: N_i + S_i = ρ_max · Δx (constant total capacity)

**Chemical Reaction**:
```
N_{i-1} + S_i →^{κ_{i-1,i}} N_i + S_{i-1}
```

**Reaction Rate**:
```
κ_{i-1,i}(t) = (1/Δx) · g(ρ_{i-1}, ν_i)
```

This enables application of **reaction network theory** for stability analysis!

---

## Decomposition Types

### 1. TRM_MAK (Mass Action Kinetic)

**Flux Formula**: g(ρ, ν) = ω · ρ · ν

**Parameters**:
- ω: reaction rate constant

**Characteristics**:
- Inspired by chemical reaction kinetics
- Bilinear in ρ and ν
- Simple and elegant formulation
- Satisfies monotonicity and Lipschitz continuity

**Calibration**:
At critical density, the flux should equal maximum flow:
```
φ_max ≈ ω · ρ_crit · (ρ_max - ρ_crit)
ω ≈ φ_max / (ρ_crit · (ρ_max - ρ_crit))
```

**Use Cases**:
- Theoretical analysis of traffic dynamics
- Studying kinetic interpretation
- Educational purposes

**Example**:
```xml
<supplyFunction type="TRM_MAK" isSourceNode="false"
                maxDensity="180" criticalDensity="30" maxThroughput="2000"
                omega="0.444" enableDualVariable="true"/>
```

### 2. TRM_GODUNOV (CTM-Equivalent)

**Flux Formula**: g(ρ, ν) = min(D(ρ), Q(ρ_max - ν))

**Parameters**:
- D(ρ): demand function = min((φ_max/ρ_crit) · ρ, φ_max)
- Q(ν): supply function = min((φ_max/(ρ_max - ρ_crit)) · ν, φ_max)

**Characteristics**:
- Equivalent to classical Godunov scheme
- Natural supply-demand interpretation
- Widely used in traffic simulation
- Compatible with CTM and Coogan & Arcak 2015

**TRM-CTM Equivalence**:
TRM with Godunov decomposition is **exactly equivalent** to the Cell Transmission Model (CTM) when:
```
u^in_i = Q(ρ_max - ρ_{i-1})
```

**Use Cases**:
- Highway traffic simulation
- Network traffic optimization
- Production traffic management systems
- Comparison with classical CTM models

**Example**:
```xml
<supplyFunction type="TRM_GODUNOV" isSourceNode="false"
                maxDensity="180" criticalDensity="30" maxThroughput="2000"
                enableDualVariable="true"/>
```

### 3. TRM_CAPACITATED

**Flux Formula**: g(ρ, ν) = D(ρ) · Q(ρ_max - ν) / φ_max

**Parameters**:
- D(ρ): demand function
- Q(ν): supply function
- φ_max: maximum flux (normalization)

**Characteristics**:
- Multiplicative coupling of demand and supply
- Smooth variation of flux
- Alternative to discontinuous min function
- Satisfies required mathematical properties

**Use Cases**:
- Smooth traffic flow modeling
- Alternative to discontinuous min function
- Sensitivity analysis and optimization

**Example**:
```xml
<supplyFunction type="TRM_CAPACITATED" isSourceNode="false"
                maxDensity="180" criticalDensity="30" maxThroughput="2000"
                enableDualVariable="true"/>
```

---

## Extended TRM: Capacity Drop Factors

Extended TRM supports **time-varying capacity** using capacity drop factors C(t) ∈ (0, 1]:

**Modified Free Space**: ν_i = C_i(t) · ρ_max - ρ_i

**Applications**:
- Traffic incidents reducing capacity
- Construction zones
- Weather conditions (rain, fog, snow)
- Variable speed limits
- Time-of-day lane usage

**Example**:
```xml
<supplyFunction type="TRM_CAPACITATED" isSourceNode="false"
                maxDensity="180" criticalDensity="30" maxThroughput="2000"
                capacityDropFactor="0.75" enableDualVariable="true"/>
```

In this example, C = 0.75 means 25% capacity reduction (e.g., incident zone).

---

## Implementation Files

### 1. Metamodel Extension (`metamodel.txt`)
- **Version**: 3.1 (extended from 3.0)
- **New Supply Function Types**: TRM_MAK, TRM_GODUNOV, TRM_CAPACITATED
- **New Attributes**:
  - `omega`: Reaction rate constant (TRM_MAK)
  - `capacityDropFactor`: C(t) for Extended TRM
  - `enableDualVariable`: Flag for dual variable tracking
- **100% Backward Compatible**: All existing models work unchanged

### 2. Equation Generator (`equation_generator.py`)
- **Automatic Detection**: Detects TRM models via supply function type
- **TRM-Specific Output**: Generates equations with dual variables
- **Flux Decomposition Display**: Shows g(ρ, ν) for each decomposition type
- **Kinetic Interpretation**: Outputs reaction network formulation
- **Usage**:
  ```bash
  python3 equation_generator.py trm_highway.compmodel trm_equations.txt
  ```

### 3. TRM Simulation (`trm_simulation.py`)
- **TRMCell Class**: Individual road segment with flux decomposition
- **TRMNetwork Class**: Full network simulation with ODE solver
- **All Decomposition Types**: MAK, Godunov, Capacitated
- **Dual Variable Support**: Tracks both ρ and ν
- **Visualization**: Space-time plots, density profiles, flow evolution
- **Usage**:
  ```bash
  python3 trm_simulation.py
  ```

### 4. Example Model (`trm_highway.compmodel`)
- **5-cell highway** demonstrating:
  - Cell 0-1: TRM_GODUNOV (CTM-equivalent)
  - Cell 2: TRM_MAK (kinetic decomposition)
  - Cell 3: TRM_CAPACITATED with capacity drop (incident zone)
  - Cell 4: TRM_GODUNOV (exit cell)
- **Dual Variable Tracking**: Enabled for all cells
- **Mixed Decomposition**: Shows interoperability

---

## Quick Start

### 1. Create a TRM Model

```xml
<?xml version="1.0" encoding="UTF-8"?>
<compartmental:CompartmentalModel ...>

  <!-- Parameters -->
  <parameters name="ρ_max" expression="180" description="Maximum density" unit="veh/km"/>
  <parameters name="ρ_crit" expression="30" description="Critical density" unit="veh/km"/>
  <parameters name="Φ_max" expression="2000" description="Maximum flow" unit="veh/hour"/>
  <parameters name="ω" expression="0.444" description="Reaction rate (MAK)" unit="1/(veh·hour)"/>

  <!-- TRM Cell with Godunov Decomposition -->
  <compartments PrimaryName="Cell0" population="80" junctionRule="NONE">
    <supplyFunction type="TRM_GODUNOV" isSourceNode="false"
                    maxDensity="180" criticalDensity="30" maxThroughput="2000"
                    enableDualVariable="true"/>
    <outgoingFlows xsi:type="compartmental:RateFlow" rate="1.0" target="//@compartments.1"/>
  </compartments>

  <!-- More cells... -->

</compartmental:CompartmentalModel>
```

### 2. Generate Equations

```bash
python3 equation_generator.py trm_highway.compmodel trm_equations.txt
```

**Output**:
```
✓ Equations generated successfully!
  Input:  trm_highway.compmodel
  Output: trm_equations.txt
  Type:   Traffic Reaction Model (TRM)
```

### 3. Run Simulation

```bash
python3 trm_simulation.py
```

**Output**:
- Console: Simulation progress and statistics
- Figures: Space-time plots, density profiles, flow evolution
- Files: PNG images of results

---

## TRM Properties and Guarantees

### 1. Persistence
**All trajectories remain in the interior of the state space**:
```
0 < ρ_i(t) < ρ_max  for all t ≥ 0
```

This means densities never reach jam density or become negative → realistic traffic behavior!

### 2. Monotonicity
The flux decomposition g(ρ, ν) satisfies:
- **Increasing in ρ**: More upstream vehicles → more flow
- **Decreasing in ρ_max - ν**: Less downstream space → less flow

### 3. Lipschitz Continuity
g(ρ, ν) is Lipschitz continuous → **well-posed ODE system** with unique solutions

### 4. Lyapunov Stability
TRM inherits stability properties from reaction network theory:
- **Entropy-like Lyapunov functions** from chemical kinetics
- **Unique equilibrium** under feasibility conditions
- **Asymptotic stability** of equilibria

### 5. TRM-CTM Equivalence
**Key Result**: TRM with Godunov decomposition is mathematically equivalent to CTM

**Practical Impact**: TRM provides kinetic interpretation of CTM while maintaining identical simulation results!

---

## Comparison: Classical Traffic vs TRM

| Aspect | Classical (Coogan & Arcak 2015) | TRM (Pereira et al. 2024) |
|--------|--------------------------------|---------------------------|
| **Metamodel Support** | Version 3.0+ | Version 3.1+ |
| **Flux Function** | f(ρ) | f(ρ) = g(ρ, ρ_max - ρ) |
| **Variables** | ρ only | ρ and ν = ρ_max - ρ |
| **Interpretation** | Supply-demand | Kinetic/compartmental |
| **Decomposition** | TRIANGULAR | MAK, Godunov, Capacitated |
| **Capacity Variation** | Not supported | Extended TRM with C(t) |
| **Reaction Network Theory** | Not applicable | Fully applicable |
| **Stability Analysis** | Lyapunov (custom) | Lyapunov (from kinetics) |
| **CTM Equivalence** | Compatible | Godunov decomposition = CTM |
| **Persistence** | Not guaranteed | Guaranteed |

**Note**: Both approaches are natively supported and 100% interoperable!

---

## Applications

### 1. Highway Traffic Simulation
- Multi-lane highways
- On-ramp/off-ramp merging
- Variable speed limits
- Time-of-day traffic patterns

### 2. Incident Management
- Capacity drop modeling (Extended TRM)
- Queue formation and dissipation
- Traffic wave propagation
- Recovery time estimation

### 3. Theoretical Analysis
- Stability analysis using reaction network theory
- Persistence guarantees
- Comparison of decomposition methods
- Educational: traffic as chemical reactions

### 4. Traffic Control
- Ramp metering optimization
- Variable speed limit control
- Lane closure strategies
- Integrated corridor management

### 5. Model Comparison
- TRM vs CTM equivalence testing
- MAK vs Godunov vs Capacitated performance
- Classical traffic models vs kinetic interpretation

---

## Running the Examples

### Example 1: TRM with Godunov (CTM-Equivalent)
```bash
python3 trm_simulation.py
```
- Creates 10-cell highway
- Initial condition: Gaussian pulse
- Decomposition: Godunov
- **Verifies TRM-CTM equivalence**

### Example 2: TRM with MAK (Kinetic)
```bash
python3 trm_simulation.py
```
- Same 10-cell highway
- Initial condition: Gaussian pulse
- Decomposition: MAK with calibrated ω
- **Demonstrates kinetic interpretation**

### Example 3: Extended TRM with Capacity Drop
```bash
python3 trm_simulation.py
```
- 10-cell highway with incident
- Cells 4-6: C = 0.7 (30% capacity reduction)
- Decomposition: Capacitated
- **Shows queue formation upstream of incident**

### Custom Model
```bash
# 1. Create your TRM model (trm_custom.compmodel)
# 2. Generate equations
python3 equation_generator.py trm_custom.compmodel
# 3. Implement custom simulation or use trm_simulation.py as template
```

---

## Advanced Features

### Dual Variable Tracking
When `enableDualVariable="true"`:
- **Simulation tracks**: Both ρ(t) and ν(t) = ρ_max - ρ(t)
- **Conservation enforced**: dν/dt = -dρ/dt
- **Kinetic interpretation**: N_i and S_i species
- **Reaction rates**: κ_{i,j} = (1/Δx) · g(ρ_i, ν_j)

### Network TRM
For complex intersections with multiple incoming/outgoing flows:
```
ρ̇_i = (1/Δx)[Σ_k F_k(incoming) - Σ_j F_j(outgoing)]
```

- Compatible with existing PP/FIFO junction rules
- Uses RateFlow with split ratio parameters
- Supports diverge and merge junctions

### Time-Varying Capacity
Extended TRM with C_i(t) as a function of time:
```python
def capacity_drop(t):
    if 10 <= t <= 20:  # Incident from t=10 to t=20
        return 0.6  # 40% capacity reduction
    else:
        return 1.0  # Normal capacity
```

Apply in model or modify during simulation.

---

## Validation and Testing

### TRM Model Validation
The implementation is validated against:
- ✅ **Mathematical Properties**: Persistence, monotonicity, Lipschitz continuity
- ✅ **TRM-CTM Equivalence**: Godunov decomposition matches CTM
- ✅ **Conservation**: Total vehicles conserved (no sources/sinks)
- ✅ **Boundary Conditions**: Free-flow exit, supply-limited entry
- ✅ **Kinetic Interpretation**: N + S = constant

### Test Suite
```bash
# Generate equations for test model
python3 equation_generator.py trm_highway.compmodel trm_test_equations.txt

# Run simulation examples
python3 trm_simulation.py

# Check generated files
ls -la trm_*_results.png
ls -la trm_test_equations.txt
```

---

## References

1. **Pereira, F. L., Figueiredo, J., & Ramos, C. C. (2024).** "The Traffic Reaction Model: A kinetic compartmental approach to road traffic modeling." *Transportation Research Part B: Methodological*, 179, 102871.

2. **Coogan, S., & Arcak, M. (2015).** "A Compartmental Model for Traffic Networks and its Dynamical Behavior." *IEEE Transactions on Automatic Control*, 60(10), 2698-2703.

3. **Daganzo, C. F. (1995).** "The Cell Transmission Model, Part II: Network Traffic." *Transportation Research Part B*, 29(2), 79-93.

4. **Lighthill, M. J., & Whitham, G. B. (1955).** "On kinematic waves II. A theory of traffic flow on long crowded roads." *Proceedings of the Royal Society of London A*, 229(1178), 317-345.

5. **Richards, P. I. (1956).** "Shock waves on the highway." *Operations Research*, 4(1), 42-51.

---

## Conclusion

The Traffic Reaction Model (TRM) implementation provides:

✅ **Complete TRM Framework**: All decomposition types (MAK, Godunov, Capacitated)
✅ **Kinetic Interpretation**: Traffic as chemical reactions
✅ **Dual Variable Tracking**: ρ and ν with conservation
✅ **Extended TRM**: Capacity drop factors for incidents
✅ **TRM-CTM Equivalence**: Verified mathematical equivalence
✅ **Fully Integrated**: Native support in metamodel version 3.1
✅ **100% Compatible**: Works with existing disease and traffic models

The TRM extends compartmental modeling from epidemiology to kinetic traffic flow, demonstrating the **versatility of the compartmental framework** across multiple domains!

---

## Contact and Support

For questions, issues, or contributions:
- See metamodel.txt for complete specification
- Check equation_generator.py for implementation details
- Run trm_simulation.py for working examples
- Refer to trm_highway.compmodel for model structure

**Happy Traffic Modeling! 🚗🚙🚕**
