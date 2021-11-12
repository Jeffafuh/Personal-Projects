/**
 * @(#)Grid.java
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

public class Grid extends Object{
	private final static int size=50;
	private int row;
	private int col;
	private int x;
	private int y;
	private ArrayList<cell> group;
	private flagButton button;
	private faceButton face;
	private int mines;

    public Grid() {
    }

    public Grid(int w,int h,int c,int r)
    {
    	col=w/size;
    	row=h/size;
    	x=c;
    	y=r;
    	group=new ArrayList<cell>();
    	for(int i=0; i<row;i++)
    		for(int j=0;j<col;j++)
    			group.add(new cell(i,j,size,x,y));
    	button=new flagButton(850,650,size);
    	face=new faceButton(850,400,size);
    }

    public void reset()
    {
    	group=new ArrayList<cell>();
    	for(int i=0; i<row;i++)
    		for(int j=0;j<col;j++)
    			group.add(new cell(i,j,size,x,y));
    	face=new faceButton(850,400,size);
    	setMines(mines);
    }

    public void draw(Graphics window)
    {
    	for(cell c:group)
    	{
    		c.draw(window);
    	}
    	button.draw(window);
    	face.draw(window);
    }

    public void reveal(int i, int j)
    {
    	if(!checkButton(i,j)&&!checkFace(i,j))
    	for(int k=0;k<group.size();k++)
    	{
    		cell c=group.get(k);
    		if(i>c.getTrueX()&&i<c.getTrueX()+size)
    			if(j>c.getTrueY()&&j<c.getTrueY()+size)
    			{
    				if(!button.getHidden())
    				{
    					if(!c.getFlag())
    					{
    						if(c.getNum()==0)
    						{
    							reveal(c,new ArrayList<cell>());
    						}
    						else
    						{
    							c.reveal();
    							if(c.getMine())
    							{
    								face.hide();
    								revealMines();
    							}
    						}
    					}
    				}
    				else c.switchFlag();
    			}
    	}
    }
    private void reveal(cell c, ArrayList<cell> visited)
    {
    	if(c.getNum()<0||visited.contains(c))
    		return;
    	c.reveal();
    	if(c.getNum()>0)
    		return;
    	visited.add(c);
    	ArrayList<cell> temp= getSquare(c);
    	for(cell b:temp)
    		reveal(b,visited);
    }
    public void revealMines()
    {
    	for(cell c:group)
    		if(c.getMine())
    			c.reveal();
    }

    public boolean checkFace(int i, int j)
    {
    	if(i>face.getTrueX()&&i<face.getTrueX()+size)
    		if(j>face.getTrueY()&&j<face.getTrueY()+size)
    		{
    			reset();
    			return true;
    		}
    	return false;
    }

    public void flagCell(int i, int j)
    {
    	for(int k=0;k<group.size();k++)
    	{
    		cell c=group.get(k);
    		if(i>c.getTrueX()&&i<c.getTrueX()+size)
    			if(j>c.getTrueY()&&j<c.getTrueY()+size)
    			{
    				c.switchFlag();
    			}
    	}
    }

    public boolean checkButton(int i, int j)
    {
		if(i>button.getTrueX()&&i<button.getTrueX()+size)
    		if(j>button.getTrueY()&&j<button.getTrueY()+size)
    		{
    			button.Switch();
    			return true;
    		}
    	return false;
    }

    public void setMines(int m)
    {
    	mines=m;
    	while(m>0)
    	{
    		int random=(int)(Math.random()*((group.size()-1-0)+1))+0;//# of cells-1=max, 0=min
    		if(!group.get(random).getMine())
    			group.get(random).setMine();
    		m--;
    	}
    	setNums();
    }

    public void setNums()
    {
		for(cell c:group)
		{
			if(!c.getMine())
			{
				int count=0;
				ArrayList<cell> temp= getSquare(c);
				for(cell d:temp)
				{
					if(d.getMine())
						count++;
				}
				c.setNum(count);
			}
		}
    }

    public int getIndex(int c, int r)
    {
    	return c+r*col;
    }

    public ArrayList<cell> getAdjacent(cell b)
    {
    	ArrayList<cell> q=new ArrayList<cell>();

		int index=getIndex(b.getC()+1,b.getR());
		if(b.getC()+1<col)
			q.add(group.get(index));

		index=getIndex(b.getC()-1,b.getR());
		if(b.getC()-1>=0)
			q.add(group.get(index));

		index=getIndex(b.getC(),b.getR()+1);
		if(b.getR()+1<row)
			q.add(group.get(index));

		index=getIndex(b.getC(),b.getR()-1);
		if(b.getR()-1>=0)
			q.add(group.get(index));

		return q;
    }

    public ArrayList<cell> getSquare(cell b)
    {
    	ArrayList<cell> q=new ArrayList<cell>(getAdjacent(b));
    	int index=getIndex(b.getC()+1,b.getR()+1);
		if(b.getC()+1<col&&b.getR()+1<row)
			q.add(group.get(index));

		index=getIndex(b.getC()-1,b.getR()+1);
		if(b.getC()-1>=0&&b.getR()+1<row)
			q.add(group.get(index));

		index=getIndex(b.getC()+1,b.getR()-1);
		if(b.getR()-1>=0&&b.getC()+1<col)
			q.add(group.get(index));

		index=getIndex(b.getC()-1,b.getR()-1);
		if(b.getR()-1>=0&&b.getC()-1>=0)
			q.add(group.get(index));
    	return q;
    }


}