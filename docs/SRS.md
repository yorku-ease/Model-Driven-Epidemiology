# System Requirements Specification

for a table of contents, see the icon in the top left of the document ![image](https://user-images.githubusercontent.com/43907476/146665040-57daa44b-8263-4a69-ac20-4a5433eecd7b.png) . For a version history of this document, see the commit history.

## Introduction

The following document presents the Requirements for the epidemiological modeling software to be develloped. It consists of mainly 2 sections. The first is the funtional requirements, referring to the capabilities of the system, in other words the features. Secondly, nonfunctional requirements referring to the way some features are implemented. Essentially they form respectively the "What" and "How" of the project.

### Project Objective

The objective is to provide the client with a framework to develop, view, compare, modify and test epidemiological models. This will be done using either the Domain Specific Language we will develop, or the graphical editor/viewer.

The training of models has to do with other software using machine learning which will not be part of this project, but a certain integration between the developped software and the training software has to be made available to minimize complexity during that transfer step from our representation of a model to a trainable representation. Visualizing predictions (curves, time series) will be part of the solution, but not training.

### Context

This is a masters project at Polytechnique Montreal, currently undertaken by a single student for a single "client", an epidemiologist, professor at the University of Montreal. There are no business collaborators. The project revolves mostly about implementing the required features, but partially about researching how to best do so following Model Driven practices.

## 1 Functional Requirements

### 1.1 Domain Specific Language & Compiler

1.1.1. The DSL must support creating model objects

1.1.2 The DSL must support defining model object properties

1.1.3 The DSL must support removing model objects

1.1.4 The DSL must support defining model object connections

1.1.5 A valid DSL file must correspond to a model definition

1.1.6 A valid model must be serializable as a DSL file

1.1.7 The DSL compiler must have an explanation for the invalidity of a DSL file

1.1.8 The DSL must support comments after the character `#` is found outside a string literal on a line

1.1.9 The DSL must use a reserved keyword `Population` to refer to a Population

1.1.10 The DSL must use a reserved keyword `Batch` to refer to constant flow from a population to another

1.1.11 The DSL must use a reserved keyword `Rate` to refer to a flow from a population to another proportional to the origin population size

1.1.12 The DSL must use a reserved keyword `Mix` to refer to a flow from a population to another proportional to the product of each population

1.1.13 The DSL must support labeling each `Population` with string literals

1.1.14 The DSL must support only named instances of `Population` using the word found after the `Population` keyword (C-Style definitions)

1.1.15 The DSL must support names matching the regular expression `[a-zA-Z_][a-zA-Z0-9_]*` (letter or underscore followed with any combination of letter, number or underscore)

### 1.2 Graphical Editor

1.2.1 The graphical editor must support to creating model objects

1.2.2 The graphical editor must support to removing model objects

1.2.3 The graphical editor must support to connecting model objects

1.2.4 The graphical editor must support to editing model object properties

1.2.5 The graphical editor must support persistence of each change after a save, including item positioning

1.2.6 The graphical editor must provide an organized default view for models generated with the DSL

1.2.7 The grapical editor must provide undo-redo stacks of at least 50 actions

## 2 Nonfunctional Requirements

### 2.1 Performance

2.1.1 Generating a model using the DSL file definition must take at most 1 second

2.1.2 Opening a model with the graphical editor must take at most 10 seconds

2.1.3 Saving a model with the graphical editor must take at most 1 second

2.1.4 Applying a property edition in the graphical editor must take at most 1 second

2.1.5 Applying a model object creation in the graphical editor must take at most 1 second

2.1.6 Applying a model object deletion in the graphical editor must take at most 1 second

2.1.7 Applying a model object connection in the graphical editor must take at most 1 second

### 2.2 Development Tools

#### 2.2.1 The Metamodel must be developped using [EMF](https://www.eclipse.org/modeling/emf/)

#### 2.2.2 The Domain Specific Language must be developped using [XText](https://www.eclipse.org/Xtext/index.html)

#### 2.2.3 The Graphical Interface must be developped using [Sirius](https://www.eclipse.org/sirius/overview.html)

### 2.3 Tests

### 2.4 Developper Documentation

### 2.5 User Documentation
