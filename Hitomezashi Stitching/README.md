# Hitomezashi Stitching
My attempt of generalizing Hitomezashi stitching patterns to the entire family of regular n-gons.

## Running The Project
This project was created in Python 3.8 using Manim Community v0.14.0 (The Manim Community Developers. (2022). Manim – Mathematical Animation Framework (Version v0.14.0) [Computer software]. https://www.manim.community/)

For more information on how to run a Manim project to create a video, please visit [their website](https://www.manim.community/).

## Description
Once run, there are 3 main buttons and 3 main sliders to mess around with.

The bottom left corner contains 3 sliders that can be used to adjust various parameters in the experiement.

### Buttons
The top left corner contains 3 buttons used to run the simulation.

"Generate!" will instantly simulate and display Buffon's Needle problem given the parameters from the sliders. Sticks that cross a strip are marked red and sticks that do not cross are marked brown.
Above the sliders contains the approximated value of pi from the experiment.

"Animation" can be toggled on/off. If toggled on (indicated by a blue highlight), pressing "Generate!" will no longer instantly display the simulation, but will toss each stick individually and then proceed to mark the crossing sticks.

"???" will not animate or display anything, but will perform the experiment with the given parameters 1000 times and display the resulting information to the terminal.

## More Information

More details about the experiment can be found here: https://en.wikipedia.org/wiki/Buffon%27s_needle_problem
