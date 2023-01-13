using python [odeint](https://stackoverflow.com/questions/51808922/how-to-solve-a-system-of-differential-equations-using-scipy-odeint) we can solve multiple equations very easily

see this simple example code for SEIR where R is permanent

![image](https://user-images.githubusercontent.com/43907476/156083899-eea90569-76ce-42e2-b001-6506b4c69663.png)

In reality we don't want to plot every compartment and we want to load our compartments and equations from a file

![image](https://user-images.githubusercontent.com/43907476/156084266-0367099a-2225-4a82-8c37-439603b2c4ee.png)

Ideally place parameters in a dict-like structure and set them using `params['Rate']['E->I1] = 0.77, params['Contagiousness']['I1'] = 0.1, ...`
