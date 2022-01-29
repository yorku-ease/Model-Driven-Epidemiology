# Why do we need versions?

We need to keep track, from an historical viewpoint, through the use of a version history, the evolution of our representation of an epidemiological model. By keeping the changes to a minimum (not renaming things for example) the equations and graphs from before and after an intervention/transformation should be coherent.

# What is a version?

The suggested approach is to represent model compositions as a sequential list of actions, a version would be a checkpoint in that list, the latest version would simply be the product of applying every single action. An "undo" would be considered an action like any other so you could have a list like:

- +A
- checkpoint X
- +B
- checkpoint Y
- -B
- checkpoint Z

States Z and X are identical but that's fine, we just need to make sure undoing actions is part of the saved actions as it might make sense. (suppose someone picks a 3 risk group approach then scales it back down to 2 groups).

# Implications

This just means we don't need to model what transitions are applied to the model so much, as at any point we are dealing with a whole model, not a model + some transformations, every time we are asked to produce the equations or graphs we apply the same steps as if no "version" had ever been created.

Most likely when building the artifacts for such a model we would build one per version and assume if there is no "version" on top of the stack we still output the current model. By artifacts we mean graphs, equations, etc.
