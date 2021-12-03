
/**
 * @(#)coordinatePlane.java
 *
 *
 * @author
 * @version 1.00 2020/3/22
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

public class coordinatePlane extends Object{

	private int width;
	private int height;
	private double iHat;//x, unit vectors are 1pi in real length
	private double jHat;//y
	private int xMid;
	private int yMid;
	private int deltaX,deltaY;
	private double magicConstant=0.0;
	private final double gravity=9.8;
	private double deltaT=0.009;//0.003
	private int length;
	private ArrayList<Double> xLines,yLines;
	private double xT;
	private double vT;
	private final double xScaleFactor=0.5;//in terms of pi
	private final double yScaleFactor=1;
	private ArrayList<double[]> lineCoords;

    public coordinatePlane() {
    	out.println("whoops");
    }
    public coordinatePlane(int w,int h)
    {
    	width=w;
    	height=h;
    	iHat=Math.PI*100;
    	jHat=100;
    	xMid=width/2;
    	yMid=height/2;
    	deltaX=deltaY=0;
    	xLines=new ArrayList<Double>();
    	yLines=new ArrayList<Double>();
    	length=1;
    	xT=Math.PI/2;
    	vT=0;
    	lineCoords=new ArrayList<double[]>();
    }

    public void draw(Graphics2D window)
    {
    	window.setColor(Color.GREEN);//y-axis
    	window.drawLine(xMid,0,xMid,height);
    	window.setColor(new Color(147,147,147));
    	drawVerticalLines(window);
    	window.setColor(Color.BLUE);//x-axis
    	window.drawLine(0,yMid,width,yMid);
    	window.setColor(new Color(147,147,147));
    	drawHorizontalLines(window);
    	window.setColor(Color.RED);
    	/*double[] original={0,0};
    	double[] newVector={0.185*Math.PI,1};
    	drawVectorArrow(window,original,newVector);*/
    	drawVectors(window);
    	window.setColor(Color.YELLOW);
    	drawOldCoords(window);
    }

    private void drawVerticalLines(Graphics window)
    {
    	double maxLines=Math.ceil(width/(iHat*xScaleFactor))+2;
		if(xMid<=0)
		{
			int line=xMid;
			double piCount=0;
			while(line<=width)
			{
				piCount+=xScaleFactor;
				line+=iHat*xScaleFactor;
				if(line>=0)
				{
					if(!yLines.contains(piCount))
					{
						Collections.sort(yLines);
						if(yLines.size()>=maxLines)
						{
							if(piCount>yLines.get(0))
							{
								yLines.remove(0);
								yLines.add(piCount);
							}
							else
							{
								yLines.remove(yLines.size()-1);
								yLines.add(0,piCount);
							}
						}
						else yLines.add(piCount);
					}
					window.drawLine(line,0,line,height);
    				window.drawString(piCount+"pi",line,yMid);
				}
			}
		}
		else if(xMid>=width)
		{
			int line=xMid;
			double piCount=0;
			while(line>=0)
			{
				piCount-=xScaleFactor;
				line-=iHat*xScaleFactor;
				if(line<=width)
				{
					if(!yLines.contains(piCount))
					{
						Collections.sort(yLines);
						if(yLines.size()>=maxLines)
						{
							if(piCount>yLines.get(0))
							{
								yLines.remove(0);
								yLines.add(piCount);
							}
							else
							{
								yLines.remove(yLines.size()-1);
								yLines.add(0,piCount);
							}
						}
						else yLines.add(piCount);
					}
					window.drawLine(line,0,line,height);
    				window.drawString(piCount+"pi",line,yMid);
				}
			}
		}
		else
		{
			for(int x=-1;x<=1;x+=2)
   		 	{
    			int line=xMid;
    			double piCount=0;
    			while(line>=0&&line<=width)
    			{
    				if(!yLines.contains(piCount*x))
					{
						Collections.sort(yLines);
						if(yLines.size()>=maxLines)
						{
							if(piCount*x>yLines.get(0))
							{
								yLines.remove(0);
								yLines.add(piCount*x);
							}
							else
							{
								yLines.remove(yLines.size()-1);
								yLines.add(0,piCount*x);
							}
						}
						else yLines.add(piCount*x);
					}
    				piCount+=xScaleFactor;
    				line+=iHat*x*xScaleFactor;
    				window.drawLine(line,0,line,height);
    				window.drawString(piCount*x+"pi",line,yMid);
    			}
    		}
		}
    }

    private void drawHorizontalLines(Graphics window)
    {
    	double maxLines=Math.ceil(height/(jHat*yScaleFactor))+2;
    	if(yMid<=0)
		{
			int line=yMid;
    		double count=0;
    		while(line<=height)
    		{
    			if(!xLines.contains(count))
				{
					Collections.sort(xLines);
					if(xLines.size()>=maxLines)
					{
						if(count>xLines.get(0))
						{
							xLines.remove(0);
							xLines.add(count);
						}
						else
						{
							xLines.remove(xLines.size()-1);
							xLines.add(0,count);
						}
					}
					else xLines.add(count);
				}
    			count-=yScaleFactor;
    			line+=jHat*yScaleFactor;
    			window.drawLine(0,line,width,line);
    			window.drawString(count+"",xMid,line);
    		}
		}
		else if(yMid>=height)
		{
			int line=yMid;
    		double count=0;
    		while(line>=0)
    		{
    			if(!xLines.contains(count))
				{
					Collections.sort(xLines);
					if(xLines.size()>=maxLines)
					{
						if(count>xLines.get(0))
						{
							xLines.remove(0);
							xLines.add(count);
						}
						else
						{
							xLines.remove(xLines.size()-1);
							xLines.add(0,count);
						}
					}
					else xLines.add(count);
				}
    			count+=yScaleFactor;
    			line-=jHat*yScaleFactor;
    			window.drawLine(0,line,width,line);
    			window.drawString(count+"",xMid,line);
    		}
		}
		else
		{
			for(int x=-1;x<=1;x+=2)
   		 	{
    			int line=yMid;
    			double count=0;
    			while(line>=0&&line<=height)
    			{
    				if(!xLines.contains(count*x))
					{
						Collections.sort(xLines);
						if(xLines.size()>=maxLines)
						{
							if(count*x>xLines.get(0))
							{
								xLines.remove(0);
								xLines.add(count*x);
							}
							else
							{
								xLines.remove(xLines.size()-1);
								xLines.add(0,count*x);
							}
						}
						else xLines.add(count*x);
					}
    				count-=yScaleFactor;
    				line+=jHat*x*yScaleFactor;
    				window.drawLine(0,line,width,line);
    				window.drawString(count*x+"",xMid,line);
    			}
    		}
		}
    }

    private void drawVectors(Graphics2D window)
    {
    	for(double y:xLines/*int y=0;y<1;y++*/)
    	{
    		for(double x:yLines/*int x=0;x<9;x++*/)
    		{
    			double i=x*Math.PI;
    			double j=y;
				double[] original=new double[] {i,j};
				double[] transform=transformVector(i,j);
				if(!(j==0&&x%1==0))
				{
					drawVectorArrow(window,original,transform);
				}
    		}
    	}
    }
    private void drawVectorArrow(Graphics2D window,double[] old, double[] newVector)
    {
    	int triangleSize=20;
		double[] shrink=shrinkVector(old,newVector);
		double[] original=getRealCoord(old[0],old[1],xMid,yMid);
		double[] store=getRealCoord(shrink[0],shrink[1],original[0],original[1]);
		Path2D myPath=new Path2D.Double();
		double slope=getSlope(original,store);
		/*if(slope==0)
		{
			//this is physically impossible
		}
		else if(slope==Double.MAX_VALUE||Math.abs(newVector[0]-old[0])<0.185*Math.PI||Math.abs(newVector[1]-old[1])>6)
		{
			if(store[1]>original[1])
			{
				myPath.moveTo(store[0]-triangleSize,store[1]-triangleSize);
				myPath.lineTo(store[0]+triangleSize,store[1]-triangleSize);
				myPath.lineTo(store[0],store[1]);
				myPath.closePath();
				window.fill(myPath);
			}
			else
			{
				myPath.moveTo(store[0]-triangleSize,store[1]+triangleSize);
				myPath.lineTo(store[0]+triangleSize,store[1]+triangleSize);
				myPath.lineTo(store[0],store[1]);
				myPath.closePath();
				window.fill(myPath);
			}
		}
		else
		{
			if(checkLR(original,store)==1)
			{
				slope*=-1;
				slope=Math.pow(slope,-1);
				double[] midpoint={(store[0]+original[0])/2,(store[1]+original[1])/2};
				myPath.moveTo(store[0],store[1]);
				double[] test1={midpoint[0]-slope*10,midpoint[1]-10};
				double[] test={midpoint[0]+slope*10,midpoint[1]+10};
				int ud=checkUD(test,test1);
    			int lr=checkLR(test,test1);
				double[][] triangle=getUnitTriangle(slope,test,test1,ud,lr);
				myPath.lineTo(triangle[0][0],triangle[0][1]);
				myPath.lineTo(triangle[1][0],triangle[1][1]);
			}
			else
			{
				slope*=-1;
				slope=Math.pow(slope,-1);
				double[] midpoint={(store[0]+original[0])/2,(store[1]+original[1])/2};
				myPath.moveTo(store[0],store[1]);
				double[] test={midpoint[0]-slope*10,midpoint[1]-10};
				double[] test1={midpoint[0]+slope*10,midpoint[1]+10};
				int ud=checkUD(test,test1);
    			int lr=checkLR(test,test1);
				double[][] triangle=getUnitTriangle(slope,test,test1,ud,lr);
				myPath.lineTo(triangle[0][0],triangle[0][1]);
				myPath.lineTo(triangle[1][0],triangle[1][1]);
			}
			myPath.closePath();
			window.fill(myPath);
		}*/
		window.draw(new Line2D.Double(original[0],original[1],store[0],store[1]));
    }
    private double[][] getUnitTriangle(double slope,double[] original,double[] newVector,int ud,int lr)
    {
    	double[][] store=new double[2][2];
    	double distance=Math.sqrt(Math.pow(original[0]-newVector[0],2)+Math.pow(original[1]-newVector[1],2));
    	if(distance<20)
    	{
    		store[0]=original;
    		store[1]=newVector;
    		return store;
    	}
    	else
    	{
			if(ud==1&&lr==1)//up right
			{
				original[0]+=0.01; original[1]+=0.01*slope; newVector[0]-=0.01; newVector[1]-=0.01*slope;
			}
			else if(ud==1&&lr==-1)//up left
			{
				original[0]-=0.01; original[1]-=0.01*slope; newVector[0]+=0.01; newVector[1]+=0.01*slope;
			}
			if(ud==-1&&lr==1)//down right
			{
				original[0]+=0.01; original[1]+=0.01*slope; newVector[0]-=0.01; newVector[1]-=0.01*slope;
			}
			else if(ud==-1&&lr==-1)//down left
			{
				original[0]-=0.01; original[1]-=0.01*slope; newVector[0]+=0.01; newVector[1]+=0.01*slope;
			}
    		return getUnitTriangle(slope,original,newVector,ud,lr);
    	}

    }
    private double[] shrinkVector(double[] original, double[] newVector)
    {
    	double[] store=new double[2];
    	double[] test=getRealCoord(original[0],original[1],xMid,yMid);
    	double[] test1=getRealCoord(newVector[0],newVector[1],test[0],test[1]);
    	double slope=getSlope(test,test1);
    	if(slope==Double.MAX_VALUE)
    	{
    		if(newVector[1]>original[1])
    		{
    			store[0]=newVector[0];
    			store[1]=1;
    		}
    		else
    		{
    			store[0]=newVector[0];
    			store[1]=-1;
    		}
    	}
		else
		{
    		double theta=Math.atan((newVector[1]-original[1])/(newVector[0]-original[0]));
    		if(checkLR(original,newVector)==1)
    		{
    			store[0]=Math.cos(theta);
    			store[1]=Math.sin(theta);
    		}
    		else
    		{
    			store[0]=-Math.cos(theta);
    			store[1]=-Math.sin(theta);
    		}
    	}
    	return store;//store for shrunk vectors
    }
    private int checkLR(double[] original, double[] newVector)//from original to new, 1 if right -1 if left
    {
    	if(newVector[0]>original[0])
    		return 1;
    	else if(newVector[0]<original[0])
    		return -1;
    	return 0;
    }
    private int checkUD(double[] original,double[] newVector)//o->n, 1 if up, -1 if down
    {
    	if(newVector[1]<original[1])
    		return 1;
    	else if(newVector[1]>original[1])
    		return -1;
    	return 0;
    }
    private double getSlope(double[] original,double[] newVector)
    {
    	if(newVector[0]==original[0])
    		return Double.MAX_VALUE;
    	return (newVector[1]-original[1])/(newVector[0]-original[0]);
    }

    public double[] calcNewDeltas(Graphics2D window,double xTheta,double vTheta,double magic,double dt)
    {
    	magicConstant=magic;
    	deltaT=dt;
    	lineCoords.add(new double[] {xTheta,vTheta});
    	double[] real=getRealCoord(xTheta,vTheta,xMid,yMid);
    	double[] transform=scaleVector(transformVector(xTheta,vTheta),deltaT);
    	double[] newVector=getRealCoord(transform[0],transform[1],real[0],real[1]);
    	//window.draw(new Line2D.Double(real[0],real[1],newVector[0],newVector[1]));
    	xTheta+=transform[0];
    	vTheta+=transform[1];
    	return new double[] {xTheta,vTheta};
    }

    private void drawOldCoords(Graphics2D window)
    {
    	double r=2.5;
    	for(double[] d:lineCoords)
    	{
    		double[] real=getRealCoord(d[0],d[1],xMid,yMid);
    		window.fill(new Ellipse2D.Double(real[0]-r,real[1]-r,2*r,2*r));
    	}
    }

    private double[] getRealCoord(double theta,double thetaDot,double x1,double y1)
    {
    	double[] store=new double[2];
    	store[0]=x1+theta/Math.PI*iHat;
    	store[1]=y1-thetaDot*jHat;
    	return store;
    }

    private double[] transformVector(double theta,double thetaDot)
    {
    	double[] store=new double[2];
    	store[0]=thetaDot;
    	store[1]=-magicConstant*thetaDot-(gravity/length)*Math.sin(theta);
    	return store;
    }

    private double[] scaleVector(double[] d, double scaleFactor)
    {
    	double[] store=new double[2];
    	store[0]=d[0]*scaleFactor;
    	store[1]=d[1]*scaleFactor;
    	return store;
    }

    public void scroll(int x,int y)
    {
    	if(deltaX!=0||deltaY!=0)
    	{
			deltaX=deltaX-x;
			deltaY=deltaY-y;
			yMid-=deltaY;
			xMid-=deltaX;
    	}
    	deltaX=x;
    	deltaY=y;
    }

    public void clearCache()
    {
    	deltaX=deltaY=0;
    }

	public void dilate(int d)
    {
    	if(d<0)
    	{
    		iHat=iHat*1.02;
    		jHat=jHat*1.02;
    	}
    	else if(d>0)
    	{
    		iHat=iHat*0.98;
    		jHat=jHat*0.98;
    	}
    }
}