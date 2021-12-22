## Model
- Used to represent things, for example, an epidemiological model is used to model epidemics
- Can be abstract, for example a [metamodel](#metamodel), so it requires concretions to represent something concrete, though it would be better to avoid naming such models models instead of metamodels
- In an AI/ML context, is often used interchangeably to describe:
  - metamodels, such as LSTMs, Transformers, etc
  - untrained models, or in other words, configurations, such as a 4 layer convolutional network
  - a trained model, such as the same 4 layer conv. net. trained on Imagenet
- In epidemiological modeling, usually refers to a data-free representation of an epidemic, which we can specify later with real world data and tune accordingly

## Metamodel
- Represents a concept, a way to describe all models of some kind
- Provides restrictions, relation types, hierarchy
- In the Sirius/EMF world, its the base element of the modeling process, it can be used to define modeling tools for our metamodels, metamodels define concepts and relations
- In epidemiological modeling, refers to the general way we describe epidemiological models, a basic metamodel may contain:
  - an `Epidemic` concept
  - a `Population` concept
  - a `Flow` concept
  - `Batch`, `Rate` & `Mix` concepts as described [here](https://github.com/polytechnique-ease/Model-Driven-Epidemiology/blob/main/docs/SRS.md#11-modeling)
  - relations between `Flows` and `Populations`
