/**
 * @(#)cell.java
 *
 *
 * @author
 * @version 1.00 2019/12/11
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.*;
import java.awt.*;
import static java.lang.System.*;

public class cell extends Object implements Comparable{

	private final Color LightGray=new Color(204,204,204);
	private final Color Gray=new Color(187,187,187);
	private final Color DarkGray=new Color(153,153,153);

	private final Color LightRed=new Color(204,54,54);
	private final Color Red=new Color(187,137,137);
	private final Color DarkRed=new Color(153,3,3);

	private final Color LightGreen=new Color(54,204,54);
	private final Color Green=new Color(187,187,187);
	private final Color DarkGreen=new Color(3,153,3);

	private final Color LightBlue=new Color(54,54,204);
	private final Color DarkBlue=new Color(3,3,153);

	private boolean color;
	private float cAdj;
	private boolean wall;
	private boolean selected;
	private boolean path;
	private int r;
	private int c;
	private int size;
	private int x;
	private int y;
	private int doubleCount;
	private String word;

	public cell()
	{
		r=0;
		c=0;
		word="";
	}

    public cell(int row, int col, int s, int x1, int y1)
    {
    	cAdj=0;
    	path=false;
    	color=false;
    	r=row;
    	c=col;
    	size=s;
    	x=x1;
    	y=y1;
    	word="";
    }

    public cell(int x1,int y1,int s,String w)
    {
    	size=s;
    	r=0;
    	c=0;
    	x=x1;
    	y=y1;
    	word=w;
    }

    public void shiftLoc(int i,int j)
    {
    	x=i;
    	y=j;
    }

    public void draw(Graphics window)
    {
		if(color)
		{
			Color LightNavy=new Color((float).01+cAdj,(float)0.4+cAdj,(float)0.4+cAdj);
			Color DarkNavy=new Color((float)0.001+cAdj,(float)0.3+cAdj,(float)0.3+cAdj);
			window.setColor(LightNavy);
    		window.fillRect(gtx(),gty(),size,size);
    		int i=3;
    			window.setColor(Color.WHITE);
    			window.fillRect(gtx(),gty(),i,size);
    			window.fillRect(gtx(),gty(),size,i);

    			window.setColor(DarkNavy);
    			window.fillRect(gtx()+size-i,gty(),i,size);
    			window.fillRect(gtx(),gty()+size-i,size,i);
		}
		else{
    		window.setColor(LightGray);
    		window.fillRect(gtx(),gty(),size,size);
    		int i=3;
    			window.setColor(Color.WHITE);
    			window.fillRect(gtx(),gty(),i,size);
    			window.fillRect(gtx(),gty(),size,i);

    			window.setColor(DarkGray);
    			window.fillRect(gtx()+size-i,gty(),i,size);
    			window.fillRect(gtx(),gty()+size-i,size,i);
		}
    		if(wall)
    		{
    			window.setColor(LightRed);
    			window.fillRect(gtx(),gty(),size,size);
    			int i=3;
    			window.setColor(Color.WHITE);
    			window.fillRect(gtx(),gty(),i,size);
    			window.fillRect(gtx(),gty(),size,i);

    			window.setColor(DarkRed);
    			window.fillRect(gtx()+size-i,gty(),i,size);
    			window.fillRect(gtx(),gty()+size-i,size,i);
    		}
    		if(selected)
    		{
    			window.setColor(LightGreen);
    			window.fillRect(gtx(),gty(),size,size);
    			int i=3;
    			window.setColor(Color.WHITE);
    			window.fillRect(gtx(),gty(),i,size);
    			window.fillRect(gtx(),gty(),size,i);

    			window.setColor(DarkGreen);
    			window.fillRect(gtx()+size-i,gty(),i,size);
    			window.fillRect(gtx(),gty()+size-i,size,i);
    		}
    		if(path)
    		{
    			window.setColor(LightBlue);
    			window.fillRect(gtx(),gty(),size,size);
    			int i=3;
    			window.setColor(Color.WHITE);
    			window.fillRect(gtx(),gty(),i,size);
    			window.fillRect(gtx(),gty(),size,i);

    			window.setColor(DarkBlue);
    			window.fillRect(gtx()+size-i,gty(),i,size);
    			window.fillRect(gtx(),gty()+size-i,size,i);
    		}
    	window.setFont(new Font("TimesRoman",Font.ITALIC+Font.BOLD,10));
    	window.setColor(new Color(50,50,185));
    	window.drawString(word,x+3,y+(size/2));
    }

    public void switchWall()
    {
    	wall=!wall;
    }

    public void switchSelect()
    {
    	selected=!selected;
    }

    public void switchPath()
    {
    	path=!path;
    }

    public void switchColor()
    {
    	color=!color;
    }

    public int gtx()//gtx()
    {
    	return c*size+x;
    }
    public int gty()//gty()
    {
    	return r*size+y;
    }

    public int compareTo(Object o)
    {
    	return 1;
    }

    public int getRow()
    {
    	return r;
    }

    public int getCol()
    {
    	return c;
    }

    public boolean getWall()
    {
    	return wall;
    }

    public boolean getColor()
    {
    	return color;
    }

    public boolean getSelected()
    {
    	return selected;
    }

    public boolean getPath()
    {
    	return path;
    }

    public void setColor(boolean a)
    {
    	color=a;
    }

    public void setColorAdj(float a)
    {
    	cAdj=a;
    }

    public void reset()
    {
    	cAdj=0;
    	color=false;
    	wall=false;
    	selected=false;
    	path=false;
    }

    public String toString()
    {
    	return "row-"+r+" col-"+c;
    }



}