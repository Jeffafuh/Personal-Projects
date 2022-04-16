/**
 * @(#)grid.java
 *
 *
 * @author
 * @version 1.00 2020/5/3
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.KeyEvent;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.*;
import javax.swing.*;
import static java.lang.System.*;
import java.awt.geom.Path2D;
import java.awt.geom.Line2D;

public class grid extends Object{

	private int mapSizeX=8;
	private int mapSizeY=8;
	private int mapS=64;
	private player p;
	private final int[] map=
	{
		1,1,1,1,1,1,1,1,
		1,0,1,0,0,0,0,1,
		1,0,1,0,0,0,0,1,
		1,0,0,0,0,0,0,1,
		1,0,0,0,0,0,0,1,
		1,0,1,0,0,1,0,1,
		1,0,0,0,0,0,0,1,
		1,1,1,1,1,1,1,1,
	};

	private ArrayList<ray> rayList;

    public grid() {
    	p=new player();
    }

    public void draw(Graphics2D window)
    {
		drawMap(window);
		p.draw(window);
		drawRays(window);
		draw3DScene(window);
    }

    public void drawMap(Graphics window)
    {
    	for(int y=0; y<mapSizeY; y++)
    	{
    		for(int x=0; x<mapSizeX; x++)
    		{
    			if(map[y*mapSizeX+x]==1)
    				window.setColor(Color.WHITE);
    			if(map[y*mapSizeX+x]==0)
    				window.setColor(Color.BLACK);

    			Polygon block=new Polygon();
    			int xOffset=x*mapS;
    			int yOffset=y*mapS;
    			block.addPoint(xOffset + 1	     , yOffset    + 1       );
    			block.addPoint(xOffset + 1       , yOffset    + mapS - 1);
    			block.addPoint(xOffset + mapS - 1, yOffset    + mapS - 1);
    			block.addPoint(xOffset + mapS - 1, yOffset    + 1       );
    			window.fillPolygon(block);
    		}
    	}
    }

    public void drawRays(Graphics2D window)
    {
		double rayAngle=p.getAngle()-(Math.PI/6);
		int mapX, mapY, mapP, limitCheck;

		rayList=new ArrayList<ray>();

		for(int i=0; i<64; i++)
		{
			ray temp=new ray(rayAngle,p.getX(),p.getY());
			rayList.add(temp);
			limitCheck=0;

			temp.calcHorizontalLine();
			while(limitCheck<8)
			{
				double[] values=temp.getHorizontalLineValues();
				mapX=(int)(values[0])>>6;
				mapY=(int)(values[1])>>6;
				mapP=mapY*mapSizeX+mapX;
				if(mapP<mapSizeX*mapSizeY && mapP>=0 && map[mapP]==1)
				{
					limitCheck=8;
				}
				else
				{
					temp.updateHorizontalValues();
					limitCheck+=1;
				}
			}

			limitCheck=0;

			temp.calcVerticalLine();
			while(limitCheck<8)
			{
				double[] values=temp.getVerticalLineValues();
				mapX=(int)(values[0])>>6;
				mapY=(int)(values[1])>>6;
				mapP=mapY*mapSizeX+mapX;
				if(mapP<mapSizeX*mapSizeY && mapP>=0 && map[mapP]==1)
				{
					limitCheck=8;
				}
				else
				{
					temp.updateVerticalValues();
					limitCheck+=1;
				}
			}

			temp.checkShortestDistance();
			temp.draw(window);

			rayAngle+=(Math.PI/180);
		}
    }

    public void draw3DScene(Graphics2D window)
    {
    	window.setColor(Color.BLUE);
		window.fillRect(512,0,512,256);
		window.setColor(Color.GREEN);
		window.fillRect(512,256,512,256);
		for(int r=0; r<rayList.size(); r++)
		{
			double correctedAngle=p.getAngle()-rayList.get(r).getAngle();
			if(correctedAngle<0)
				correctedAngle+=2*Math.PI;
			if(correctedAngle>2*Math.PI)
				correctedAngle-=2*Math.PI;
			double correctedDistance=rayList.get(r).getShortestDistance()*Math.cos(correctedAngle);

			double lineHeight=(mapS*320)/correctedDistance;
			/*if(lineHeight>350)
				lineHeight=350;*/
			double lineOffset=256-lineHeight/2.0;

			double wallX=rayList.get(r).getShortestPosition()%64;

			window.setStroke(new BasicStroke(8));
			if(rayList.get(r).getShortest().equals("vertical"))
				window.setColor(new Color(255,0,0));
			else window.setColor(new Color(255,100,0));
			window.draw(new Line2D.Double(r*8+516,lineOffset,r*8+516,lineOffset+lineHeight));
		}
    }

    public void move(char s)
    {
    	p.move(s);
    }

    public void print()
    {
		for(ray r:rayList)
		{
			double wallX=r.getShortestPosition()%64;
			out.println(wallX);
		}
    }



}