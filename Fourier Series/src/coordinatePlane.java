/**
 * @(#)coordinatePlane.java
 *
 *
 * @author
 * @version 1.00 2020/4/12
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
	private double iHat;//unit vectors
	private double jHat;
	private int xMid;
	private int yMid;
	private int deltaX;
	private int deltaY;
	private ArrayList<vector> vectorList;
	private ArrayList<complexNumber> path;
	private ArrayList<Color> pathColorValues;
	private int equationListOffSet;
	private vector storedVector;
	private boolean displayRealGraph;
	private boolean displayComplexGraph;
	private boolean displayAxis;
	private ArrayList<complexNumber> function;
	private slider slide;
	private int vectorNumber;

    public coordinatePlane() {
    	out.println("whoops");
    }

    public coordinatePlane(int w,int h)
    {
    	width=w;
    	height=h;
    	iHat=50;
    	jHat=-50;
    	xMid=width/2;
    	yMid=height/2;
    	path=new ArrayList<complexNumber>();
    	pathColorValues=new ArrayList<Color>();
    	vectorList=new ArrayList<vector>();
    	equationListOffSet=0;
    	storedVector=null;
    	displayRealGraph=false;
    	displayComplexGraph=false;
    	function=null;
    	displayAxis=false;
		deltaY=deltaX=0;
		slide=new slider(10,310,900);
		vectorNumber=100;
    }

    public coordinatePlane(int w,int h,ArrayList<complexNumber> f)
    {
    	this(w,h);
    	makeApproximation(f);
    	function=f;
    }

    public void draw(Graphics2D window,double time)
    {
    	drawAxis(window);
    	drawVectors(window,time);
    	drawPath(window);
    	updatePathSize();
    	drawVectorEquations(window);
    	drawRealGraph(window);
    	drawComplexGraph(window);
    	drawFunction(window);
    }

    public void drawVectors(Graphics2D window,double time)
    {
    	if(vectorList.size()==0)
    		return;
    	complexNumber prevCoords=new complexNumber(0,0);
    	complexNumber coords=new complexNumber(0,0);
		for( int i=0; i<vectorList.size(); i++ )
		{
			window.setColor(vectorList.get(i).getColor());
			coords = vectorList.get(i).getCartesianCoords(time);
			coords = coords.add(prevCoords);
			window.draw(new Line2D.Double(xMid+iHat*prevCoords.getRealComponent(),yMid+jHat*prevCoords.getComplexComponent(),xMid+iHat*coords.getRealComponent(),yMid+jHat*coords.getComplexComponent()));
			double radius = iHat*vectorList.get(i).getRadius();
			window.draw(new Ellipse2D.Double(xMid+iHat*prevCoords.getRealComponent()-radius,yMid+jHat*prevCoords.getComplexComponent()-radius,radius*2,radius*2));
			prevCoords = coords;
			if(i==vectorList.size()-1)
			{
				path.add(prevCoords);
				pathColorValues.add(new Color((float)1.0,(float)1.0,(float)1.0));
			}
		}
    }

    public void drawVectorEquations(Graphics window)
    {
    	window.setColor(Color.WHITE);
    	window.fillRect(200,10,1200,120);
    	for(int i=equationListOffSet,j=0; i<vectorList.size()&&i<9+equationListOffSet; i++,j++)
    	{
    		vectorList.get(i).setEquationCoords(100+(j+1)*130,50);
    		vectorList.get(i).drawEquation(window);
    	}
    }

    public void drawPath(Graphics2D window)
    {
    	if(path.size()==0)
    		return;
    	Path2D myPath=new Path2D.Double();
    	myPath.moveTo(xMid+iHat*path.get(0).getRealComponent(),yMid+jHat*path.get(0).getComplexComponent());
    	for(int i=0; i<path.size(); i++)
    	{
    		window.setColor(pathColorValues.get(i));
    		myPath.lineTo(xMid+iHat*path.get(i).getRealComponent(),yMid+jHat*path.get(i).getComplexComponent());
    	}
    	myPath.moveTo(xMid+iHat*path.get(0).getRealComponent(),yMid+jHat*path.get(0).getComplexComponent());
    	myPath.closePath();
    	window.draw(myPath);
    }

    public void drawFunction(Graphics2D window)
    {
		if(function==null)
    		return;
    	window.setColor(Color.BLACK);
    	window.fillRect(10,665,300,200);
    	window.setColor(Color.WHITE);
    	window.drawRect(10,665,300,200);
    	slide.draw(window);

		int xMid=160;
		int yMid=765;
		int iHat=10;
		int jHat=-10;
    	Path2D myPath=new Path2D.Double();
    	myPath.moveTo(xMid+iHat*function.get(0).getRealComponent(),yMid+jHat*function.get(0).getComplexComponent());
    	for(int i=0; i<function.size(); i++)
    	{
    		window.setColor(Color.WHITE);
    		myPath.lineTo(xMid+iHat*function.get(i).getRealComponent(),yMid+jHat*function.get(i).getComplexComponent());
    	}
    	myPath.moveTo(xMid+iHat*function.get(0).getRealComponent(),yMid+jHat*function.get(0).getComplexComponent());
    	myPath.closePath();
    	window.draw(myPath);

    	window.setColor(Color.WHITE);
    	window.drawString("# of vectors being used for approximation :: +/-"+vectorNumber,322,902);
    }

    public void updatePathSize()
    {
    	int cap=1000;
    	/*if(function!=null)
    	{
    		cap=function.size();
    	}*/

    	if(path.size()>cap)
    	{
    		pathColorValues.remove(0);
    		path.remove(0);
    	}
    }

    public void addVector()
    {
    	if(vectorList.size()==0)
    		vectorList.add(new vector(new complexNumber(1,0),1,"0"));
    	else if(vectorList.size()%2==0)
    		vectorList.add(new vector(new complexNumber(1,0),1,vectorList.size()+""));
    	else vectorList.add(new vector(new complexNumber(1,0),-1,vectorList.size()+""));
    }

    public void deleteVector()
    {
    	if(vectorList.size()!=0)
    		vectorList.remove(vectorList.size()-1);
    }

    public boolean checkWithinBoundsofEquationList(int x, int y)
    {
    	if(x>200&&x<1200)
    		if(y>10&&y<120)
    			return true;
    	return false;
    }

    public void shiftEquationListOffSet(int offset)
    {
    	if(vectorList.size()>9)
    	{
    		equationListOffSet+=offset;
			if(equationListOffSet<0)
				equationListOffSet=0;
			if(equationListOffSet>vectorList.size()-9)
				equationListOffSet=vectorList.size()-9;
    	}
    }

    public void checkClick(int x,int y)
    {
		for(int i=equationListOffSet; i<vectorList.size()&&i<9+equationListOffSet; i++)
		{
			vectorList.get(i).checkEquationClicked(x,y);
			String result= vectorList.get(i).checkEquationDisplayClicked(x,y);
			if(result.length()!=0)
			{
				storedVector=vectorList.get(i);
			}
		}
    }

    public void terminateInput()
    {
		storedVector.terminateInput();
    }
    public void backspace()
    {
		if(storedVector==null)
			return;
		storedVector.backspace();
    }
    public void sendInput(char c)
    {
		if(storedVector==null)
			return;
		storedVector.append(c);
    }

    public void toggleRealGraph()
    {
		displayRealGraph=!displayRealGraph;
    }

    public void drawRealGraph(Graphics2D window)
    {
		if(!displayRealGraph)
			return;
		window.setColor(Color.WHITE);
		//window.drawRect(1200,300,200,200);
		window.setColor(Color.BLUE);
		window.drawLine(1200,300,1200,500);
		window.drawString("real output",1202,300);
		window.setColor(Color.GREEN);
    	window.drawLine(1200,400,1400,400);
    	window.drawString("time",1202,399);
		if(path.size()==0)
    		return;
    	Path2D myPath=new Path2D.Double();
    	myPath.moveTo(1200,400+-20*path.get(path.size()-1).getRealComponent());
    	for(int i=path.size()-1; i>=0&&i>=path.size()-1-199; i--)
    	{
    		window.setColor(pathColorValues.get(i));
    		myPath.lineTo(1200+Math.abs(i-path.size()-1),400+-20*path.get(i).getRealComponent());
    	}
    	myPath.moveTo(1200,400+-20*path.get(path.size()-1).getRealComponent());
    	myPath.closePath();
    	window.draw(myPath);
    }

    public void toggleComplexGraph()
    {
    	displayComplexGraph=!displayComplexGraph;
    }

    public void drawComplexGraph(Graphics2D window)
    {
    	if(!displayComplexGraph)
    		return;
    	window.setColor(Color.BLUE);
    	window.drawLine(1200,600,1200,800);
    	window.drawString("complex output",1202,600);
    	window.setColor(Color.GREEN);
    	window.drawLine(1200,700,1400,700);
    	window.drawString("time",1202,699);
    	if(path.size()==0)
    		return;
    	Path2D myPath=new Path2D.Double();
    	myPath.moveTo(1200,700+-20*path.get(path.size()-1).getComplexComponent());
    	for(int i=path.size()-1; i>=0&&i>=path.size()-1-199; i--)
    	{
    		window.setColor(pathColorValues.get(i));
    		myPath.lineTo(1200+Math.abs(i-path.size()-1),700+-20*path.get(i).getComplexComponent());
    	}
    	myPath.moveTo(1200,700+-20*path.get(path.size()-1).getComplexComponent());
    	myPath.closePath();
    	window.draw(myPath);
    }

    public void makeApproximation(ArrayList<complexNumber> f)
    {
		double dt=1.0/f.size();
    	double time=0;
    	TreeMap<Double,complexNumber> function=new TreeMap<Double,complexNumber>();
    	for(complexNumber num:f)
    	{
    		function.put(time,num);
    		time+=dt;
    	}
    	for(int x=-(vectorNumber);x<=(vectorNumber);x++)
    	{
			complexNumber startConstant=new complexNumber(0,0);
			vector calculation=new vector(new complexNumber(1,0),-x,"");
			for(double t: function.keySet())
			{
				complexNumber tempConstant=new complexNumber(0,0);
				tempConstant=tempConstant.add(calculation.getCartesianCoords(t));
				tempConstant=tempConstant.multiply(function.get(t));
				tempConstant=tempConstant.multiply(new complexNumber(dt,0));
				startConstant=startConstant.add(tempConstant);
			}
    		vectorList.add(new vector(startConstant,x,x+""));
    	}
    }

    public void dilate(int d)
    {
		if(d<0)
    	{
    		iHat=iHat*1.03;
    		jHat=jHat*1.03;
    	}
    	else if(d>0)
    	{
    		iHat=iHat*0.97;
    		jHat=jHat*0.97;
    	}
    }

    public void scroll(int x,int y)
    {
		if(deltaX!=0||deltaY!=0)
    	{
			deltaX=deltaX-x;
			deltaY=deltaY-y;
			if(function!=null&&slide.checkWithinBounds(x,y))
			{
				slide.scroll(deltaX,deltaY);
				checkTheVectorNumber();
			}
			else
			{
				yMid-=deltaY;
				xMid-=deltaX;
			}
    	}
    	deltaX=x;
    	deltaY=y;
    }

    public void clearCache()
    {
		deltaX=deltaY=0;
    }

    public void toggleAxis()
    {
    	displayAxis=!displayAxis;
    }

    public void drawAxis(Graphics window)
    {
    	if(!displayAxis)
    		return;
		window.setColor(Color.BLUE);
		window.drawLine(xMid,0,xMid,height);
		window.setColor(Color.GREEN);
		window.drawLine(0,yMid,width,yMid);
    }

	public void checkTheVectorNumber()
	{
		int newNum=(int)Math.floor((slide.getConstant()-slide.getStart())/(slide.getStop()-slide.getStart())*100)+1;
		if(newNum!=vectorNumber)
		{
			vectorList=new ArrayList<vector>();
			path=new ArrayList<complexNumber>();
			vectorNumber=newNum;
			makeApproximation(function);
		}
	}

}