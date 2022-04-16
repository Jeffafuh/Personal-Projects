from manim import *
from ngon import *

#KNOWN BUGS:
#A starting theta not PI/2 or 0 and a shift of the polygon will result in an infinite loop during "actually create the sublines"
#-Cause unknown
#TODO: why doesn't shift work for ngon of n=7
#Temp solution: Generate shape, then apply shift/rotate :)

class ConstructionDemo(Scene):
    def construct(self):
        def show_next_instruction(instructions, small_instructions, curr_instruction):
            self.wait(0.5)
            self.play(Transform(instructions[curr_instruction], small_instructions[curr_instruction]),
                    Create(instructions[curr_instruction+1]))
            self.wait(0.5)

        def create_subsegments(bits: VGroup, lines: VGroup, subsegments: VGroup, runtime=1):
            length = len(lines)
            
            for i in range(length):
                self.play(bits[i+1].animate.set_color(GREEN), lines[i].animate.set_color(GREEN), run_time=runtime)
                self.wait(runtime/2)
                self.play(Create(subsegments[i]), FadeOut(lines[i]), bits[i+1].animate.set_color(WHITE), run_time=runtime)

        def create_subsegments_2(p1: ngon, p2: ngon, edge_num, runtime=1):
            length = len(p1.lines[edge_num])
            
            for i in range(length):
                self.play(p1.bits[edge_num][i+1].animate.set_color(GREEN), p1.lines[edge_num][i].animate.set_color(GREEN),
                          p2.bits[edge_num][i+1].animate.set_color(GREEN), p2.lines[edge_num][i].animate.set_color(GREEN),
                          run_time=runtime)
                self.wait(runtime/2)
                self.play(Create(p1.subsegments[edge_num][i]), FadeOut(p1.lines[edge_num][i]), p1.bits[edge_num][i+1].animate.set_color(WHITE),
                          Create(p2.subsegments[edge_num][i]), FadeOut(p2.lines[edge_num][i]), p2.bits[edge_num][i+1].animate.set_color(WHITE),
                          run_time=runtime)

        def create_edge(instructions: VGroup, p: ngon, edgeNum: int):
            for i in range(len(instructions)):
                self.play(instructions[i].animate.set_color(YELLOW), run_time=0.2)

                if i == 0:
                    self.play(p.edges[edgeNum].animate.set_color(YELLOW), run_time=0.5)
                    self.play(p.edges[edgeNum].animate.set_color(WHITE), run_time=0.5)
                elif i == 1:
                    self.play(Create(p.bisectors[edgeNum].set_color(RED).reverse_direction()), run_time=0.4)
                elif i == 2:
                    self.play(p.edges[edgeNum].animate.set_color(YELLOW), run_time=0.2)
                    self.play(Create(p.lines[edgeNum].set_color(BLUE)), run_time=0.5)
                    self.play(p.edges[edgeNum].animate.set_color(WHITE), FadeOut(p.bisectors[edgeNum]), run_time=0.2)
                elif i == 3:
                    self.play(Create(p.bits[edgeNum]), run_time=0.5)
                elif i == 4:
                    create_subsegments(p.bits[edgeNum], p.lines[edgeNum], p.subsegments[edgeNum], runtime=0.2)
                elif i == 5:
                    pass

                self.play(instructions[i].animate.set_color(WHITE))

        def create_edge_2(p1: ngon, p2:ngon, edgeNum: int):
            for i in range(5):
                if i == 0:
                    self.play(p1.edges[edgeNum].animate.set_color(YELLOW),
                              p2.edges[edgeNum].animate.set_color(YELLOW), 
                              run_time=0.5)
                    self.play(p1.edges[edgeNum].animate.set_color(WHITE),
                              p2.edges[edgeNum].animate.set_color(WHITE), 
                              run_time=0.5)
                elif i == 1:
                    self.play(Create(p1.bisectors[edgeNum].set_color(RED)),
                              Create(p2.bisectors[edgeNum].set_color(RED)),
                              run_time=0.2)
                elif i == 2:
                    self.play(p1.edges[edgeNum].animate.set_color(YELLOW),
                              p2.edges[edgeNum].animate.set_color(YELLOW),
                              run_time=0.2)
                    self.play(Create(p1.lines[edgeNum].set_color(BLUE)),
                              Create(p2.lines[edgeNum].set_color(BLUE)),
                              run_time=0.5)
                    self.play(p1.edges[edgeNum].animate.set_color(WHITE), FadeOut(p1.bisectors[edgeNum]),
                              p2.edges[edgeNum].animate.set_color(WHITE), FadeOut(p2.bisectors[edgeNum]),
                              run_time=0.3)
                elif i == 3:
                    self.play(Create(p1.bits[edgeNum]),
                              Create(p2.bits[edgeNum]),
                              run_time=0.5)
                elif i == 4:
                    create_subsegments_2(p1, p2, edgeNum, runtime=0.2)
        
        instructions = VGroup()
        small_instructions = VGroup()

        instructions.add(Tex(r"Step 1:\\Create the\\regular polygon and\\pick an edge"))
        small_instructions.add(Tex(r"Step 1: Pick an edge").scale(0.5))
        instructions.add(Tex(r"Step 2: Draw the\\perpendicular bisector").shift(4*RIGHT))
        small_instructions.add(Tex(r"Step 2: Draw the perpendicular bisector").scale(0.5).next_to(small_instructions[0], DOWN))
        instructions.add(Tex(r"Step 3: Evenly \\divide the bisector\\(Parallel to the\\current edge)").shift(4*RIGHT))
        small_instructions.add(Tex(r"Step 3: Evenly divide the bisector").scale(0.5).next_to(small_instructions[1], DOWN))
        instructions.add(Tex(r"Step 4:\\Assign a 'random'\\bit-sequence").shift(4*RIGHT))
        small_instructions.add(Tex(r"Step 4: Assign a 'random' bit-sequence").scale(0.5).next_to(small_instructions[2], DOWN))
        instructions.add(Tex(r"Step 5: Stitch each division.\\If the associated bit is:\\0: Start stitching immediately.\\1: Start at an offset, then stitch.").scale(0.75).shift(4*RIGHT))
        small_instructions.add(Tex(r"Step 5: Stitch each division").scale(0.5).next_to(small_instructions[3], DOWN))
        instructions.add(Tex(r"Repeat for all* other edges!").shift(4*RIGHT))
        small_instructions.add(Tex(r"Repeat for all* other edges!").scale(0.5).next_to(small_instructions[4], DOWN))
        small_instructions.to_edge(UR, buff=0.5)
        

        title = Tex(r"Hitomezashi Stitching\\Construction").scale_to_fit_width(10)
        self.play(Create(title))
        self.wait(.5)
        self.play(FadeOut(title))

        p = ngon(num_verticies=4, shift=LEFT*3, segments_per_line= 10, radius=3.5)
        cur_instruction = 0

        self.play(Create(instructions[0]))
        self.wait(0.5)
        self.play(Create(p.edges), Create(p.verticies),
                  instructions[0].animate.shift(4*RIGHT), run_time=2)
        self.play(p.edges[2].animate.set_color(YELLOW))
        self.play(p.edges[2].animate.set_color(WHITE))

        show_next_instruction(instructions, small_instructions, cur_instruction)
        cur_instruction += 1

        self.play(Create(p.bisectors[0].set_color(RED)))

        show_next_instruction(instructions, small_instructions, cur_instruction)
        cur_instruction += 1

        self.play(p.edges[2].animate.set_color(YELLOW))
        self.play(Create(p.lines[0].set_color(BLUE)), run_time=3)
        self.play(p.edges[2].animate.set_color(WHITE), FadeOut(p.bisectors[0]))

        show_next_instruction(instructions, small_instructions, cur_instruction)
        cur_instruction += 1

        self.play(Create(p.bits[2]), run_time=2)

        show_next_instruction(instructions, small_instructions, cur_instruction)
        cur_instruction += 1

        create_subsegments(p.bits[2], p.lines[0], p.subsegments[0], runtime=0.7)

        show_next_instruction(instructions, small_instructions, cur_instruction)
        self.wait(0.5)

        disclaimer = Tex(r"*For regular $n$-gons with an even $n$,\\only half of the edges need stitching").scale(0.4).next_to(instructions[5], DOWN)
        self.add(disclaimer)
        self.wait(1)
        self.remove(disclaimer)

        self.play(Transform(instructions[5], small_instructions[5]))
        self.play(instructions.animate.scale(1.25).move_to(4*RIGHT))
        self.wait(0.5)

        create_edge(instructions, p, 1)

        self.wait(1)

        self.play(FadeOut(p.bits[1:3], p.edges, p.verticies, p.subsegments, instructions))
        self.wait(0.5)

        bonus_text = Tex(r"Bonus").move_to(UP).scale_to_fit_height(.75)
        rest_text = Tex(r"Hexagon + Triangle construction").scale_to_fit_height(.75).next_to(bonus_text, DOWN)
        bestagon_text = Tex(r"Bestagon").scale_to_fit_height(.75).move_to(rest_text, aligned_edge=LEFT)
        self.play(Create(bonus_text), run_time = .4)
        self.play(Create(bestagon_text), run_time=0.5)
        self.play(Uncreate(bestagon_text), run_time=0.3)
        self.play(Create(rest_text))
        self.play(FadeOut(bonus_text, rest_text))

        self.wait(0.5)

        p1 = ngon(num_verticies=3, shift=LEFT*3.5, segments_per_line= 11, radius=3)
        p2 = ngon(num_verticies=6, shift=RIGHT*3.5, segments_per_line= 11, radius=3)

        self.play(Create(p1.edges), Create(p1.verticies),
                  Create(p2.edges), Create(p2.verticies))

        for i in range(3):
            create_edge_2(p1, p2, i)

        self.wait(1)

        self.play(FadeOut(p1.bits[0:3], p1.edges, p1.verticies, p1.subsegments,
                          p2.bits[0:3], p2.edges, p2.verticies, p2.subsegments))