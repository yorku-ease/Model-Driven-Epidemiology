# Model-Driven-Epidemiology

Model Driven Engineering framework for developing Epidemiological models

[Youtube Channel](https://www.youtube.com/@Epi-model)

## How to run (developer mode)

- clone the repository and `git checkout fm-removed` or go to the [fm-removed branch](https://github.com/yorku-ease/Model-Driven-Epidemiology/tree/fm-removed) and download as zip, then unzip
- install [obeo designer](https://www.obeodesigner.com/en/)
- create environment variable `epimodelExtensionsFolder` and set the value to your [jars folder](https://github.com/yorku-ease/Model-Driven-Epidemiology/tree/main/metamodel-jars) based on where you placed the repository
- run it and select your [workspace folder](https://github.com/yorku-ease/Model-Driven-Epidemiology/tree/main/src/workspace) as the workspace path, wherever you cloned the repo
- create a new debug configuration for eclipse applications
  - name it plugin
  - set the "location" to `${workspace_loc}/../runtime`, the [runtime folder](https://github.com/yorku-ease/Model-Driven-Epidemiology/tree/main/src/runtime)
  - go to plugins (3rd panel)
    - change "launch with" to select below only
    - unselect all workspace projects
    - select
      - org.epimodel (the plugin)
      - epimodel (the metamodel)
      - my.project.design (the sirius viewpoint specification)
