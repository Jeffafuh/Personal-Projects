/**
 * @(#)cell.java
 *
 *
 * @author
 * @version 1.00 2020/1/27
 */
import java.awt.*;
import java.util.*;
import static java.lang.System.*;

public class cell extends Object implements Comparable{

	private int x;
	private int y;
	private int size;
	private boolean isButton;
	private boolean isSelected;
	private boolean isToggled;
	private boolean isPath;
	private boolean isMarked;
	private boolean specMark;
	private Color color;
	private String word;

	//make my life easier by storing some path-finding values in here instead of messing with a complicated parallel list :)
	private int distance;

    public cell() {
    	x=0;
    	y=0;
    	size=10;
    	randomColor();
    	initBooleans();
    	word="";
    }

   	public cell(int col,int row, int s)
   	{
   		x=col;
   		y=row;
   		size=s;
   		randomColor();
   		initBooleans();
   		word="";
   	}

   	public void initBooleans()
   	{
   		isButton=false;
   		isSelected=false;
   		isPath=false;
   		isToggled=false;
   		isMarked=false;
   		specMark=false;
   	}

   	public void initPathFinding()
   	{
   		distance=Integer.MAX_VALUE;
   	}

   	public cell(int col,int row,String s)
   	{
   		x=col;
   		y=row;
   		size=50;
   		initBooleans();
   		isButton=true;
   		word=s;
   	}

   	public void randomColor()
   	{
		float r=(float)Math.random();
		float g=(float)Math.random();
		float b=(float)Math.random();
		color=new Color(r,g,b);
   	}

    public int compareTo(Object o)
    {
    	Color temp=((cell)o).getColor();
    	if(color.getRed()<temp.getRed())
    		return -1;
    	else if(color.getRed()>temp.getRed())
    		return 1;
    	if(color.getGreen()<temp.getGreen())
    		return -1;
    	else if(color.getGreen()>temp.getGreen())
    		return 1;
    	if(color.getBlue()<temp.getBlue())
    		return -1;
    	else if(color.getBlue()>temp.getBlue())
    		return 1;
    	return 0;
    }

    public void draw(Graphics window)
    {
    	if(isButton)
    	{
    		if(isToggled)
    		{
    			window.setColor(new Color(50,204,204));
 		   		window.fillRect(x,y,size,size);
    			int i=3;
    				window.setColor(new Color(50,255,255));
    				window.fillRect(x,y,i,size);
    				window.fillRect(x,y,size,i);

    				window.setColor(new Color(50,153,153));
    				window.fillRect(x+size-i,y,i,size);
    				window.fillRect(x,y+size-i,size,i);
    		}
    		else
    		{
    			window.setColor(new Color(204,204,204));
 		   		window.fillRect(x,y,size,size);
    			int i=3;
    				window.setColor(Color.WHITE);
    				window.fillRect(x,y,i,size);
    				window.fillRect(x,y,size,i);

    				window.setColor(new Color(153,153,153));
    				window.fillRect(x+size-i,y,i,size);
    				window.fillRect(x,y+size-i,size,i);
    		}
    	}
    	else//if just circle boi
    	{
    		window.setColor(color);
    		window.fillOval(x,y,size,size);
    		if(isSelected)
    		{
    			window.setColor(checkColor());
    			window.fillOval(x-3,y-3,size+6,size+6);

    			window.setColor(color);
    			window.fillOval(x,y,size,size);

    			window.setColor(checkColor());
    			window.fillRect(x+(size/2)-1,y,3,size);//vertical line
    			window.fillRect(x,y+(size/2)-1,size,3);//horizontal line
    		}
    	}
    	int sizeFactor=12;
    	if(word.length()==6)
    		sizeFactor=11;
    	if(word.length()>6)
    		sizeFactor=9;
    	window.setFont(new Font("TimesRoman",Font.ITALIC+Font.BOLD,sizeFactor));
    	window.setColor(new Color(50,50,185));
    	window.drawString(word,x+3,y+(size/2));
    }

    public void shiftLoc(int i,int j)
    {
    	x-=i;
    	y-=j;
    }

    public int getX()
    {
    	return x;
    }
    public int getY()
    {
    	return y;
    }
    public Color getColor()
    {
    	return color;
    }

    public Color checkColor()
    {
    	//relects color hierarchy
    	if(specMark)
    		return Color.YELLOW;
    	else if(isMarked)
    		return Color.GREEN;
    	else if(isPath)
    		return Color.RED;
    	return Color.BLUE;
    }

    public void setColor(int r,int g,int b)
    {
    	color=new Color(r,g,b);
    }

    public int getMidX()
    {
    	return getX()+(getSize()/2);
    }
    public int getMidY()
    {
    	return getY()+(getSize()/2);
    }

    public void setSize(int s)
    {
    	size=s;
    }
    public int getSize()
    {
    	return size;
    }

    public void setSelected(boolean b)
    {
    	isSelected=b;
    }
    public void switchSelected()
    {
    	isSelected=!isSelected;
    }

    public void switchToggle()
    {
    	isToggled=!isToggled;
    }

    public void switchPath()
    {
    	isPath=!isPath;
    }

    public void switchMarked()
    {
    	isMarked=!isMarked;
    }
    public void setMarked(boolean b)
    {
    	isMarked=b;
    }

    public void switchSpecMark()
    {
    	specMark=!specMark;
    }

    public String toString()
    {
    	return "|x="+x+" y="+y+" size="+size+" "+color+word+"|";
    }






}