# Model-Driven-Epidemiology
Model Driven Engineering framework for developing Epidemiological models (Masters project)

## How to run (developer mode)

- install [obeo designer](https://www.obeodesigner.com/en/)
- run it and select your [workspace folder](https://github.com/yorku-ease/Model-Driven-Epidemiology/tree/main/src/workspace) as the workspace path, wherever you cloned the repo
- create environment variable 'epimodelExtensionsFolder' and set the value to your [jars folder](https://github.com/yorku-ease/Model-Driven-Epidemiology/tree/main/metamodel-jars)
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
