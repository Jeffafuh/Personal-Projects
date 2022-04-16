/**
 * @(#)swingThing.java
 *
 *
 * @author
 * @version 1.00 2020/3/27
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.*;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.lang.*;
import static java.lang.System.*;

public class swingThing extends Object{

	private int x;
	private int y;
	private int width;
	private int height;
	private int length;
	private int radius;
	private double theta;
	private double thetaDot;
	private int deltaX;
	private int deltaY;
	private int xMid;
	private int yMid;
	private double xCMid;
	private double yCMid;

    public swingThing() {
    	out.println("whoops");
    }

    public swingThing(int x1,int y1,int w,int h)
    {
    	theta=Math.PI/2;
    	thetaDot=0;
    	x=x1;
    	y=y1;
    	width=w;
    	height=h;
    	length=100;
    	radius=20;
    	xMid=x+width/2;
    	yMid=y+height/2;
    	xCMid=xMid;
    	yCMid=yMid+length;
    }

    public void draw(Graphics2D window)
    {
    	window.setColor(Color.GRAY);
    	window.fillRect(x,y,width,height);
    	window.setColor(Color.PINK);
    	xCMid=xMid+Math.sin(theta)*length;
    	yCMid=yMid+Math.cos(theta)*length;
    	window.draw(new Line2D.Double(xMid,yMid,xCMid,yCMid));
    	window.fill(new Ellipse2D.Double(xCMid-radius,yCMid-radius,radius*2,radius*2));
    }

    public boolean checkWithinBounds(int x1,int y1)
    {
    	if(x1>x&&x1<x+width)
    		if(y1>y&&y1<y+height)
    			return true;
    	return false;
    }

    public void moveBall(int x,int y)
    {
		if(deltaX!=0||deltaY!=0)
    	{
			deltaX=deltaX-x;
			deltaY=deltaY-y;
			yCMid-=deltaY;
			xCMid-=deltaX;
			double distance=Math.sqrt(Math.pow(xCMid-xMid,2)+Math.pow(yCMid-yMid,2));
			if(distance!=length)
			{
			}
    	}
    	deltaX=x;
    	deltaY=y;
    }

    public void clearCache()
    {
    	deltaX=0;
    	deltaY=0;
    }

    public void updateThetas(double O,double Odot)
    {
    	theta=O;
    	thetaDot=Odot;
    }


}