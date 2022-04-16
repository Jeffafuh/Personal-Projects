/**
 * @(#)cell.java
 *
 *
 * @author
 * @version 1.00 2019/8/27
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.*;
import static java.lang.System.*;

public abstract class cell extends Object{
	 boolean isHidden;
	 int align;
	 int r;
	 int c;
	 int w;
	 int adjr;
	 int adjc;
	 Stack<entity> thing;
	 Color colour;

    public cell() {
    	isHidden=true;
    	r=0;
    	c=0;
    	w=1;
    	align=7;
    	thing=new Stack<entity>();
    }
    public cell(int row,int col,int wid,int adjRow,int adjCol)
    {
    	isHidden=true;
    	r=row;
    	c=col;
    	w=wid;
    	adjr=adjRow;
    	adjc=adjCol;
    	align=7;
    	thing=new Stack<entity>();
    	colour=Color.GRAY;
    }
    public abstract void draw(Graphics window);

    public void draw(Graphics window,int x)
    {
    	int k=(w-w/10)/2;
    	window.setColor(Color.BLACK);
    	window.fillRect(adjc+c*w,adjr+r*w,w,w);
    	window.setColor(Color.GREEN);
    	window.drawString(x+"",adjc+c*w+k,adjr+r*w+k);
    	for(entity e:thing)
    		e.draw(window,this);
    }

    public void setAlign(int x)
    {
    	align=x;
    }
    public int getAlign()
    {
    	return align;
    }

    public int getAdjustedRow()
    {
    	return adjr+r*w;
    }
    public int getWidth()
    {
    	return w;
    }
    public int getAdjustedCol()
    {
    	return adjc+c*w;
    }
    public int getRow()
    {
    	return r;
    }
    public int getCol()
    {
    	return c;
    }
    public void addThing(entity thang)
    {
    	thing.push(thang);
    }
    public void setVisible(boolean a)
    {
    	isHidden=a;
    }
    public void setColour(Color c)
    {
    	colour=c;
    }
    public entity getThing()
    {
    	if(!thing.isEmpty())
    		return thing.peek();
    	return null;
    }
    public entity removeThing()
    {
    	if(!thing.isEmpty())
    		return thing.pop();
    	return null;
    }
    public int getQSize()
    {
    	return thing.size();
    }
    public boolean isEmpty()
    {
    	if(thing.isEmpty())
    		return true;
    	return false;
    }
    public ArrayList<int[]> move(String s)
    {
    	ArrayList<int[]> vals=new ArrayList<int[]>();
    	for(int x=0;x<thing.size();x++)
    	{
			entity temp=removeThing();
			vals.add(temp.mvmt(s));
			addThing(temp);
    	}
    	return vals;
    }

    public String toString()
    {
    	return "r-"+r+"c-"+c+"w-"+w;
    }


}