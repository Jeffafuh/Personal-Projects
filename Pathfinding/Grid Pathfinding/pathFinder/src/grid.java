/**
 * @(#)grid.java
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
import static java.lang.System.*;

public class grid extends Object {
	private Queue<cell> selected;
	private final int buttons=5;
	private final static int size=40;
	private int row;
	private int col;
	private int x;
	private int y;
	private ArrayList<cell> group;
	private ArrayList<cell> multiple;
	private boolean generating;

	//maze-generation variables!
	private int s;//size
	private int pointer;
	private int counter;
	private ArrayList<cell> q;
	private ArrayList<cell> path;
	private ArrayList<Integer> count;
	private cell a;
	private cell b;
	private cell prev;

	//scrolling values
	private int deltaX;
	private int deltaY;



    public grid(int w,int h,int c,int r)
    {
    	generating=false;
    	selected= new LinkedList<cell>();
    	multiple=new ArrayList<cell>();
    	col=w/size;
    	row=h/size;
    	x=c;
    	y=r;
    	deltaX=0;
    	deltaY=0;
    	group=new ArrayList<cell>();
    	for(int i=0; i<row;i++)
    		for(int j=0;j<col;j++)
    			group.add(new cell(i,j,size,x,y));
    	//group.add(new cell(800,800,size,"ASTAR"));//star
    	group.add(new cell(700,800,size,"GREED"));//greed
    	group.add(new cell(600,800,size,"FLOOD"));//flood
    	group.add(new cell(500,800,size,"CLEAN"));//clear
    	group.add(new cell(400,800,size,"CLEAR"));//full clear

    	//maze-generation
    	a=null;
    	b=null;
    	prev=null;
		q=new ArrayList<cell>();
		path=new ArrayList<cell>();
		count=new ArrayList<Integer>();

		counter=0;
		pointer=0;
		s=1;
    }

    public String selectCell(int i, int j,String string)
    {
    	for(int k=1; k<=buttons;k++)
		{
			cell c=group.get(group.size()-k);
			if(i>c.gtx()&&i<c.gtx()+size)
    			if(j>c.gty()&&j<c.gty()+size)
    			{
    				if(k==buttons-4)//full clear
    				{
    					resetCells();
    					return "no";
    				}
    				if(k==buttons-3)//clear
    				{
    					resetSelected();
    					return "no";
    				}
    				if(k==buttons-2)//flood
    				{
  	 	 				b=selected.remove();
						a=selected.remove();
						q.add(b);
						count.add(counter);
						return "flood";
    				}
    				if(k==buttons-1)//greed
    				{
  	 	 				b=selected.remove();
						a=selected.remove();
						prev=b;
    					return "greed";
    				}
    				if(k==buttons)//A*
    				{
						return "star";
    				}
    			}
		}
		for(int k=0; k<group.size()-buttons;k++)
		{
			cell c=group.get(k);
			if(i>c.gtx()&&i<c.gtx()+size)
    			if(j>c.gty()&&j<c.gty()+size)
    			{
    				if(!multiple.contains(c))
    				{
    					if(string.equals("left"))
    						c.switchWall();
    					else if(string.equals("right"))
    						selection(c);
    					multiple.add(c);
    				}
    			}
		}
    	return "no";
    }

    public void scroll(int i, int j)
    {
		if(deltaX!=0||deltaY!=0)
		{
			x=x-(deltaX-i);
			y=y-(deltaY-j);
			shiftCells(x,y);
		}
			deltaX=i;
			deltaY=j;
    }
    private void shiftCells(int i, int j)
    {
    	for(int x=0;x<group.size()-buttons;x++)
    	{
			group.get(x).shiftLoc(i,j);
    	}
    }

    private void switchBlue()//testing purposes ONLY
    {
    	for(cell c:group)
    		c.switchColor();
    }

    private void selection(cell c)
    {
		if(!selected.isEmpty()&&selected.contains(c))
		{
			selected.remove(c);
		}
		else
			selected.add(c);
		c.switchSelect();
		if(selected.size()>2)
		{
			selected.remove().switchSelect();
		}
    }

    public String find()
    {
		if(q.contains(a))
		{
			return findPath();
		}
		else
		{
			ArrayList<cell> tempList=new ArrayList<cell>();
			counter++;
			for(int i=0;i<s;i++)
			{
				cell temp=q.get(pointer);
				int c=count.get(pointer);
				ArrayList<cell> adjacent=getAdjacent(temp);
				for(int x=adjacent.size()-1; x>=0;x--)
				{
					cell list=adjacent.get(x);
					if(list.getWall())
						adjacent.remove(x);
					if(q.contains(list))
					{
						if(count.get(q.indexOf(list))<=counter)
						{
							adjacent.remove(x);
						}
						else if(count.get(q.indexOf(list))>counter)
							q.remove(q.indexOf(list));
					}
				}
				for(cell list:adjacent)
				{
					q.add(list);
					tempList.add(list);
					count.add(counter);
				}
				pointer++;
			}
			s=tempList.size();
			float color=getCAdj(counter);
			for(cell d:tempList)
			{
				if(!d.getWall())
				{
					d.switchColor();
					d.setColorAdj(color);
				}
			}
		}

    	return "flood";
    }

    public String findPath()
    {
    	if(path.isEmpty())
    	{
    		counter=0;
    		pointer=q.indexOf(a);
			path.add(q.get(pointer));
    	}

    	if(path.contains(b))
    	{
    		resetValues();
    		return "no";
    	}
    	else{

			ArrayList<cell> adjacent=getAdjacent(q.get(pointer));
			int lowest=Integer.MAX_VALUE;
			for(cell list:adjacent)
			{
				if(q.indexOf(list)>=0&&count.get(q.indexOf(list))<lowest)
				{
					lowest=count.get(q.indexOf(list));
					pointer=q.indexOf(list);
				}
			}
			path.add(q.get(pointer));
			cell list=q.get(pointer);

			if(!list.getWall()&&!list.equals(b))
			{
				list.switchPath();
			}

    	}
    	return "flood";
    }

    public String Greedy()
    {
    	counter++;
    	if(!prev.getPath()&&!prev.equals(a)&&!prev.equals(b))
    	{
    		prev.switchPath();
    		return "greed";
    	}
    	if(prev.equals(a))
    	{
    		resetValues();
    		return "no";
    	}
		ArrayList<cell> adjacent=getSquare(prev);
		TreeMap<Double,cell> heu=new TreeMap<Double,cell>();
		for(cell c:adjacent)
		{
			if(!c.getWall()&&!path.contains(c))
			{
				heu.put(getH(c,a),c);
				if(!c.getColor())
				{
					c.switchColor();
					c.setColorAdj(getCAdj((int)getH(c,a)));
				}
			}
		}
		path.add(prev);
		prev=heu.get(heu.firstKey());
    	return "greed";
    }

    public String AStar()
    {
    	if(path.contains(a))
    	{
    		resetValues();
    		return "no";
    	}
    	return "star";
    }

    private double getF(cell a,cell b)
    {
    	return getH(a,b)+getG();
    }

    private double getH(cell a, cell b)
    {
    	return Math.sqrt(Math.pow(a.getCol()-b.getCol(),2)+Math.pow(a.getRow()-b.getRow(),2));
    }

    private double getG()
    {
		return 1.0;
    }

    private void deselectAll()
    {
    	for(cell c:group)
    	{
    		c.setColor(false);
    	}
    }

    public void resetValues()
    {
    	prev=null;
		q=new ArrayList<cell>();
		path=new ArrayList<cell>();
		count=new ArrayList<Integer>();

		counter=0;
		pointer=0;
		s=1;
    }

	public void resetCells()
	{
		resetValues();
		selected=new LinkedList<cell>();
		for(cell c:group)
		{
			c.reset();
		}
	}

	public void resetSelected()
	{
		selected.add(b);
		selected.add(a);
		resetValues();
		for(cell c:group)
		{
			if(!c.getSelected()&&!c.getWall())
				c.reset();
		}
	}

    private float getCAdj(int t)
    {
    	if(t==0)
    		return 0;
    	return (float)(0.6/(1+5*Math.exp(-0.08047*t)));
    }

    public void draw(Graphics window)
    {
    	for(cell c:group)
    		c.draw(window);
    }

    public ArrayList<cell> getAdjacent(cell b)
    {
    	ArrayList<cell> q=new ArrayList<cell>();

		int index=getIndex(b.getCol()+1,b.getRow());
		if(b.getCol()+1<col)
			q.add(group.get(index));

		index=getIndex(b.getCol()-1,b.getRow());
		if(b.getCol()-1>=0)
			q.add(group.get(index));

		index=getIndex(b.getCol(),b.getRow()+1);
		if(b.getRow()+1<row)
			q.add(group.get(index));

		index=getIndex(b.getCol(),b.getRow()-1);
		if(b.getRow()-1>=0)
			q.add(group.get(index));

		return q;
    }

    public ArrayList<cell> getSquare(cell b)
    {
    	ArrayList<cell> q=new ArrayList<cell>(getAdjacent(b));
    	int index=getIndex(b.getCol()+1,b.getRow()+1);
		if(b.getCol()+1<col&&b.getRow()+1<row)
			q.add(group.get(index));

		index=getIndex(b.getCol()-1,b.getRow()+1);
		if(b.getCol()-1>=0&&b.getRow()+1<row)
			q.add(group.get(index));

		index=getIndex(b.getCol()+1,b.getRow()-1);
		if(b.getRow()-1>=0&&b.getCol()+1<col)
			q.add(group.get(index));

		index=getIndex(b.getCol()-1,b.getRow()-1);
		if(b.getRow()-1>=0&&b.getCol()-1>=0)
			q.add(group.get(index));
    	return q;
    }

    public int getIndex(int c, int r)
    {
    	return c+r*col;
    }

    public void clearCache()
    {
    	deltaX=0;
    	deltaY=0;
    	multiple=new ArrayList<cell>();
    }

    public ArrayList<cell> getGrid()
    {
    	return group;
    }


}