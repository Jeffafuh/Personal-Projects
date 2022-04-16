import numpy as np
from manim import *
from random import randint

def pol2cart(rho, phi):
    x = rho * np.cos(phi)
    y = rho * np.sin(phi)
    return [x, y, 0]

def find_intersection(p1, p2, p3, p4):
    intersection = [0, 0, 0]
    
    denom = (p1[0]-p2[0])*(p3[1]-p4[1])-(p3[0]-p4[0])*(p1[1]-p2[1])
    if denom == 0:
        return intersection

    intersection[0] = ((p1[0]*p2[1]-p1[1]*p2[0])*(p3[0]-p4[0])-(p3[0]*p4[1]-p3[1]*p4[0])*(p1[0]-p2[0]))/denom
    intersection[1] = ((p1[0]*p2[1]-p1[1]*p2[0])*(p3[1]-p4[1])-(p3[0]*p4[1]-p3[1]*p4[0])*(p1[1]-p2[1]))/denom

    return intersection

class ngon():
    def __init__(self, num_verticies = 3, radius = 3, segments_per_line = 15, theta = PI/2, probability_of_one = 0.5, shift = 0):

        increment = 2*PI/num_verticies

        #calculate needed constants
        apothem = radius * np.cos(PI/num_verticies) #the length of the apothem
        segment_proportion = 1/(segments_per_line+1) #the proportion of how spaced the subsegments need to be relative to the perpendicular bisector
        num_lines = num_verticies if num_verticies%2 == 1 else int(num_verticies/2) #how many edges need a unique bit assignment
        side_length = 2*radius*np.sin(PI/num_verticies) #length of one edge/side
        traversed_edges = int((num_verticies-1)/2) #how many edges the between the start and end of the perpendicular bisector
        subsegment_length = side_length*traversed_edges/(segments_per_line+1) #the length of one single subsegment

        #draw the verticies by using the circumscribed radius and rotating it 2 pi radians
        #also draw the "ghost" verticies to be used for aligning the bit assignment strings
        self.verticies = VGroup()
        ghost_verticies = VGroup()

        for i in range(num_verticies):
            self.verticies.add(
                Dot(point=pol2cart(radius, theta))
                )
            ghost_verticies.add(
                Dot(point=pol2cart(radius+0.5, theta))
            )
            theta += increment

        #create and add edges + ghost edges
        self.edges = VGroup()
        ghost_edges = VGroup()

        for i in range(num_verticies):
            self.edges.add(
                Line(self.verticies[i].get_center(), self.verticies[(i+1)%num_verticies].get_center(), buff=0)
            )
            ghost_edges.add(
                Line(ghost_verticies[i].get_center(), ghost_verticies[(i+1)%num_verticies].get_center(), buff=0)
            )

        #create and assign the bit assignments depending on given probability
            bit_assignments = np.random.choice(
                a=[1, 0],
                size=(num_lines, segments_per_line),
                replace=True,
                p=[probability_of_one, 1 - probability_of_one]
            )

        #if there is an even number of edges, then only half of them need an unique bit assignment so duplicate the assignments vertically (edges opposite from each other)
        #otherwise, if there is an odd number of edges, each edge needs it's own unique bit assignment (edges opposite from a vertex)      
        if num_verticies % 2 == 0:
            bit_assignments = np.vstack((bit_assignments, bit_assignments))

        theta -= increment
        self.bits = VGroup()
        for list in bit_assignments:
                self.bits.add(Text(str(list)))#use MathTex for fancy text, Text for fast rendering
                theta += increment
        self.bits.scale_to_fit_width(side_length)

        for i in range(num_verticies):
            self.bits[i].rotate(ghost_edges[i].get_angle()).move_to(ghost_edges[i])

        #init all groups to store parts of the polygon
        self.lines = VGroup()
        self.mid_pts = VGroup()
        self.subsegments = VGroup()
        self.bisectors = VGroup()
        self.mid_pts = VGroup()
        self.full_subsegments = VGroup()

        for i in range(num_lines): #loop through the edges (num_lines)

            lines = VGroup()
            self.lines.add(lines)
            left_edge_index = i-1
            right_edge_index = (i+1)%num_verticies

            #create and place the bisector
            pts = perpendicular_bisector(self.edges[i].get_start_and_end())
            bisector = Line(pts[0], pts[1]).set_length(apothem + radius if num_verticies%2 == 1 else 2*apothem)
            bisector.move_to(self.edges[i].get_midpoint(), aligned_edge=bisector.get_end())
            self.bisectors.add(bisector)
            
            #create the points for the sublines
            mid_pts = VGroup()
            self.mid_pts.add(mid_pts)
            for j in range(segments_per_line): #loop through and create each line segment (segments_per_line)
                mid_pts.add(
                    self.edges[i].copy().move_to( Dot(bisector.point_from_proportion(segment_proportion*(j+1))) )
                    )
            self.mid_pts.add(mid_pts)

            #actually create the sublines
            for j in range(segments_per_line):
                while True:
                    intersection = find_intersection(
                        mid_pts[j].get_start(),
                        mid_pts[j].get_end(),
                        self.edges[right_edge_index].get_start(),
                        self.edges[right_edge_index].get_end()
                    )

                    try:
                        proportion = self.edges[right_edge_index].proportion_from_point(intersection)
                        lines.add(Line(
                            self.edges[left_edge_index].point_from_proportion(1-proportion), intersection
                        ))
                        break
                    except ValueError as e:
                        left_edge_index = (left_edge_index-1)%num_verticies
                        right_edge_index = (right_edge_index+1)%num_verticies

            #create the dotted pattern on the lines
            subsegments = VGroup()
            self.subsegments.add(subsegments)

            for j in range(segments_per_line):
                line_subsegments = VGroup()
                subsegments.add(line_subsegments)

                subsegment_proportion = subsegment_length/lines[j].get_length()
                cur_subsegment_proportion = 0
                
                if bit_assignments[i][j] == 1:
                    cur_subsegment_proportion += subsegment_proportion

                while cur_subsegment_proportion < 1:
                    line_subsegments.add(
                        Line(lines[j].point_from_proportion(cur_subsegment_proportion),
                             lines[j].point_from_proportion(min(cur_subsegment_proportion + subsegment_proportion, 1)))
                    )
                    cur_subsegment_proportion += 2*subsegment_proportion
        
        self.apply_shift(shift)

    def get_all_visible_elements(self):

        everything = VGroup()
        everything.add(self.verticies, self.edges, self.subsegments, self.bits)

        return everything

    def get_all_elements(self):

        everything = VGroup()
        everything.add(self.verticies, self.edges, self.subsegments, self.bits, self.bisectors, self.lines)

        return everything

    def apply_shift(self, shift):
        self.get_all_elements().shift(shift)