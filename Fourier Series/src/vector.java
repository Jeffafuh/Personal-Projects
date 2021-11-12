/**
 * @(#)vector.java
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

public class vector extends Object{

	private complexNumber startPositionConstant;
	private double periodConstant;
	private Color color;
	private String nNumber;
	private String display;
	private String whatToModify;
	private String modifyingString_Real;
	private String modifyingString_Complex;
	private int x;
	private int y;
	private int CBounds;
	private int KBounds;


    public vector() {
    	startPositionConstant=new complexNumber(1,0);
    	periodConstant=1;
  		setColor();
  		nNumber="0";
  		display="";
  		CBounds=0;
  		whatToModify="";
  		modifyingString_Real="";
  		modifyingString_Complex="";
    }

    public vector(complexNumber num, double p,String n)
    {
    	startPositionConstant=num;
    	periodConstant=p;
    	setColor();
    	nNumber=n;
    	display="";
    	KBounds=0;
    	whatToModify="";
    	modifyingString_Real="";
    	modifyingString_Complex="";
    }

    public void drawEquation(Graphics window)
    {
    	int regSize=40;
    	int subScriptSize=20;
    	int superScriptSize=18;
    	int totalXDistance=x;
    	window.setColor(color);
    	Font font=new Font("TimesRoman", Font.ITALIC,regSize);

		window.setFont(font);
    	window.drawString("C",totalXDistance,y);
    	totalXDistance+=regSize/2;

		font=font.deriveFont((float)subScriptSize);
    	totalXDistance+=subScriptSize/4;
    	window.setFont(font);
    	window.drawString(nNumber,totalXDistance,y+5);
    	totalXDistance+=subScriptSize/2*nNumber.length();
    	CBounds=totalXDistance;

		font=font.deriveFont((float)regSize);
    	window.setFont(font);
    	window.drawString("e",totalXDistance,y);
    	totalXDistance+=subScriptSize/2;

		totalXDistance+=superScriptSize/3;
		KBounds=totalXDistance;
    	font=font.deriveFont((float)superScriptSize);
    	window.setFont(font);
    	window.drawString("k*2ipi*t",totalXDistance,y-20);
    	totalXDistance+=superScriptSize/2;

    	if(display.length()!=0)
    	{
    		font=font.deriveFont((float)regSize*2);
    		if(display.equals("startpositionconstant"))
    		{
				window.drawString(modifyingString_Real+"+"+modifyingString_Complex+"i",x,y+regSize);
    		}
    		else if(display.equals("period"))
    		{
				window.drawString(modifyingString_Real,x,y+regSize);
    		}
    	}
    }

    public void setEquationCoords(int x,int y)
    {
    	this.x=x;
    	this.y=y;
    }

    public complexNumber getCartesianCoords(double time)
    {
    	complexNumber coords=new complexNumber();
    	complexNumber originalCosVal=new complexNumber(Math.cos(periodConstant*time*2*Math.PI),0);
    	coords=startPositionConstant.multiply(originalCosVal);
    	complexNumber originalSinVal=new complexNumber(0,Math.sin(periodConstant*time*2*Math.PI));
		coords=coords.add(startPositionConstant.multiply(originalSinVal));
    	return coords;
    }

    public double getRadius()
    {
    	double real = startPositionConstant.getRealComponent();
    	double complex = startPositionConstant.getComplexComponent();
    	return Math.sqrt(Math.pow(real,2)+Math.pow(complex,2));
    }

    public void setColor()
    {
		float r=(float)Math.random();
		float g=(float)Math.random();
		float b=(float)Math.random();
		color=new Color(r,g,b);
    }

    public Color getColor()
    {
    	return color;
    }

	public String checkEquationClicked(int x,int y)
	{
		if(x>this.x&&x<CBounds)
			if(y<this.y&&y>this.y-30)
			{
				if(!display.equals("startpositionconstant"))
				{
					modifyingString_Real=startPositionConstant.getRealComponent()+"";
    				modifyingString_Complex=startPositionConstant.getComplexComponent()+"";
					display="startpositionconstant";
				}
				else
				{
					display="";
					modifyingString_Real=modifyingString_Complex="";
				}
			}
		if(x>KBounds&&x<KBounds+18)
			if(y<this.y-20&&y>this.y-38)
			{
				if(!display.equals("period"))
				{
					display="period";
					modifyingString_Real=periodConstant+"";
				}
				else
				{
					display="";
					modifyingString_Real=modifyingString_Complex="";
				}
			}
		return display;
	}

	public String checkEquationDisplayClicked(int x,int y)
	{
		whatToModify="";
		if(display.length()==0)
			return whatToModify;
		if(x>this.x&&x<this.x+(modifyingString_Real.length()-1)*13)
			if(y<this.y+40&&y>this.y+25)
				whatToModify="real";
		if(x>this.x+(modifyingString_Real.length()-1)*15&&x<this.x+(modifyingString_Real.length()+modifyingString_Complex.length()-1)*15)
			if(y<this.y+40&&y>this.y+25)
				whatToModify="complex";
		return whatToModify;
	}

	public void append(char c)
	{
		if(whatToModify.length()==0)
			return;
		if(whatToModify.equals("real"))
			modifyingString_Real+=c;
		else if(whatToModify.equals("complex"))
			modifyingString_Complex+=c;
	}

	public void backspace()
	{
		if(whatToModify.equals("real"))
			modifyingString_Real=modifyingString_Real.substring(0,modifyingString_Real.length()-1);
		else if(whatToModify.equals("complex"))
			modifyingString_Complex=modifyingString_Complex.substring(0,modifyingString_Complex.length()-1);
	}

	public void terminateInput()
	{
		if(display.equals("startpositionconstant"))
		{
			double real=Double.parseDouble(modifyingString_Real);
			double complex=Double.parseDouble(modifyingString_Complex);
			startPositionConstant=new complexNumber(real,complex);
		}
		else if(display.equals("period"))
		{
			double real=Double.parseDouble(modifyingString_Real);
			periodConstant=real;
		}
		whatToModify="";
	}
}