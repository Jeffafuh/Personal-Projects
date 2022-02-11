from manim import *
from ngon import *

#KNOWN BUGS:
#A starting theta not PI/2 or 0 and a shift of the polygon will result in an infinite loop during "actually create the sublines"
#-Cause unknown
#TODO: why doesn't shift/rotate work for ngon of n=7
#Temp solution: Generate shape, then apply shift/rotate :)
class demoOneShape(Scene):
    def construct(self):
        pass

class Hitomezashi(Scene):
    def construct(self):

        def drawPolygon(p: ngon, runtime=1):
            self.play(Create(p.edges), Create(p.verticies), run_time=3)
            self.play(Create(p.subsegments), run_time=2)
            self.play(FadeIn(p.bits))

        def changePolygon(p: ngon, pNew: ngon, runtime=1):
            self.play(Transform(p.edges, pNew.edges), 
                      Transform(p.verticies, pNew.verticies),
                      Transform(p.subsegments, pNew.subsegments), 
                      Transform(p.bits, pNew.bits),
                      run_time=runtime)

        def iterateThroughProb(prob_one: ValueTracker, p: ngon, verticies):
            if prob_one.get_value() < 1:
                increment = 0.1
            else:
                increment = -0.1
            
            for _ in range(10):
                self.play(prob_one.animate.increment_value(increment))
                for _ in range(1):
                    newgon = ngon(num_verticies=verticies, probability_of_one=prob_one.get_value(), shift = 3*LEFT)
                    changePolygon(p, newgon)
            
            self.wait(1)

        title = Tex("Hitomezashi Stitching").scale_to_fit_width(10)
        self.play(Create(title))
        self.wait(.5)
        self.play(FadeOut(title))

        prob_one = ValueTracker(1)
        basegon = ngon(num_verticies=4, probability_of_one=prob_one.get_value(), shift=3*LEFT)

        drawPolygon(basegon)
        
        prob_one_text = MathTex("P(X=1)").shift(3.5*RIGHT + 1.5*UP)
        prob_one_numline = NumberLine(
            x_range=[0,1,0.1],
            length=4,
            include_ticks=True,
            include_numbers=True,
            font_size=22
        ).next_to(prob_one_text, DOWN)
        prob_one_dot = always_redraw(lambda: Dot(prob_one_numline.number_to_point(prob_one.get_value())))

        prob_zero_numline = prob_one_numline.copy()
        prob_zero_text = MathTex("P(X=0)")
        prob_zero_text.next_to(prob_one_numline, DOWN)
        prob_zero_numline.next_to(prob_zero_text, DOWN)
        prob_zero_dot = always_redraw(lambda: Dot(prob_zero_numline.number_to_point(1-prob_one.get_value())))

        self.play(FadeIn(prob_one_numline, prob_one_dot, prob_one_text,
                         prob_zero_numline, prob_zero_dot, prob_zero_text))

        iterateThroughProb(prob_one, basegon, 4)
        changePolygon(basegon, ngon(probability_of_one=prob_one.get_value(), shift=3*LEFT))
        iterateThroughProb(prob_one, basegon, 3)
        changePolygon(basegon, ngon(num_verticies=6, probability_of_one=prob_one.get_value(), shift=3*LEFT))
        iterateThroughProb(prob_one, basegon, 6)
        changePolygon(basegon, ngon(num_verticies=8, probability_of_one=prob_one.get_value(), shift=3*LEFT))
        iterateThroughProb(prob_one, basegon, 8)
        changePolygon(basegon, ngon(num_verticies=7, probability_of_one=prob_one.get_value(), shift=3*LEFT))
        iterateThroughProb(prob_one, basegon, 7)

        #vert = 7
        #for _ in range(2):
        #    changePolygon(basegon, ngon(num_verticies=vert, probability_of_one=prob_one.get_value(), shift=3*LEFT))
        #    iterateThroughProb(prob_one, basegon, vert)
        #    self.wait(0.5)
        #    vert += 1

        self.play(FadeOut(basegon.get_all_visible_elements(),
                          prob_one_dot, prob_one_text, prob_one_numline,
                          prob_zero_numline, prob_zero_dot, prob_zero_text))
        self.wait(1)