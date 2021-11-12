/**
 * @(#)cell.java
 *
 *
 * @author
 * @version 1.00 2019/11/13
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.*;
import static java.lang.System.*;

public class cell extends Object{
	public final Color LightGray=new Color(204,204,204);
	public final Color Gray=new Color(187,187,187);
	public final Color DarkGray=new Color(153,153,153);
	private int r;
	private int c;
	private int size;
	private int x;
	private int y;
	private int num;
	private boolean hidden;
	private boolean flag;
	private boolean mine;
	private int doubleCount;

    public cell()
    {
    }

    public cell(int row,int col,int s,int x1,int y1)
    {
    	x=x1;
    	y=y1;
    	r=row;
    	c=col;
    	size=s;
    	hidden=true;
    	flag=false;
    	mine=false;
    	doubleCount=0;
    	num=-1;
    }

    public cell(int x1,int y1,int s)
    {
    	x=x1;
    	y=y1;
    	r=0;
    	c=0;
    	size=s;
    }

    public void draw(Graphics window)
    {
    	if(hidden)
    	{
    		window.setColor(LightGray);
    		window.fillRect(getTrueX(),getTrueY(),size,size);
    		int i=3;
    			window.setColor(Color.WHITE);
    			window.fillRect(getTrueX(),getTrueY(),i,size);
    			window.fillRect(getTrueX(),getTrueY(),size,i);

    			window.setColor(DarkGray);
    			window.fillRect(getTrueX()+size-i,getTrueY(),i,size);
    			window.fillRect(getTrueX(),getTrueY()+size-i,size,i);
    		if(flag)
    		{
    			window.setColor(Color.RED);
    			window.fillRect(getTrueX(),getTrueY(),size,size);
    		}
    	}
    	else
    	{
    		window.setColor(Gray);
    		window.fillRect(getTrueX(),getTrueY(),size,size);
    		int i=1;
    			window.setColor(DarkGray);
    			window.fillRect(getTrueX(),getTrueY(),i,size);
    			window.fillRect(getTrueX(),getTrueY(),size,i);
    			window.fillRect(getTrueX()+size-i,getTrueY(),i,size);
    			window.fillRect(getTrueX(),getTrueY()+size-i,size,i);
    		if(mine)
    		{
    			window.setColor(Color.BLACK);
    			window.fillRect(getTrueX(),getTrueY(),size,size);
    		}
    		else if(num!=0)
    		{
    			window.setColor(Color.BLACK);
    			window.drawString(num+"",getTrueX()+10,getTrueY()+10);
    		}
    	}
    }

    public void reveal()
    {
    	hidden=false;
    }
    public void hide()
    {
    	hidden=true;
    }

    public void Switch()
    {
    	doubleCount++;
    	if(doubleCount==2)
    	{
    		doubleCount=0;
    		return;
    	}
    	if(hidden)
    		reveal();
    	else hide();
    }

    public int getTrueX()
    {
    	return c*size+x;
    }
    public int getTrueY()
    {
    	return r*size+y;
    }
    public boolean getHidden()
    {
    	return hidden;
    }
    public int getSize()
    {
    	return size;
    }
    public boolean getFlag()
    {
    	return flag;
    }
    public boolean getMine()
    {
    	return mine;
    }
    public void setMine()
    {
    	mine=true;
    }
    public int getC()
    {
    	return c;
    }
    public int getR()
    {
    	return r;
    }
    public void setNum(int i)
    {
    	num=i;
    }
    public int getNum()
    {
    	return num;
    }
    public void switchFlag()
    {
    	doubleCount++;
    	if(doubleCount==2)
    	{
    		doubleCount=0;
    		return;
    	}
    	if(flag)
    		flag=false;
    	else flag=true;
    }
    public String toString()
    {
    	return c+" "+r;
    }
}
