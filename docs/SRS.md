# System Requirements Specification

For a table of contents, see the icon in the top left of the document ![image](https://user-images.githubusercontent.com/43907476/146665040-57daa44b-8263-4a69-ac20-4a5433eecd7b.png).

For a version history of this document, see the commit history.

[Glossary](https://github.com/polytechnique-ease/Model-Driven-Epidemiology/blob/main/docs/Glossary.md)

## Introduction

The following document presents the Requirements for the epidemiological modeling software to be develloped. It consists of mainly 2 sections. The first is the funtional requirements, referring to the capabilities of the system, in other words the features. Secondly, nonfunctional requirements referring to the way some features are implemented. Essentially they form respectively the "What" and "How" of the project.

### Project Objective

The objective product is a framework to develop, view, compare, modify, train and test epidemiological models. This will be done using either a Domain Specific Language or a graphical editor/viewer, aswell as a model training environment.

### Context

This is a masters project at Polytechnique Montreal, currently undertaken by a single student for a single "client", an epidemiologist, professor at the University of Montreal. There are no business collaborators. The project revolves mostly about implementing the required features aswell as how to best do so following Model Driven practices.

## 1 Functional Requirements

### 1.1 Modeling

1.1.1 The model must contain `Compartment` objects

1.1.2 The model must contain `Flow` objects

1.1.3 `Flow` objects can be of kind `Batch`, `Rate` or `Contact`

1.1.4 `Batches` are `Flows` from population 1 to population 2 with constant value

1.1.5 `Rates` are `Flows` from population 1 to population 2 with value directly proportional to the size of population 1

1.1.6 `Contacts` are `Flows` from population 1 to population 2 with value proportional to the product of the size of population 1 and the size of the `Mixed` population 3

1.1.7 The metamodel supports extensions

1.1.8 Extensions refer to polymorphic alternatives to existing objects in higher level metamodels (concretisations)

1.1.9 It is possible to create new types of flows and they behave as expected with composed compartments

1.1.10 It is possible to create new types of compartments and they behave as expected with regards to composability

#### 1.1.1 extension usage

1.1.1.1 Extensions are made available by placing them in the metamodel extensions folder

1.1.1.2 Extensions are marked as used or unused for each model

1.1.1.3 The extended metamodel is generated when opening a graphical editor view of a model

1.1.1.4 The extended metamodel is generated when opening a DSL editor view of a model

1.1.1.5 The extended metamodel is generated when generating artifacts for a model


### 1.2 Domain Specific Language

1.2.1. The DSL must support creating model objects

1.2.2 The DSL must support defining model object properties (attributes, references)

1.2.3 A valid DSL file must correspond to a model definition

1.2.4 A valid model must be serializable as a DSL file

1.2.5 The DSL compiler must provide explanations for invalid DSL files

1.2.6 The DSL must support comments after the character `#` is found outside a string literal on a line

1.2.7 The DSL must use a reserved keyword `Compartment` to refer to a Compartment (base type)

1.2.8 the DSL must use reserved keywords to refer to any concrete type defined by extensions, for example: `Batch`, `Rate`, `Contact`, etc.

1.2.9 The DSL must support labeling each object with variable names matching the regular expression `[a-zA-Z_][a-zA-Z0-9_]*` (letter or underscore followed with any combination of letter, number or underscore)


### 1.3 Graphical Editor

1.3.1 The graphical editor must support to creating model objects

1.3.2 The graphical editor must support to removing model objects

1.3.3 The graphical editor must support to connecting model objects

1.3.4 The graphical editor must support to editing model object properties (attributes and references)

1.3.5 The graphical editor must support persistence of each change after a save, including item positioning

1.3.6 The graphical editor must provide an organized default view for models created without the graphical editor

1.3.7 The grapical editor must provide persistent undo-redo stacks of at least 50 actions

1.3.8 The graphical editor must provide a compare view accompanied with an automatically generated textual log of changes


### 1.4 Model Views

1.4.1 A mathematical equations view is available using a text editor

1.4.2 A list of members view is available using Eclipse

1.4.3 A graphical view is available using [Sirius](https://www.eclipse.org/sirius/overview.html)

1.4.4 A DSL view is available using a text editor

1.4.5 An XML view available using a text editor

1.4.6 *An expanded graphical view is available using **?*** TBD during a meeting if it is useful


## 2 Nonfunctional Requirements


### 2.1 Development Tools

#### 2.1.1 The Metamodel must be developped using [EMF](https://www.eclipse.org/modeling/emf/)

#### 2.1.2 The Domain Specific Language must be developped using [XText](https://www.eclipse.org/Xtext/index.html)

#### 2.1.3 The Graphical Interface must be developped using [Sirius](https://www.eclipse.org/sirius/overview.html)


### 2.2 Developper Documentation


### 2.3 User Documentation
