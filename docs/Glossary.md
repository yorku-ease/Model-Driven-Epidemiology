[E](#e), [M](#m), [S](#s), [X](#x)

---

# E
## [Eclipse Modeling Framework]
- Used to define metamodels and create models, [Sirius](#sirius) & [XText](#xtext) make use of the framework

---

# M

## Metamodel
- Represents a concept, a way to describe all models of some kind
- Provides restrictions, relation types, hierarchy
- In the EMF world, its the base element of the modeling process, a modeling project defines at metamodel, but can also inherit from another metamodel to extend it, in this case, "The" metamodel is usually seen as the result after extension, rather than either project
- In epidemiological modeling, refers to the general way we describe epidemiological models, a basic metamodel may contain:
  - an `Epidemic` concept
  - a `Compartment` concept
  - a `Flow` concept
  - `Batch`, `Rate` & `Mix` concepts as described [here](https://github.com/polytechnique-ease/Model-Driven-Epidemiology/blob/main/docs/SRS.md#11-modeling)
  - Additional types of flows
  - Additional types of Compartments

## Model
- Used to represent our understanding of systems, for example, an epidemiological model is used to model epidemics as we understand them
- Can be abstract, for example a [metamodel](#metamodel), so it requires concretions to represent something concrete, though it would be better to avoid naming such models "models" instead of "metamodels"
- In an AI/ML context, is often used interchangeably to describe:
  - metamodels, such as LSTMs, Transformers, etc
  - untrained models, or in other words, configurations, such as a 4 layer convolutional network
  - a trained model, such as the same 4 layer conv. net. trained on Imagenet
- In epidemiological modeling:
  - a data-free representation of an epidemic, which we can specify later with real world data and tune accordingly, this is the type of model we can instantiate using our metamodel through EMF.
  - OR a trained model which only requires input values to produce a graph meant to fit the epidemic for a population over time, this will be created from an untrained model created using our EMF platform and training it inside a 
  - OR (very simplistic) the prediction themselves (the graph) is usually what the general population might call a model

---

# S

## [Sirius](https://www.eclipse.org/sirius/)
- A tool used to define metamodels and create developpement tools for them, such as a graphical editor

---

# X
## [XText](https://www.eclipse.org/Xtext/index.html)
- A tool used to define [Domain Specific Languages](https://en.wikipedia.org/wiki/Domain-specific_language)
