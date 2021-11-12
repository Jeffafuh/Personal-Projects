/**
 * @(#)uiElements.java
 *
 *
 * @author
 * @version 1.00 2020/3/29
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

public class uiElements extends Object{
	private slider magicSlider;
	private slider dtSlider;
	private slider gravSlider;
	private int x;
	private int y;
	private int width;
	private int height;
	private int deltaX;
	private int deltaY;
	private String select;

    public uiElements() {
    	out.println("whoops");
    }

    public uiElements(int x1,int y1,int w,int h)
    {
    	x=x1;
    	y=y1;
    	width=w;
    	height=h;
    	magicSlider=new slider(100,500,800);
    	dtSlider=new slider(100,500,700);
    	//gravSlider=new slider(100,)
    	select="";
    }

    public void draw(Graphics2D window)
    {
    	window.setColor(Color.GRAY);
    	window.fillRect(x,y,width,height);
    	window.setColor(Color.BLACK);
    	window.drawString("Change size of dt (Currently "+getDT()+")",100,650);
    	magicSlider.draw(window);
    	window.drawString("Change air resistance (Currently "+getMagic()+")",100,750);
    	dtSlider.draw(window);
    }

    public boolean checkWithinBounds(int x1,int y1)
    {
		if(x1>x&&x1<x+width)
			if(y1>y&&y1<y+width)
			{
				if(magicSlider.checkWithinBounds(x1,y1))
					select="magicslide";
				else if(dtSlider.checkWithinBounds(x1,y1))
					select="dtslide";
				return true;
			}
		return false;
    }

    public void scroll(int x,int y)
    {
		if(deltaX!=0||deltaY!=0)
    	{
			deltaX=deltaX-x;
			deltaY=deltaY-y;
			if(select.equals("magicslide"))
				magicSlider.scroll(deltaX,deltaY);
			else if(select.equals("dtslide"))
				dtSlider.scroll(deltaX,deltaY);
    	}
    	deltaX=x;
    	deltaY=y;
    }

    public void clearCache()
    {
		deltaX=0;
    	deltaY=0;
    	select="";
    }

    public double getMagic()
    {
    	return (magicSlider.getConstant()-magicSlider.getStart())/(magicSlider.getStop()-magicSlider.getStart());
    }

    public double getDT()
    {
    	return (dtSlider.getConstant()-dtSlider.getStart())/(dtSlider.getStop()-dtSlider.getStart())*0.015;
    }


}