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

Another aspect of the training is that we might want to also try 2 different models, train them both, see which performs best. The goal would be to automate this process or assist the developper with generating models at least. This way we could simultaneously train 100 models of different configurations such as more or less disease steps, risk groups or regions. This process could be fully automated if we can specify through the data what knowledge we have access to. For example, we can't just make up a risk group if in our data we have no idea of risk group (though this is a bad example because age could be encoded into a risk group), but what about regions? if we have data for Montreal island cases, it doesn't mean that we can split that data into east/west or in any way. What is possible though is if we have data for multiple regions, we could offer the possibility of abstracting away (by grouping) some regions. Ideally by specifying what the scope of possible dimension stratifications we have access to, the model could cycle through them, we could then apply another random search through those configurations.
