# EpiMDE Model Repair Prompt

## Task

Fix exactly **one** EMF validation error in a `.compmodel` XMI file. The model follows the EpiMDE compartmental epidemiology metamodel. Your output must be a complete, well-formed `.compmodel` XML that preserves all existing valid elements and fixes only the described error.

## Rules

1. **Fix one error at a time.** Never change more than what the single reported error requires.
2. **Preserve everything else.** Do not rename, reorder, or restructure elements that are not part of the error.
3. **Output the full corrected `.compmodel` XML** (not a diff or fragment), wrapped in a markdown code block with `xml` language tag.
4. **Use the correct namespace prefixes:**
   - `compartmental:` for the model namespace (`http://example.com/compartmentalmodel`)
   - `xmi:` for XMI (`http://www.omg.org/XMI`)
   - `xsi:` for XML Schema Instance (`http://www.w3.org/2001/XMLSchema-instance`)
5. **Target references** use zero‑based index format: `//@compartments.X` or `//@parameters.X`.
6. **Do NOT add `seir:` namespace** to the output model. The metamodel uses `seir:` but generated models use `compartmental:`.

## Structural Schema (Metamodel)

The phase 1 metamodel defines the allowed structure. Key mapping: the metamodel uses `seir:SEIRModel` as root and `seir:` namespace prefix; your output must use `compartmental:CompartmentalModel` with `compartmental:` prefix.

### Root Element

```xml
<compartmental:CompartmentalModel
    xmlns:compartmental="http://example.com/compartmentalmodel"
    xmlns:xmi="http://www.omg.org/XMI"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmi:version="2.0"
    totalPopulation="..."    <!-- optional -->
    globalBirthRate="..."    <!-- optional -->
    globalDeathRate="...">   <!-- optional -->
```

### Parameters

```xml
<parameters name="β" expression="0.2" type="CONSTANT"
    unit="per day" description="Transmission rate" />
```

- `name`: Required. Supports Unicode / Greek letters.
- `type`: One of `CONSTANT`, `VARIABLE`, `EXPRESSION`. Default `CONSTANT`.
- `expression`: Numeric value (CONSTANT/VARIABLE) or formula (EXPRESSION).
- `unit`: Optional physical unit.
- `description`: Optional human-readable explanation.

### Compartments

```xml
<compartments PrimaryName="Susceptible" population="1000"
    SecondaryName="Adults">
    <outgoingFlows .../>
</compartments>
```

- `PrimaryName`: Required. Epidemiological state (e.g. Susceptible, Exposed, Infectious, Recovered, Hospitalized, Dead, Removed).
- `population`: Optional initial count (float).
- `SecondaryName`: Optional stratification descriptor.
- `outgoingFlows`: Zero or more child flow elements.

### Flows

**RateFlow** — rate‑based transition (progression, recovery, death):

```xml
<outgoingFlows xsi:type="compartmental:RateFlow"
    rate="0.1"
    target="//@compartments.3"
    description="Progression to infectious" />
```

Parametric variant (preferred):

```xml
<outgoingFlows xsi:type="compartmental:RateFlow"
    rateParameter="//@parameters.2"
    target="//@compartments.3" />
```

- `xsi:type`: **Required.** Must be `"compartmental:RateFlow"` or `"compartmental:ContactFlow"`.
- `rate`: Numeric value (float). Use `0.0` when `rateParameter` is set.
- `rateParameter`: Reference to a Parameter (zero‑based index).
- `target`: **Required.** Reference to target compartment (`//@compartments.X`). Must NOT point to the source compartment (no self‑loops for standard epidemiological flows).

**ContactFlow** — contact‑dependent transmission:

```xml
<outgoingFlows xsi:type="compartmental:ContactFlow"
    contactRate="0.01"
    contactCompartment="//@compartments.3"
    target="//@compartments.1"
    description="Transmission from Infectious" />
```

Parametric variant:

```xml
<outgoingFlows xsi:type="compartmental:ContactFlow"
    contactRateParameter="//@parameters.0"
    contactCompartment="//@compartments.3"
    target="//@compartments.1" />
```

- `contactCompartment`: **Required.** The compartment whose population drives transmission (usually Infectious).
- `contactRate`: Numeric value (float).
- `contactRateParameter`: Reference to a Parameter.

### External Sources (births/recruitment)

```xml
<externalSources name="Births" rate="0.0001"
    targetCompartment="//@compartments.0" />
```

### External Sinks (deaths)

```xml
<externalSinks name="Deaths" rate="0.0001"
    sourceCompartment="//@compartments.0" />
```

## Common Validation Errors and Fixes

### 1. Flow missing `xsi:type`

**Error:** `outgoingFlows` element has no `xsi:type` attribute.

**Fix:** Add `xsi:type="compartmental:RateFlow"`. If the flow represents infection transmission (susceptible → exposed), use `xsi:type="compartmental:ContactFlow"` and add a `contactCompartment` attribute.

### 2. Flow missing rate attribute

**Error:** `RateFlow` has no `rate` or `rateParameter`.

**Fix:** Add `rate="0.0"` as a numeric placeholder, or reference an existing parameter via `rateParameter="//@parameters.X"` if a matching parameter exists.

### 3. Flow missing `contactCompartment`

**Error:** `ContactFlow` has no `contactCompartment`.

**Fix:** Add `contactCompartment="//@compartments.X"` referencing the Infectious compartment (or the appropriate transmission source).

### 4. Self‑referencing flow

**Error:** `target="//@compartments.X"` where X is the index of the source compartment.

**Fix:** Either remove the self‑loop (if it has no epidemiological meaning) or retarget to the correct downstream compartment.

### 5. Garbled parameter name

**Error:** Parameter name contains paper text, section headers, newlines, or otherwise non‑epidemiological identifiers.

**Fix:** Replace `name` with a proper epidemiological parameter symbol or short name derived from context (e.g., β for transmission rate, γ for recovery rate, μ for death rate). Preserve `expression` value if valid.

### 6. Garbled compartment name

**Error:** Compartment `PrimaryName` contains paper excerpts, multi‑line text, or prose instead of a state name.

**Fix:** Replace with the correct epidemiological compartment name (e.g., Susceptible, Exposed, Infectious, Recovered, Hospitalized, Dead, Removed).

### 7. Invalid parameter reference

**Error:** `rateParameter`, `contactRateParameter` points to a parameter index that does not exist or is out of range.

**Fix:** Update the index to reference an existing valid parameter, or remove the attribute and use a numeric `rate` instead.

### 8. Invalid compartment reference

**Error:** `target`, `contactCompartment`, `sourceCompartment`, or `targetCompartment` points to a compartment index that does not exist.

**Fix:** Update the index to reference an existing compartment.

### 9. Flow type mismatch

**Error:** A ContactFlow is used where a RateFlow is expected (e.g., progression flows), or vice versa.

**Fix:** Change the `xsi:type` to the correct type and adjust attributes accordingly.

### 10. Missing `target` on flow

**Error:** `outgoingFlows` has no `target` attribute.

**Fix:** Add `target="//@compartments.X"` pointing to the downstream compartment.

## Reference Files

- Phase 1 metamodel JSON (structural schema): `phase 1/metamodel_epidemiology.json`
- Valid reference model: `Compartmental/CompartmentalModel/covid.compmodel`
- Current (broken) model: provided below

## Input: Current Model XML

```xml
{CURRENT_MODEL_XML}
```

## Input: Validation Error

```
{VALIDATION_ERROR}
```

## Output Format

Respond with a single markdown code block containing the **complete** corrected `.compmodel` XML. Include the `<?xml ...?>` declaration. Do not add any explanatory text before or after the code block.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<compartmental:CompartmentalModel ...>
    ...
</compartmental:CompartmentalModel>
```
