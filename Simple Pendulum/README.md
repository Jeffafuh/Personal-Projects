An interactive model for the phase space of a single pendulum inspired by 3b1b.

Once compiled, "physicsRunner.java" contains the main method to be executed to run the program. Once run, the program will display a grid used to model the pendulum's movement.
The x-axis represents the angle of the pendulum relative to the vertical and the y-axis represents how fast, or the velocity, that the angle is changing at.
The current position of the pendulum is indicated on the plane as a yellow point and is also displayed at the top left of the screen.

The red lines from each point on the plane represent the vector field of the system of equations of the pendulum; each line has been normalized to a unit vector but still retains the direction of motion for that point.
While the starting conditions cannot be changed once the program is running, the plane is scalable (mousewheel) and moveable (dragging w/ mouse).

At the bottom left of the screen, there are two sliders that control two parameters of the pendulum.

The top "dt" slider changes the size of the next "step" for the pendulum to be calculated at each point in time. 
While greater values of dt cause the pendulum to move faster, this also causes the calculations to become increasingly inaccurate (eventually resulting in the pendulum gaining energy).

The bottom "air resistance" slider changes the coefficient of the damping force experienced by the system.

Created in JCreator.
