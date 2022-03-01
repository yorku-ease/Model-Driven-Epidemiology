using python [odeint](https://stackoverflow.com/questions/51808922/how-to-solve-a-system-of-differential-equations-using-scipy-odeint) we can solve multiple equations very easily

see this simple example code for SEIR where R is permanent

![image](https://user-images.githubusercontent.com/43907476/156083899-eea90569-76ce-42e2-b001-6506b4c69663.png)

I'm not quite sure how the solver behaves, but its very easy to input the equations, so we can try modifying the current output to refer to variables placed in "state" just like above.

![image](https://user-images.githubusercontent.com/43907476/156084266-0367099a-2225-4a82-8c37-439603b2c4ee.png)

Currently each element in a `${}` refers to a constant, that is the hyperparameter space we want to random search over in the minimal viable product proposition. Having them inside templates and keeping their name/metadata is nice because when setting them in the notebook we should be able to understand quite easily what we are doing.

I am thinking of placing each parameter in a dict and setting them using `params['Rate']['E->I1] = 0.77, params['Contagiousness']['I1'] = 0.1, ...` then we could take them out of the dict, by example assigning each parameter an index and placing them in an array, modifying the equations to use the parameters from the array with the right index.
