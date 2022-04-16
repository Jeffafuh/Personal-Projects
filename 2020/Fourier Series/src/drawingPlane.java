/**
 * @(#)drawingPlane.java
 *
 *
 * @author
 * @version 1.00 2020/4/22
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

public class drawingPlane {

	private int height;
	private int width;
	private double iHat;//unit vectors
	private double jHat;
	private int xMid;
	private int yMid;
	private ArrayList<complexNumber> path;

    public drawingPlane(int w, int h) {
    	width=w;
    	height=h;
    	iHat=50;
    	jHat=-50;
    	xMid=width/2;
    	yMid=height/2;
    	path=new ArrayList<complexNumber>();
    }

    public void draw(Graphics2D window)
    {
		drawPath(window);
    }

	private void drawPath(Graphics2D window)
	{
		if(path.size()==0)
    		return;
    	Path2D myPath=new Path2D.Double();
    	myPath.moveTo(xMid+iHat*path.get(0).getRealComponent(),yMid+jHat*path.get(0).getComplexComponent());
    	for(int i=0; i<path.size(); i++)
    	{
    		window.setColor(Color.WHITE);
    		myPath.lineTo(xMid+iHat*path.get(i).getRealComponent(),yMid+jHat*path.get(i).getComplexComponent());
    	}
    	myPath.moveTo(xMid+iHat*path.get(0).getRealComponent(),yMid+jHat*path.get(0).getComplexComponent());
    	myPath.closePath();
    	window.draw(myPath);
	}

    public void sendCoords(int x,int y)
    {
		double real=(x-xMid)/iHat;
		double complex=(y-yMid)/jHat;
		path.add(new complexNumber(real,complex));
    }

    public ArrayList<complexNumber> getFunction()
    {
    	return path;
    	/*double dt=1.0/function.size();
    	double time=0;
    	TreeMap<Double,complexNumber> newFunction=new TreeMap<Double,complexNumber>();
    	for(complexNumber num:function)
    	{
    		newFunction.put(time,num);
    		time+=dt;
    	}*/
    }

    public void reset()
    {
    	path=new ArrayList<complexNumber>();
    }


}