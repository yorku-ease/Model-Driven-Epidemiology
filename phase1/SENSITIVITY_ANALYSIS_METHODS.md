# Enhanced Sensitivity Analysis Methods

## Overview

The sensitivity analyzer now implements multiple established methods for comprehensive parameter sensitivity analysis:

1. **Morris Method** - One-at-a-time screening (recommended for initial analysis)
2. **Grid Search** - Systematic exploration of parameter space
3. **Random Search** - Random sampling of parameter combinations
4. **Sobol Sequences** - Quasi-random sampling for better space coverage
5. **Multi-Parameter Sensitivity** - Varying multiple parameters simultaneously

## Methods Comparison

| Method | Best For | Pros | Cons |
|--------|----------|------|------|
| **Morris** | Initial screening, many parameters | Fast, identifies important parameters, detects non-linearity | One-at-a-time, may miss interactions |
| **Grid Search** | Few parameters, systematic exploration | Complete coverage, reproducible | Exponential cost (curse of dimensionality) |
| **Random Search** | Exploration, many parameters | Simple, flexible | Less efficient than Sobol |
| **Sobol Sequences** | Global sensitivity, many parameters | Better space coverage than random | Requires scipy |

## Usage

### Command Line

```bash
# Morris method (default, recommended)
python3 analysis/sensitivity_analysis.py --method morris

# Random search
python3 analysis/sensitivity_analysis.py --method random

# Grid search
python3 analysis/sensitivity_analysis.py --method grid

# Sobol sequences
python3 analysis/sensitivity_analysis.py --method sobol

# Custom variation range
python3 analysis/sensitivity_analysis.py --method morris --variation 0.3  # ±30%
```

### Programmatic Usage

```python
from analysis.sensitivity_analysis import SensitivityAnalyzer

analyzer = SensitivityAnalyzer("model.compmodel", "Model Name")

# Morris method (recommended for screening)
morris_result = analyzer.morris_method(
    parameters={'beta': (0.000005, 0.000015), 'gamma': (0.05, 0.15)},
    n_trajectories=20
)

# Grid search
grid_result = analyzer.grid_search(
    parameters={'beta': (0.000005, 0.000015, 5), 'gamma': (0.05, 0.15, 5)}
)

# Random search
random_result = analyzer.random_search(
    parameters={'beta': (0.000005, 0.000015), 'gamma': (0.05, 0.15)},
    n_samples=100
)

# Multi-parameter sensitivity
multi_result = analyzer.multi_parameter_sensitivity(
    parameters={'beta': (0.000005, 0.000015), 'gamma': (0.05, 0.15)},
    method='morris',
    n_samples=100
)

# Generate full report
report = analyzer.generate_sensitivity_report(method='morris', variation_range=0.2)
analyzer.export_sensitivity_report("output.json", method='morris')
```

## Morris Method Details

The Morris method is recommended for initial screening because it:
- Tests each parameter individually (one-at-a-time)
- Calculates elementary effects
- Provides two metrics:
  - **μ*** (mu_star): Mean absolute effect (importance)
  - **σ** (sigma): Standard deviation (non-linearity/interactions)

**Interpretation:**
- High μ*: Parameter is important
- High σ: Parameter has non-linear effects or interactions

**Example Output:**
```json
{
  "sensitivity_indices": {
    "beta": {
      "mu": 0.85,
      "mu_star": 0.92,
      "sigma": 0.15,
      "n_samples": 20
    }
  }
}
```

## Grid Search Details

Grid search systematically explores all combinations of parameter values.

**Use when:**
- You have few parameters (2-3)
- You want complete coverage
- Computational cost is acceptable

**Example:**
```python
# 5x5 grid = 25 simulations
grid_result = analyzer.grid_search({
    'beta': (0.000005, 0.000015, 5),
    'gamma': (0.05, 0.15, 5)
})
```

## Random Search Details

Random search samples parameter combinations randomly.

**Use when:**
- You have many parameters
- You want quick exploration
- Computational budget is limited

**Example:**
```python
# 100 random samples
random_result = analyzer.random_search({
    'beta': (0.000005, 0.000015),
    'gamma': (0.05, 0.15)
}, n_samples=100)
```

## Sobol Sequences Details

Sobol sequences provide quasi-random sampling with better space coverage than pure random.

**Use when:**
- You want better coverage than random
- You have many parameters
- You can install scipy

**Requirements:**
```bash
pip install scipy
```

## Multi-Parameter Sensitivity

Tests multiple parameters simultaneously to detect interactions.

**Methods:**
- `method='random'`: Random sampling
- `method='grid'`: Grid search
- `method='sobol'`: Sobol sequences
- `method='morris'`: Morris method

**Example:**
```python
# Test beta and gamma together
result = analyzer.multi_parameter_sensitivity(
    parameters={
        'beta': (0.000005, 0.000015),
        'gamma': (0.05, 0.15)
    },
    method='random',
    n_samples=100
)
```

## Output Metrics

All methods calculate:
- **Peak Infections**: Maximum number of infectious individuals
- **Peak Time**: Time to reach peak infections
- **Total Cases**: Cumulative number of cases
- **Final Recovered**: Number recovered at end of simulation

## Recommendations

1. **Start with Morris method** for initial screening
2. **Use grid search** if you have 2-3 parameters and want complete coverage
3. **Use random/Sobol** for exploration with many parameters
4. **Use multi-parameter** to detect interactions

## Dependencies

**Required:**
- None (basic functionality works without numpy)

**Recommended:**
```bash
pip install numpy    # For efficient calculations
pip install scipy    # For Sobol sequences
```

## Limitations

- Currently uses simplified SEIR simulation
- For complex models, integrate with actual model simulator
- Grid search becomes expensive with many parameters (curse of dimensionality)
