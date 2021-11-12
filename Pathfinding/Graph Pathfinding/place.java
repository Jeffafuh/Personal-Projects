/**
 * @(#)place.java
 *
 *
 * @author
 * @version 1.00 2020/1/28
 */
import java.awt.*;
import java.util.*;
import static java.lang.System.*;

public class place extends Object{

	//cell manipulation
	private TreeMap<cell,TreeSet<cell>> map;
	private ArrayList<cell> buttons;
	private ArrayList<line> lines;
	private Queue<cell> selected;
	private int deltaX, deltaY;
	private int size;
	private cell multiple;
	private String action;
	private int width;
	private int length;

	//boolean list
	private boolean isSpeed;
	private boolean markMode;
	private boolean editGMode;

	//path-finding list
	private ArrayList<cell> store;
	private ArrayList<cell> path;
	private ArrayList<Integer> count;
	private cell a;
	private cell b;
	private int counter;
	private int pointer;
	private int pointerSize;
	private String mode;

	//line editing
	private line storeLine;

    public place() {
    	//cell manipulation
    	selected=new LinkedList<cell>();
    	size=50;
    	deltaX=deltaY=0;
    	multiple=null;
    	buttons=new ArrayList<cell>();
    	lines=new ArrayList<line>();

    	//button list
    	buttons.add(new cell(900,900,"ADD"));//add node
    	buttons.add(new cell(800,900,"CONNECT"));//build all connections
    	buttons.add(new cell(700,900,"CLEAR"));//clear all
    	buttons.add(new cell(600,900,"QBUILD"));//speed build
    	buttons.add(new cell(500,900,"DELETE"));//delete one cell
    	buttons.add(new cell(400,900,"PATH"));//build the path
    	buttons.add(new cell(300,900,"CLEAN"));//clear all booleans on cells
    	buttons.add(new cell(200,900,"EDIT"));//edit "G" values of the lines
    	buttons.add(new cell(100,900,"DIJKSTRA"));//build path w/ f values
    	map=new TreeMap<cell,TreeSet<cell>>();

    	//booleans
    	isSpeed=false;
    	markMode=false;
    	editGMode=false;

    	//path-finding
    	store=new ArrayList<cell>();
    	path=new ArrayList<cell>();
    	count=new ArrayList<Integer>();
    	counter=0;
    	pointer=0;
    	pointerSize=1;
    	mode="";

    	//line editing
    	storeLine=null;
    }

    public void setDimensions(int w,int l)
    {
    	width=w;
    	length=l;
    }

    public void draw(Graphics window)
    {
    	for(cell c:map.keySet())
    	{
    		c.draw(window);
    	}
    	for(cell c:buttons)
    	{
    		c.draw(window);
    	}
    	for(line l:lines)
    		l.draw(window);
    }

    public void createCell()
    {
    	int randX=(int)(Math.random()*((width-size)+1))+size;//width=max, size=min
    	int randY=(int)(Math.random()*((length-size)+1))+size;//length=max, size=min
    	map.put(new cell(randX,randY,size),new TreeSet<cell>());
    }

    public void scroll(int x,int y)
    {
		cell temp=getCell(x,y);
		if(temp==null)
			return;
		if(multiple==null)
			multiple=temp;
		if(deltaX!=0||deltaY!=0)
		{
			multiple.shiftLoc(deltaX-x,deltaY-y);
			shiftLine(multiple);
		}
		deltaX=x;
		deltaY=y;
    }

    public void shiftLine(cell c)
    {
    	for(cell d:map.get(c))
    	{
    		for(line l:lines)
    		{
    			if(l.isLine(c,d))
    			{
					l.shiftCell(c);
    			}
    		}
    	}
    }

    public void doPathFinding()
    {
		if(mode.equals("flood"))
			findPath();
		if(mode.equals("dij"))
			Dijkstra();
    }

    public boolean isPathFinding()
    {
    	if(mode.length()>0)
    		return true;
    	return false;
    }

    public void checkPathFinding()
    {
		if(mode.length()>0)
			if(selected.size()==2)
			{
				if(mode.equals("flood"))
				{
					a=selected.remove();
					b=selected.remove();
					a.setMarked(true);
					b.setMarked(true);
					count.add(counter);
					store.add(a);
				}
				if(mode.equals("dij"))
				{
					a=selected.remove();
					b=selected.remove();
					a.setMarked(true);
					b.setMarked(true);
					count.add(counter);
					store.add(a);
				}
			}
    }


    public void clickButton(int x,int y)
    {
		for(int i=0;i<buttons.size();i++)
		{
			if(x>buttons.get(i).getX()&&x<buttons.get(i).getX()+buttons.get(i).getSize())
				if(y>buttons.get(i).getY()&&y<buttons.get(i).getY()+buttons.get(i).getSize())
				{
					if(i==0)
					{
						createCell();
					}
					if(i==1)
					{
						popAllConnections();
					}
					if(i==2)
					{
						clearAll();
					}
					if(i==3)
					{
						buttons.get(i).switchToggle();
						isSpeed=!isSpeed;
					}
					if(i==4)
					{
						deleteSelected();
					}
					if(i==5)
					{
						if(mode.length()==0)
							mode="flood";
						else mode="";
						buttons.get(i).switchToggle();
					}
					if(i==6)
					{
						resetValues();
						wipeCells();
					}
					if(i==7)
					{
						buttons.get(i).switchToggle();
						editGMode=!editGMode;
						for(line l:lines)
						{
							if(editGMode)
								l.reveal();
							else
							{
								if(storeLine!=null)
								{
									storeLine.setBoxSelect(false);
									storeLine=null;
								}
								l.hide();
							}
						}
					}
					if(i==8)
					{
						if(mode.length()==0)
							mode="dij";
						else mode="";
						buttons.get(i).switchToggle();
					}
				}
		}
    }

    public void popAllConnections()
    {
    	if(selected.isEmpty())
    		return;
    	popAllConnections(selected.remove());
    }
	private void popAllConnections(cell a)
    {
    	a.switchSelected();
    	if(selected.isEmpty())
    		return;
    	cell temp=selected.remove();
    	buildConnection(a,temp);
    	popAllConnections(temp);
    }

    public void clearAll()
    {
    	map=new TreeMap<cell,TreeSet<cell>>();
    	lines=new ArrayList<line>();
    	selected=new LinkedList<cell>();
    	resetValues();
    }

    public void wipeCells()
    {
    	for(cell c:map.keySet())
    	{
    		c.initBooleans();
    	}
    	for(line l:lines)
    		l.initBooleans();
    }

    private void buildConnection(cell a,cell b)
    {
    	lines.add(new line(a,b));
    	map.get(a).add(b);
    	map.get(b).add(a);
    }

    public cell getCell(int x, int y)
    {
    	for(cell c:map.keySet())
    	{
    		if(x>c.getX()&&x<c.getX()+size)
    			if(y>c.getY()&&y<c.getY()+size)
    				return c;
    	}
    	return null;
    }

    public cell selectButton(int x,int y)
    {
    	for(cell c:buttons)
    	{
    		if(x>c.getX()&&x<c.getX()+size)
    			if(y>c.getY()&&y<c.getY()+size)
    				return c;
    	}
    	return null;
    }

    public void selectCell(int x,int y)
    {
		cell temp=getCell(x,y);
		if(temp!=null)
			selection(temp);
    }

    private void selection(cell c)
    {
    	if(selected.contains(c))
		{
			selected.remove(c);
		}
		else
			selected.add(c);
		c.switchSelected();
		if(mode.length()>0)
			c.switchMarked();
		if(selected.size()>=2&&isSpeed) //for quick-select mode
		{
			cell a=selected.remove();
			cell b=selected.remove();
			selected.add(a);
			selected.add(b);
			popAllConnections(selected.remove());
			b.switchSelected();
			selected.add(b);
		}
    }

    public void deleteSelected()
    {
    	while(!selected.isEmpty())
    	{
    		cell c=selected.remove();
    		for(cell a:map.get(c))
    		{
    			for(int i=lines.size()-1;i>=0;i--)
    				if(lines.get(i).isLine(a,c))
    					lines.remove(i);
    			map.get(a).remove(c);
    		}
    		map.remove(c);
    	}
    }

    public void selectLine(int x,int y)
    {
    	if(editGMode)
    	{
			line temp=getLine(x,y);
			if(temp!=null)
				selectLine(temp);
    	}
    }

    private line getLine(int x,int y)
    {
    	for(line c:lines)
    	{
    		if(c.checkInBox(x,y))
    			return c;
    	}
    	return null;
    }

    private void selectLine(line l)
    {
    	if(storeLine!=null)
    		storeLine.setBoxSelect(false);
    	l.setBoxSelect(true);
		storeLine=l;
    }

    public void sendInput(char s)
    {
		if(s>='0'&&s<='9')
			if(storeLine!=null)
				storeLine.appendG(s);
    }
    public void terminateInput()
    {
    	if(storeLine!=null)
    		storeLine.setBoxSelect(false);
    	storeLine=null;
    }
    public void backspace()
    {
    	if(storeLine!=null)
    		storeLine.deleteLast();
    }

    public void clearCache()
    {
    	deltaX=deltaY=0;
    	multiple=null;
    }

    public int getWidth()
    {
    	return width;
    }

    public void dilate(int d)
    {
    	size-=d;
    	dilateCells();
    }
    private void dilateCells()
    {
    	for(cell c:map.keySet())
    	{
    		c.setSize(size);
    	}
    }

    public void resetValues()
    {
		store=new ArrayList<cell>();
    	path=new ArrayList<cell>();
    	count=new ArrayList<Integer>();
    	counter=0;
    	pointer=0;
    	pointerSize=1;
    }

    public void findPath()
    {
    	if(store.contains(b))
    		seekPath();
    	else
    	{
    		ArrayList<cell> tempList=new ArrayList<cell>();
    		counter++;
    		for(int i=0;i<pointerSize;i++)
    		{
    			cell temp=store.get(pointer);
    			int c=count.get(pointer);
    			for(cell adj:map.get(temp))
    			{
    				boolean skip=false;
    				if(store.contains(adj))
    				{
    					if(count.get(store.indexOf(adj))<=counter)
    					{
    						skip=true;
    					}
    					else if(count.get(store.indexOf(adj))>counter)
    						store.remove(store.indexOf(adj));
    				}
    				if(!skip)
    				{
    					getLine(adj,store.get(pointer)).setSpecMark(true);
    					store.add(adj);
    					tempList.add(adj);
    					count.add(counter);
    				}
    			}
    			pointer++;
    		}
    		pointerSize=tempList.size();
    		for(cell d:tempList)
    		{
    			if(d.equals(b))
    			{
    				pointer=store.indexOf(d);
    				d.switchSpecMark();
    			}
    			else d.switchSelected();
    		}
    	}
    }

    private int stepCount=0;//1=select all connect 2=pick right line 3=select cell
    private void seekPath()
    {
    	stepCount++;
		if(path.contains(a))
		{
			resetValues();
			a.switchSpecMark();
			mode="";
			buttons.get(5).switchToggle();
		}
		else
		{
			int lowest= Integer.MAX_VALUE;
			int storeOrig=pointer;
			for(cell list:map.get(store.get(pointer)))
			{
				if(stepCount==1)
				{
					if(getLine(store.get(pointer),list).getSpecMark())
						getLine(store.get(pointer),list).setSelected(true);
				}
				else
					if(store.indexOf(list)>=0&&count.get(store.indexOf(list))<lowest)
					{
						lowest=count.get(store.indexOf(list));
						pointer=store.indexOf(list);
					}
			}
			if(stepCount==2)
			{
				getLine(store.get(pointer),store.get(storeOrig)).setMarked(true);
				pointer=storeOrig;
			}
			if(stepCount==3)
			{
				cell list=store.get(pointer);
				path.add(list);
				list.switchPath();
				stepCount=0;
			}
		}
    }

    public void Dijkstra()
    {

    }
	private int getG(cell a, cell b)
	{
		line store=getLine(a,b);
		return store.getG();
	}
	private int getH(cell a,cell b)
	{
		return 0;
	}

    private line getLine(cell a,cell b)
    {
		for(line l:lines)
			if(l.isLine(a,b))
				return l;
		return null;
    }








}