import numpy as np
from manim import *

#2 current ideas:
# Idea 1: Use numpy to generate a cumulative sum walk/step matrix using cumsum(), then use add_line_to to iterate over the new matrix
# Idea 2: Just pre-generate all of the lines, then use move_to() to make the path
#         |_ Don't need to fresh generate each time, use a dict to keep track of previously created lines and use copy() instead?
#prob do idea 1 for faster run-time

class testing(Scene):
    def construct(self):
        def intro():
            text = VGroup()
            title = Tex(r"Pi Day 2022\\Calculating Pi Via Walking").scale_to_fit_width(10)
            sub_title = Tex(r"Bonus Rectangular Walks").next_to(title, DOWN)
            text.add(title, sub_title)
            text.move_to(ORIGIN)
            self.play(Create(title))
            self.wait(1)
            self.add(sub_title)
            self.wait(1)
            self.play(FadeOut(title, sub_title))
            self.wait(.5)
        
        intro()

class genWalkRect(Scene):
    def construct(self):
        num_walks = 10
        num_steps = 5

        walk_1 = np.random.choice(
                a=[0, -1, 1],
                size=(num_walks, num_steps),
                replace=True,
                p=[0.5, 0.25, 0.25]
            )
        
        walk_2 = walk_1.copy()
        walk_2[walk_1 == 0] = np.random.choice(a=[-1, 1], p=[0.5, 0.5])
        walk_2[np.logical_or(walk_1 == -1, walk_1 == 1)] = 0
        walk_brown = np.dstack((walk_1, walk_2))
        cum_walk = np.cumsum(walk_brown, axis=1) * 0.25

        final_walk_coords = np.sum(walk_brown, axis=1)
        final_walk_dist = np.sqrt(np.sum(final_walk_coords**2, axis=1))
        avg_walk_dist = np.mean(final_walk_dist)

        pi_est = 4*avg_walk_dist**2/num_steps #2D
        print(final_walk_coords)

class genWalkPolar(Scene):
    def construct(self):
        num_walks = 200
        num_steps = 200

        walk_polar = 2*PI*np.random.random_sample(size=(num_walks, num_steps))
        walk_rect = np.dstack((np.cos(walk_polar), np.sin(walk_polar)))
        cum_walk = np.cumsum(walk_rect, axis=1) * 0.25

        final_walk_coords = np.sum(walk_rect, axis=1)
        final_walk_dist = np.sqrt(np.sum(final_walk_coords**2, axis=1))
        avg_walk_dist = np.mean(final_walk_dist)

        pi_est = 4*avg_walk_dist**2/num_steps #2D
        print(avg_walk_dist)
        print(pi_est)
        print(100*(PI-pi_est)/PI)

class Explanation(Scene):
    def construct(self):
        def intro():
            title = Tex(r"Pi Day 2022\\Calculating Pi Via Walking").scale_to_fit_width(10)
            self.play(Create(title))
            self.wait(.5)
            self.play(FadeOut(title))
            self.wait(.5)

            explain = Tex(r"Let $d$ denote the distance from the starting point to\\the ending point of a random walk with $n$ steps.").scale(1).to_edge(UP)
            self.play(Create(explain))
            self.wait(1)

            formula = MathTex(r"E[d]", r"=", r"\frac{\sqrt{n\pi}}{2}")
            formula1 = MathTex(r"d_{avg}", r"=", r"\frac{\sqrt{n\pi}}{2}")
            formula2 = MathTex(r"\pi = \frac{4d_{avg}^2}{n}")
            rearrange = Tex(r"Rearrange the terms...").scale(0.75).next_to(formula2, UP)
            framebox = SurroundingRectangle(formula[0], buff=0.1)
            dist = Tex(r"Average Distance").scale(0.75).next_to(framebox, UP)
            self.play(Create(formula))
            self.wait(0.5)
            self.play(Create(framebox))
            self.play(Create(dist))
            self.wait(0.5)
            self.play(Uncreate(framebox), Transform(dist, formula1[0]), Transform(formula, formula1))
            self.remove(dist)
            self.play(Create(rearrange))
            self.wait(0.5)
            self.play(Transform(formula, formula2))
            self.wait(1)
            final = Tex(r"...Now let's simulate $d_avg$!").shift(DOWN*1.5)
            self.play(Create(final), FadeOut(rearrange))
            self.wait(1)
            self.play(FadeOut(explain, final, formula))
            self.wait(1)

        def demonstrateWalk():
            title = Tex(r"Pi Day 2022\\How Do We Walk?").scale_to_fit_width(10)
            self.play(Create(title))
            self.wait(.5)
            self.play(FadeOut(title))
            self.wait(.5)

            walk_polar = 2*PI*np.random.random_sample(size=(1, 10))
            walk_rect = np.dstack((np.cos(walk_polar), np.sin(walk_polar)))
            cum_walk = np.cumsum(walk_rect, axis=1)
            lines = VGroup().add(Line(ORIGIN, [cum_walk[0,0,0], cum_walk[0,0,1], 0]))
            for j in range(1, 10):
                lines.add(Line([cum_walk[0,j-1,0], cum_walk[0,j-1,1], 0], [cum_walk[0,j,0], cum_walk[0,j,1], 0]))
            lines.set_color(random_color())

            numberplane = NumberPlane()

            self.play(Create(numberplane))

        #intro()
        demonstrateWalk()
        
class CalcPiRect(Scene):
    def construct(self):
        def intro():
            text = VGroup()
            title = Tex(r"Pi Day 2022\\Calculating Pi Via Walking").scale_to_fit_width(10)
            sub_title = Tex(r"Bonus Rectangular Walks").next_to(title, DOWN)
            text.add(title, sub_title)
            text.move_to(ORIGIN)
            self.play(Create(title))
            self.wait(1)
            self.add(sub_title)
            self.wait(1)
            self.play(FadeOut(title, sub_title))
            self.wait(.5)
        
        intro()

        num_walks = 200
        num_steps = 200

        walk_1 = np.random.choice(
                a=[-2, 2, -1, 1],
                size=(num_walks, num_steps),
                replace=True,
                p=[0.25, 0.25, 0.25, 0.25]
            )
        
        walk_2 = walk_1.copy()
        walk_2 = np.where(np.logical_or(walk_1 == -2, walk_1 == 2), walk_1/2, 0)
        walk_1[np.logical_or(walk_1 == -2, walk_1 == 2)] = 0
        #walk_2[walk_1 == 0] = np.random.choice(a=[-1, 1], p=[0.5, 0.5])
        #walk_2[np.logical_or(walk_1 == -1, walk_1 == 1)] = 0
        walk_brown = np.dstack((walk_1, walk_2))
        cum_walk = np.cumsum(walk_brown, axis=1) * 0.25

        final_walk_coords = np.sum(walk_brown, axis=1)
        final_walk_dist = np.sqrt(np.sum(final_walk_coords**2, axis=1))
        avg_walk_dist = np.mean(final_walk_dist)

        pi_est = 4*avg_walk_dist**2/num_steps #2D
        print(avg_walk_dist)

        paths = VGroup()
        end_points = VGroup()
        origin_lines = VGroup()
        aligned_lines = VGroup()
        avg_line = Line([-avg_walk_dist/8, 0, 0], [avg_walk_dist/8, 0, 0])
        for i in range(num_walks):
            print("=====Progress: %.2f=====" %(i/num_walks*100), end="\r")
            lines = VGroup().add(Line(ORIGIN, [cum_walk[i,0,0], cum_walk[i,0,1], 0]))
            for j in range(1, num_steps):
                lines.add(Line([cum_walk[i,j-1,0], cum_walk[i,j-1,1], 0], [cum_walk[i,j,0], cum_walk[i,j,1], 0]))
            col = random_color()
            paths.add(lines.set_color(col))
            end_points.add(Dot(lines[-1].get_end()).set_color(col))
            origin_lines.add(Line(end_points[-1].get_center(), ORIGIN).set_color(col))
            if i == 0:
                aligned_lines.add(Line([-final_walk_dist[i]/8, 0, 0], [final_walk_dist[i]/8, 0, 0]).set_color(col).to_edge(UP, buff=0.05))
            else:
                aligned_lines.add(Line([-final_walk_dist[i]/8, 0, 0], [final_walk_dist[i]/8, 0, 0]).set_color(col).next_to(aligned_lines[-1], DOWN, buff=0.04))
        print()

        simulate = Tex(r"Simulating 200\\walks with 200 steps...")
        self.play(Create(simulate))
        self.wait(0.5)
        self.play(simulate.animate.scale(0.5).to_edge(UL))

        self.play(Create(paths), run_time=15)
        self.wait(1)
        self.play(Transform(simulate, Tex(r"Calculating the\\final distances...").scale(0.5).to_edge(UL)))
        self.wait(0.5)
        self.play(Create(end_points), run_time=3)
        self.wait(0.5)
        self.play(Create(origin_lines), run_time=3)
        self.wait(1)
        self.play(Transform(simulate, Tex(r"Getting the\\average distance...").scale(0.5).to_edge(UL)))
        self.wait(0.5)
        self.play(FadeOut(paths, end_points), run_time=0.5)
        self.play(Transform(origin_lines, aligned_lines), run_time=2)
        self.wait(0.5)
        self.play(Transform(origin_lines, avg_line), run_time=3)
        self.wait(0.5)
        brace = Brace(avg_line)
        brace_text = brace.get_text("Average Distance = 12.554")
        self.play(FadeIn(brace, brace_text), FadeOut(simulate))
        self.wait(0.5)
        self.play(FadeOut(origin_lines, brace), brace_text.animate.to_edge(UL))

        formula = MathTex(r"\pi = \frac{4d_{avg}^2}{n}").scale(2)
        formula1 = MathTex(r"\pi = \frac{4*12.554^2}{200}").scale(2)
        formula2 = MathTex(r"\pi = 3.1524792137675326").scale(2)
        perc_error = Tex(r"0.3465299 Percent off pi!").next_to(formula2, DOWN)

        self.play(Create(formula))
        self.wait(1)
        self.play(Transform(formula, formula1))
        self.wait(1)
        self.play(Transform(formula, formula2), FadeOut(brace_text))
        self.wait(1)
        self.play(FadeIn(perc_error))
        self.wait(2)

        self.play(FadeOut(perc_error, formula))
        self.wait(1)

class CalcPiPolar(Scene):
    def construct(self):
        title = Tex(r"Pi Day 2022\\Calculating Pi Via Walking").scale_to_fit_width(10)
        self.play(Create(title))
        self.wait(.5)
        self.play(FadeOut(title))
        self.wait(.5)

        explain = Tex(r"Let $d$ denote the distance from the starting point to\\the ending point of a random walk with $n$ steps.").scale(1).to_edge(UP)
        self.play(Create(explain))
        self.wait(1)

        formula = MathTex(r"E[d]", r"=", r"\frac{\sqrt{n\pi}}{2}")
        formula1 = MathTex(r"d_{avg}", r"=", r"\frac{\sqrt{n\pi}}{2}")
        formula2 = MathTex(r"\pi = \frac{4d_{avg}^2}{n}")
        rearrange = Tex(r"Rearrange the terms...").scale(0.75).next_to(formula2, UP)
        framebox = SurroundingRectangle(formula[0], buff=0.1)
        dist = Tex(r"Average Distance").scale(0.75).next_to(framebox, UP)
        self.play(Create(formula))
        self.wait(0.5)
        self.play(Create(framebox))
        self.play(Create(dist))
        self.wait(0.5)
        self.play(Uncreate(framebox), Transform(dist, formula1[0]), Transform(formula, formula1))
        self.remove(dist)
        self.play(Create(rearrange))
        self.wait(0.5)
        self.play(Transform(formula, formula2))
        self.wait(1)
        final = Tex(r"...Now let's simulate $d_{avg}$!").shift(DOWN*1.5)
        self.play(Create(final), FadeOut(rearrange))
        self.wait(1)
        self.play(FadeOut(explain, final, formula))
        self.wait(1)

        num_walks = 200
        num_steps = 200

        walk_polar = 2*PI*np.random.random_sample(size=(num_walks, num_steps))
        walk_rect = np.dstack((np.cos(walk_polar), np.sin(walk_polar)))
        cum_walk = np.cumsum(walk_rect, axis=1) * 0.25

        final_walk_coords = np.sum(walk_rect, axis=1)
        final_walk_dist = np.sqrt(np.sum(final_walk_coords**2, axis=1))
        avg_walk_dist = np.mean(final_walk_dist)

        pi_est = 4*avg_walk_dist**2/num_steps #2D
        print(avg_walk_dist)

        paths = VGroup()
        end_points = VGroup()
        origin_lines = VGroup()
        aligned_lines = VGroup()
        avg_line = Line([-avg_walk_dist/8, 0, 0], [avg_walk_dist/8, 0, 0])
        for i in range(num_walks):
            print("=====Progress: %.2f=====" %(i/num_walks*100), end="\r")
            lines = VGroup().add(Line(ORIGIN, [cum_walk[i,0,0], cum_walk[i,0,1], 0]))
            for j in range(1, num_steps):
                lines.add(Line([cum_walk[i,j-1,0], cum_walk[i,j-1,1], 0], [cum_walk[i,j,0], cum_walk[i,j,1], 0]))
            col = random_bright_color()
            paths.add(lines.set_color(col))
            end_points.add(Dot(lines[-1].get_end()).set_color(col))
            origin_lines.add(Line(end_points[-1].get_center(), ORIGIN).set_color(col))
            if i == 0:
                aligned_lines.add(Line([-final_walk_dist[i]/8, 0, 0], [final_walk_dist[i]/8, 0, 0]).set_color(col).to_edge(UP, buff=0.05))
            else:
                aligned_lines.add(Line([-final_walk_dist[i]/8, 0, 0], [final_walk_dist[i]/8, 0, 0]).set_color(col).next_to(aligned_lines[-1], DOWN, buff=0.04))
        print()

        simulate = Tex(r"Simulating 200\\walks with 200 steps...")
        self.play(Create(simulate))
        self.wait(0.5)
        self.play(simulate.animate.scale(0.5).to_edge(UL))

        self.play(Create(paths), run_time=15)
        self.wait(1)
        self.play(Transform(simulate, Tex(r"Calculating the\\final distances...").scale(0.5).to_edge(UL)))
        self.wait(0.5)
        self.play(Create(end_points), run_time=3)
        self.wait(0.5)
        self.play(Create(origin_lines), run_time=3)
        self.wait(1)
        self.play(Transform(simulate, Tex(r"Getting the\\average distance...").scale(0.5).to_edge(UL)))
        self.wait(0.5)
        self.play(FadeOut(paths, end_points), run_time=0.5)
        self.play(Transform(origin_lines, aligned_lines), run_time=2)
        self.wait(0.5)
        self.play(Transform(origin_lines, avg_line), run_time=3)
        self.wait(0.5)
        brace = Brace(avg_line)
        brace_text = brace.get_text("Average Distance = 12.504")
        self.play(FadeIn(brace, brace_text), FadeOut(simulate))
        self.wait(0.5)
        self.play(FadeOut(origin_lines, brace), brace_text.animate.to_edge(UL))

        formula = MathTex(r"\pi = \frac{4d_{avg}^2}{n}").scale(2)
        formula1 = MathTex(r"\pi = \frac{4*12.504^2}{200}").scale(2)
        formula2 = MathTex(r"\pi = 3.1273855560927597").scale(2)
        perc_error = Tex(r"0.4522259 Percent off pi!").next_to(formula2, DOWN)

        self.play(Create(formula))
        self.wait(1)
        self.play(Transform(formula, formula1))
        self.wait(1)
        self.play(Transform(formula, formula2), FadeOut(brace_text))
        self.wait(1)
        self.play(FadeIn(perc_error))
        self.wait(2)

        self.play(FadeOut(perc_error, formula))
        self.wait(1)
