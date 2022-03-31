# Base Metamodel

This base metamodel lets us easily define orthogonal dimensions (compartment graphs) and there is already developped Java code to generate the full graph using a cartesian box product for creating all the different physical compartment and flows.

![image](https://user-images.githubusercontent.com/43907476/160959830-58de8527-f7d1-476a-90af-b2b15a249806.png)

This metamodel is close to being enough for what we need, it could be, but it wouldn't be very user friendly. Let's modify it a little.

# First layer model

This model is slightly more complex, and all we will have to do is define a Model-to-Model Transformation (could be with ATL MMT, or in hand-written Java) to map it to the base model. This is layering because we can imagine making more complex models that map onto this model.

First take a look at the diagram, then we will justify the modifications.

![image](https://user-images.githubusercontent.com/43907476/160959923-d222f610-fcec-4714-967c-54409c66142b.png)

The first thing to notice is that Dimension & MetaCompartment now are interchangeable as they are both IDimensions. In most situations, it wouldn't make sense to replace a dimension or metacompartment of the base model, the goal is not exactly to make them interchangeable, but rather to apply the composite design pattern. This should most likely only be done at a depth of 1, but placing a dimension inside a dimension has its advantages. This will address model uploading.

Uploading refers to taking an existing graph/dimension and using it as a compartment, for example, we could want to make an SEIR model and then upload an existing detailed I graph, for example {{I1,I2,I3},{I1->I2,I2->I3}}. The previous metamodel would imply having to copy the graph over to our model, but with this change, we can reference it. This means that a change to our referenced model would be visible in the model that references it. We can also define that graph inline, here is an example of an inline definition:

![image](https://user-images.githubusercontent.com/43907476/160961252-c8e2011f-9355-4aa9-91cf-3e727ab998ca.png)

Now, if that dimension "I" was defined elsewhere (for example someone else's project) we could refer to it directly by creating a MetaRate and setting it's from/to attribute to refer to it (referencing other models through attributes is supported by default in EMF), or we could create a DimensionRef object and make that object point to the refered dimension. This has the advantage that its very easy to point to a new dimension by only changing 1 attribute instead of 2, but also lets us import whole dimensions easily, imagine a Vaccination dimension can now be declared as a DimensionRef and point towards some health research group's published proposed vaccination model that maps correctly to vaccination data in your region.

Finally, another good reason to allow grouping of MetaCompartments is the ease of definition of flow. Though we don't save any time declaring compartments, we do save quite a bit of time declaring flows. For example in the SEIR model seen previously with I1/2/3, notice how there is a single contact, before we would have needed 1 contact per Infectious MetaCompartment.

The only downside to this change is the complexity of the model is no longer "capped", since its defined recursively, someone could place a dimension that refers to multiple dimensions that refer to multiple dimensions... So we need to apply the cartesian box product recursively, but as I said, the most common use case without a doubt would be a single level abstraction. For now, the developped code might support just a single level and be adapted later to be recursive, unless there are no complications in which case it will be recursive by default.
