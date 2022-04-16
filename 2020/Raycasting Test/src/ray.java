/**
 * @(#)ray.java
 *
 *
 * @author
 * @version 1.00 2020/5/5
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

public class ray extends Object{

	private double rayAngle;
	private double objY;
	private double objX;

	private double hRayX;
	private double hRayY;
	private double hXOffset;
	private double hYOffset;

	private double vRayX;
	private double vRayY;
	private double vXOffset;
	private double vYOffset;

	private String shortest;

    public ray() {
    }

    public ray(double a,double x,double y)
    {
		rayAngle=a;
		if(rayAngle<0)
			rayAngle+=2*Math.PI;
		if(rayAngle>2*Math.PI)
			rayAngle-=2*Math.PI;

		objX=x;
		objY=y;

		shortest="";
    }

    public void draw(Graphics2D window)
    {
    	if(shortest.equals("vertical"))
    	{
    		window.setColor(Color.RED);
    		window.draw(new Line2D.Double(objX,objY,vRayX,vRayY));
    	}
    	else if(shortest.equals("horizontal"))
    	{
    		window.setColor(new Color(255,100,0));
    		window.draw(new Line2D.Double(objX,objY,hRayX,hRayY));
    	}
    }

    public void calcHorizontalLine()
    {
		double arcTan = -1/Math.tan(rayAngle);

		if(rayAngle>Math.PI)//looking up
		{
			hRayY = (((int)objY>>6)<<6)-0.0001;
			hRayX = (objY-hRayY)*arcTan+objX;
			hYOffset = -64;
			hXOffset = -hYOffset*arcTan;
		}
		else if(rayAngle<Math.PI)//looking down
		{
			hRayY = (((int)objY>>6)<<6)+64;
			hRayX = (objY-hRayY)*arcTan+objX;
			hYOffset = 64;
			hXOffset = -hYOffset*arcTan;
		}
		else //looking left or right
		{
			hRayY=objY;
			hRayX=objX;
			hYOffset=0;
			hXOffset=0;
		}
    }

    public double[] getHorizontalLineValues()
    {
    	return new double[]{hRayX,hRayY,hXOffset,hYOffset};
    }

    public void updateHorizontalValues()
    {
    	hRayX+=hXOffset;
    	hRayY+=hYOffset;
    }

    public double calcHorizontalDistance()
    {
    	return Math.sqrt(Math.pow(objX-hRayX,2)+Math.pow(objY-hRayY,2));
    }


    public void calcVerticalLine()
    {
		double nTan = -Math.tan(rayAngle);

		if(rayAngle>Math.PI/2 && rayAngle<3*Math.PI/2)//looking left
		{
			vRayX = (((int)objX>>6)<<6)-0.0001;
			vRayY = (objX-vRayX)*nTan+objY;
			vXOffset = -64;
			vYOffset = -vXOffset*nTan;
		}
		else if(rayAngle<Math.PI/2 || rayAngle>3*Math.PI/2)//looking right
		{
			vRayX = (((int)objX>>6)<<6)+64;
			vRayY = (objX-vRayX)*nTan+objY;
			vXOffset = 64;
			vYOffset = -vXOffset*nTan;
		}
		else //looking up or down
		{
			vRayY=objY;
			vRayX=objX;
			vYOffset=0;
			vXOffset=0;
		}

    }

    public double[] getVerticalLineValues()
    {
    	return new double[]{vRayX,vRayY,vXOffset,vYOffset};
    }

    public void updateVerticalValues()
    {
    	vRayX+=vXOffset;
    	vRayY+=vYOffset;
    }

    public double calcVerticalDistance()
    {
    	return Math.sqrt(Math.pow(objX-vRayX,2)+Math.pow(objY-vRayY,2));
    }

    public void checkShortestDistance()//return true if vertical is shorter else false if horizontal is shorter
    {
    	if(calcVerticalDistance()<calcHorizontalDistance())
    		shortest="vertical";
    	else if(calcHorizontalDistance()<calcVerticalDistance())
    		shortest="horizontal";
    }

    public double getShortestDistance()
    {
    	if(shortest.equals("vertical"))
    		return calcVerticalDistance();
    	return calcHorizontalDistance();
    }

    public String getShortest()
    {
    	return shortest;
    }

    public double getShortestPosition()
    {
    	if(shortest.equals("vertical"))
    	{
    		return vRayY;
    	}
    	else return hRayX;
    }

    public double getAngle()
    {
    	return rayAngle;
    }

    public String toString()
    {
    	return calcVerticalDistance()+" "+calcHorizontalDistance();
    }


}