/**
 * @(#)player.java
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
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;

public class player extends Object{

	private double xPos;
	private double yPos;
	private double pSize;
	private double pAngle;
	private double pdx;
	private double pdy;

    public player() {
    	xPos=300;
    	yPos=300;
    	pSize=8;
    	pAngle=0;
    	pdx=3*Math.cos(pAngle);
		pdy=3*Math.sin(pAngle);
    }

    public void draw(Graphics2D window)
    {
		window.setColor(new Color(255,0,255));
		window.fill(new Rectangle2D.Double(xPos - (pSize/2),yPos - (pSize/2),pSize,pSize));
		window.draw(new Line2D.Double(xPos,yPos,xPos+5*pdx,yPos+5*pdy));
    }

    public void move(char s)
    {
		if(s=='d')
		{
			pAngle+=0.05;
			if(pAngle>2*Math.PI)
				pAngle-=2*Math.PI;
			pdx=3*Math.cos(pAngle);
			pdy=3*Math.sin(pAngle);
		}
		if(s=='a')
		{
			pAngle-=0.05;
			if(pAngle<0)
				pAngle+=2*Math.PI;
			pdx=3*Math.cos(pAngle);
			pdy=3*Math.sin(pAngle);
		}
		if(s=='w')
		{
			xPos+=pdx;
			yPos+=pdy;
		}
		if(s=='s')
		{
			xPos-=pdx;
			yPos-=pdy;
		}
    }

    public double getAngle()
    {
    	return pAngle;
    }

    public double getY()
    {
    	return yPos;
    }

    public double getX()
    {
    	return xPos;
    }


}