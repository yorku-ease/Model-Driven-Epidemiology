# A key part of the project is the ability to automatically train models since they contain thousands of parameters

## Why are there thousands of parameters?

The reason models contain so many parameters is because we form tensors of parameters of the same dimensions as the model dimensions.

An SEIR model might have 20 flows, but for 2 vaccination groups, 2 risk groups, 2 regions, maybe even a disparity in men/women creating another 2 risk groups, we end up with 2^4 * 20 flows just by replicating the original flows 2^4 times, but also require more flows to connect these dimensions together, since infected women can transmit to succeptible men for example.

Those flows are not exactly parameters, each flow is parametrized with multiple parameters, for example we might have a transmissibility, a resistance, multiple disease evolution parameters, etc. per dimension. We haven't really discussed the mathematical aspects of the model so I am out of my depth. W what is clear is that we form tensors of parameters which can be referenced through equations, such that a tensor of shape `(2, 2, 2)` representing transmissibility might be referenced in group 1, region 2, vaccination group 1 using `risk[0][1][0]` for example. Now we form many of those tensors and generally the depth of each dimension will be more than 2.

## How will we tune the parameters?

It is possible, but complicated, to apply backpropagation through the model equations to tune the parameters, but it is not the envisionned approach.

What we think is a viable solution  is a form of random search through parameters. Simply iterating through different values of each parameter randomly and picking the best combination. What we mean by "best" is that we can run a simulation and it fits the expected curve better than other simulations.

This can most likely be done in a straight forward way in the software since we already plan on supporting simulation and then we can add some rules like comparing squared error with other curves.

## Tuning Model architecture

Another aspect of the training is that we might want to also try 2 different models, train them both, see which performs best. The goal would be to automate this process or assist the developper with generating models at least. This way we could simultaneously train 100 models of different configurations such as more or less disease steps, risk groups or regions.

This process could be fully automated if we can specify through the data what knowledge we have access to. For example, we can't just make up a risk group if in our data we have no idea of risk group (though this is a bad example because age could be encoded into a risk group), but what about regions? if we have data for Montreal island cases, it doesn't mean that we can split that data into east/west or in any way. What is possible though, is if we have data for multiple regions, we could offer the possibility of abstracting away (by grouping) some regions.

Ideally by specifying what the scope of possible dimension stratifications we have access to, the model could cycle through them, we could then apply another random search through those configurations.

## Tuning Specific Stratifications or Dimensions

Another aspect of the training is that, as mentionned previously, we form tensors of parameters of size of the dimensions explored. As explained previously, if we have 4 dimensions orthogonal to our SEIR model, that makes a product of those 4 dimensions sizes multiplied by the number of flows of our SEIR model. That makes alot of weights to train, and more room for error or overfitting.

What could be done is trying to train only the disease "dimensions" which represents the transitions from Exposed to Infected to Recovered. We can train it using some observed data gathered using, say 1000 or more people in the observed population that have been infected, exactly how long they stay in each stage for. Once that training is done, we could lock the values of those flows, simplifying the rest of the training process for every model that reuses the same SEIR model.

Another possibility is if we have some data that applies only to a specific region for example, its the opposite of what we just saw. In the previous example we focus on the disease, but now we want to use the whole model but only tuning to a part of the data. Say the whole data is for Quebec (Province) but we have data for Quebec (City) and Montreal, but don't want to train it all at once, we could train in parrallel a model composed only of Montreal and one only of Quebec, then lock the parameters and train the transmissions between Montreal and Quebec by training the whole model.

Those two possibilities are not entirely different, actually the first one is more specified than the second. The second just requires us to remove some dimensions during training of parts of the model, but the first also requires us to remove part of the dimension we are targetting. Essentially we have to set some parameters to 0 like new cases when we want to train the disease dimension, because the disease is part of the SEIR dimension and can't be made orthogonal. Regions and Disease steps ARE orthogonal though, so anything that have to do with training based on disease steps will be more complicated, it is unclear exactly what will be the right way to define dimensions such that we can toggle some aspects of them, maybe in the end it will be the same process used to specify "locked" parameter values and manually setting them to 0 to look only at disease evolution and not transmission in some experiments.
