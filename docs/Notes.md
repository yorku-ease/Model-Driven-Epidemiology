# Meeting Feb. 8th, mainly centered around the Models publication

Michalis/Marios: Presenting the Metamodel and a simple use isn't enough for Models. Presenting the domain requirements and transformations, then presenting our metamodel and implementation should be enough. Ideally we include "real world" examples from Simon's past publications/work as case studies.

Simon: Following Pareto's law, presenting the best 20% transformations accounts for 80% of the use cases. The main 2 transformations are 1. [Cartesian box product]() TODO & 2. [Uploading]() TODO.

(1) Intro: The problem (from an epidemiologist's POV) + which parts MDE tackles. The main part of the problem is documenting, validating and reproducing epidemiological model development, especially when trying to integrate other people's models made different tools or procedures. Simon mentions that alot of the problems are documented since he spent a long time thinking about this project and applied for funding with this problematic explained.
(2) Translation of the epidemiology domain to MDSE. Which terms and entities are represented. Dictionnary.
(3) Our implementation: DSL, Metamodel, maybe simulation environment or simulation code depending on what direction we go in.
(4) Case study of some of Simon's models & uploading

Simon mentions 2 aspects to the project: 1. Modeling, 2. Training (includes simulating), I suggest a 3rd one: Automatic model generation, I think its quite different from just modeling, but we will see.

We propose that the main drive behind the project is Reproducibility, though faster development is also good. Michalis proposes using a language server and explains a use for it but I didn't really get it and we were out of time. For me a language server has to do with the language server protocol but not sure what our DSL has to do with that.

Michalis also explains that a use for our tool could be to avoid too many "researcher degrees of freedom". Essentially to ensure we avoid "researcher overfitting", a phenomenon should appear with each combination of hyperparameter. Simon mentionned a certain phenomenon known as an Epidemic Bridge (pont épidémiologique in french has results) which means certain populations get infected through others for example when kids go back to school and get infected then transfer to their parents.

A use case for our platform could be to take 2 models developped independantly by 2 groups or people for an epidemic and reproduce them using our platform to compare them.

A goal for the platform is to increase the degree of automation in the epidemiological domain (Creation, Simulation, Validation)

Since we plan on generating models automatically using computer power, we propose "La Valorisation des ressources" a word play since it ressembles IVADO, "Institut de valorisation des données". Jokes aside, this is connected to overfitting, generating many models and picking ones with least bias (simplest structure) has great value. Simon also mentions that for example some people use different datasets for their results like hospital data that others don't, maybe we can help reconcile this debate by speeding up development process so its simpler to reason about whether or not certain datasources should be included or not.
