/**
 * @(#)maze.java
 *
 *
 * @author
 * @version 1.00 2019/8/27
 */
import java.util.*;
import static java.lang.System.*;
import java.awt.Graphics;
import java.awt.Color;

public class maze {

	private ArrayList<cell> group;
	private int col;
	private int row;
	private int s;
	private int adjc;
	private int adjr;

    public maze(int w,int h, int size, player p) {
    	s=size;
    	group=new ArrayList<cell>();
    	col=w/s;
    	row=h/s;
    	adjc= (w-(col*s))/2;
    	adjr= (h-(row*s))/2;
    	for(int x=0; x<row;x++)
    	{
    		for(int y=0;y<col;y++)
    		{
    			group.add(new block(x,y,s,adjr,adjc));
    		}
    	}
    	int stop=getRooms(w,h,s);
    	for(int x=0;x<stop;x++)
     	{
     		int[] temp=generateRoom();
    		fillRoom(temp);
    		hideOutside(temp);
    	}

    	//out.println(row+" "+col);
    	//out.println(getAdjacent(group.get(getIndex(col-1,0))));

    	/*fillRoom(new int[]{getIndex(1,1),8,8});
    	hideOutside(new int[]{getIndex(1,1),8,8});
    	fillRoom(new int[]{getIndex(25,15),8,8});
    	hideOutside(new int[]{getIndex(25,15),8,8});
    	drawPath(getEntrances().get(0),getEntrances().get(1));*/

    	/*drawPath(group.get(getIndex(15,14)),group.get(getIndex(17,17)));
    	group.get(getIndex(15,14)).setColour(Color.orange);
    	group.get(getIndex(17,17)).setColour(Color.RED);*/

    	generatePaths();

    	for(cell list:group)
    		if(!list.isHidden&&list.align>6&&list.align!=8)
    		{
    			list.addThing(p);
    			break;
    		}
    	hideAll();
    	reveal();
    }
    public int[] generateRoom()
    {
    	int random=(int)(Math.random()*((row*col-1-0)+1))+0;//row*col-1=max, 0=min
    	int vertical=(int)(Math.random()*((9-6)+1))+6;//7=max, 4=min values+2 for buffer
    	int horizontal=(int)(Math.random()*((12-9)+1))+9;//10=max, 7=min
    	if(group.get(random).getCol()+horizontal>col-1||group.get(random).getRow()+vertical>row-1)
    	{
    		return generateRoom();
    	}
    	for(int y=0,x=random; y<vertical*horizontal; y++,x++)
    	{
    		if(y%horizontal==0&&y!=0)
    		{
    			x--;
    			x+=col;
    			x-=horizontal-1;
    		}
    		if(!group.get(x).isHidden)
    		{
    			return generateRoom();
    		}
    	}
    	return new int[]{random,horizontal,vertical};
    }
    public void fillRoom(int[] dim)
    {
    	boolean test=true;
    	int tick=0;
    	int random=(int)(Math.random()*(((2*(dim[1]-2))+(2*(dim[2]-2))-8-0)))+0;//perimeter-8=max, 0=min
		for(int y=0,x=dim[0],r=0; y<dim[2]*dim[1]; y++,x++)
    	{
    		if(y%dim[1]==0&&y!=0)
    		{
    			x--;
    			x+=col;
    			x-=dim[1]-1;
    			r++;
    		}

    		if(y==dim[1]+1)//top left
    		{
    			group.set(x,new wall(group.get(x).getRow(),group.get(x).getCol(),s,adjr,adjc,2));
    		}
    		else if(y==2*dim[1]-2)//top right
    		{
    			group.set(x,new wall(group.get(x).getRow(),group.get(x).getCol(),s,adjr,adjc,3));
    		}
    		else if(y==dim[1]*(dim[2]-1)+1-dim[1])//bottom left
    		{
    			group.set(x,new wall(group.get(x).getRow(),group.get(x).getCol(),s,adjr,adjc,4));
    		}
    		else if(y==dim[1]*dim[2]-1-dim[1]-1)//bottom right
    			group.set(x,new wall(group.get(x).getRow(),group.get(x).getCol(),s,adjr,adjc,5));
    		else if((y>=dim[1]&&y<2*dim[1])||(y>=dim[1]*(dim[2]-2)&&y<dim[1]*dim[2]-dim[1]))//horizontal
    		{
    			if(!((y)%dim[1]==0||(y+1)%dim[1]==0))//if not 1st or last row
    			{
    				if(tick==random)
    				{
    					group.set(x,new wall(group.get(x).getRow(),group.get(x).getCol(),s,adjr,adjc,6));
    				}
    				else
    					group.set(x,new wall(group.get(x).getRow(),group.get(x).getCol(),s,adjr,adjc,1));
    				tick++;
    			}
    		}
    		else if((y-1)%dim[1]==0||(y+2)%dim[1]==0)//vertical
    		{
    			if(!((y>=0&&y<dim[1])||(y>=dim[1]*(dim[2]-1)&&y<dim[1]*dim[2])))//if not 1st or last col
    			{
    				if(tick==random)
    				{
    					group.set(x,new wall(group.get(x).getRow(),group.get(x).getCol(),s,adjr,adjc,6));
    				}
    				else
    					group.set(x,new wall(group.get(x).getRow(),group.get(x).getCol(),s,adjr,adjc,0));
    				tick++;
    			}
    		}
    		group.get(x).isHidden=false;
    	}
    }

    public void hideOutside(int[] dim)
    {
    	for(int y=0,x=dim[0],r=0; y<dim[2]*dim[1]; y++,x++)
    	{
    		if(y%dim[1]==0&&y!=0)
    		{
    			x--;
    			x+=col;
    			x-=dim[1]-1;
    			r++;
    		}
    		if(y<r*dim[1]+1||y>=r*dim[1]+dim[1]-1)
    			group.get(x).isHidden=true;
    		if(r==0||r==dim[2]-1)
    			group.get(x).isHidden=true;
    	}
    }

    public void generatePaths()//TRAVELING SALESMAN lite LETS GOOOOOOOOOOOOOOO
    {
    	ArrayList<cell> ent=getEntrances();
    	generatePaths(ent);
    }

    private void generatePaths(ArrayList<cell> ent)
    {
    	if(ent.size()>1)
    	{
    		ArrayList<Double> nums=new ArrayList<Double>();
    		for(int x=1; x<ent.size();x++)
	    	{
    			double distance=Math.sqrt(Math.pow(ent.get(0).r-ent.get(x).r,2)+Math.pow(ent.get(0).c-ent.get(x).c,2));
    			nums.add(distance);
 	   		}
    		double min=Integer.MAX_VALUE;
    		int index=0;
    		for(int x=0; x<nums.size();x++)
    		{
				if(nums.get(x)<min)
				{
					min=nums.get(x);
					index=x;
				}
    		}
    		index++;
    		drawPath(ent.get(0),ent.get(index));
			ent.set(0,ent.get(index));
			ent.remove(index);
			generatePaths(ent);
    	}
    }

    private void drawPath(cell a, cell b)//from a to b
    {
		ArrayList<cell> q=new ArrayList<cell>();
		ArrayList<Integer> count=new ArrayList<Integer>();
		int counter=0;
		int pointer=0;
		int size=1;
		q.add(b);
		count.add(counter);
		while(!q.contains(a))
		{
			ArrayList<cell> tempList=new ArrayList<cell>();
			counter++;
			for(int i=0;i<size;i++)
			{
				cell temp=q.get(pointer);
				int c=count.get(pointer);
				ArrayList<cell> adjacent=getAdjacent(temp);
				for(int x=adjacent.size()-1; x>=0;x--)
				{
					cell list=adjacent.get(x);
					if(list.align<=5)
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
					list.setColour(Color.gray);
				}
				pointer++;
			}
			size=tempList.size();
		}
		pointer=q.indexOf(a);
		ArrayList<cell> path=new ArrayList<cell>();
		path.add(q.get(pointer));
		while(!path.contains(b))
		{
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
		}
		for(cell list:path)
		{
			list.isHidden=false;
			if(list.align!=6)
				list.setAlign(8);
		}
    }

    public ArrayList<cell> getAdjacent(cell b)
    {
    	ArrayList<cell> q=new ArrayList<cell>();

		int index=getIndex(b.c+1,b.r);
		if(b.c+1<col)
			q.add(group.get(index));

		index=getIndex(b.c-1,b.r);
		if(b.c-1>=0)
			q.add(group.get(index));

		index=getIndex(b.c,b.r+1);
		if(b.r+1<row)
			q.add(group.get(index));

		index=getIndex(b.c,b.r-1);
		if(b.r-1>=0)
			q.add(group.get(index));

		return q;
    }

    public ArrayList<cell> getSquare(cell b)
    {
    	ArrayList<cell> q=new ArrayList<cell>(getAdjacent(b));
    	int index=getIndex(b.c+1,b.r+1);
		if(b.c+1<col&&b.r+1<row)
			q.add(group.get(index));

		index=getIndex(b.c-1,b.r+1);
		if(b.c-1>=0&&b.r+1<row)
			q.add(group.get(index));

		index=getIndex(b.c+1,b.r-1);
		if(b.r-1>=0&&b.c+1<col)
			q.add(group.get(index));

		index=getIndex(b.c-1,b.r-1);
		if(b.r-1>=0&&b.c-1>=0)
			q.add(group.get(index));
    	return q;
    }

    public int getIndex(int c, int r)
    {
    	return c+r*col;
    }

    private ArrayList<cell> getEntrances()
    {
    	ArrayList<cell> result=new ArrayList<cell>();
    	for(cell c:group)
    		if(c.align==6)
    			result.add(c);
    	return result;
    }

    public int getRooms(int w, int h, int s)
    {
    	int total=row*col;
    	int random=(int)(Math.random()*100+1);
    	if(total>=100&&total<=500)
    	{
    	}
		return 10;
    }

    public cell get(int x)
    {
    	return group.get(x);
    }

    public void move(String dir)
    {
    	ArrayList<entity> hasMoved= new ArrayList<entity>();
    	for(int x=0; x<group.size();x++)//loop through the cells
    	{
			cell c=group.get(x);
			if(!c.isEmpty())
			{
				int size=c.getQSize();
				for(int y=0;y<size;y++)
				{
					if(!hasMoved.contains(c.getThing())){
						int[] coord=c.getThing().mvmt(dir);
						hasMoved.add(c.getThing());
						if(c.getCol()+coord[0]<0)//left bound
							coord[0]=-c.getCol();
						if(c.getCol()+coord[0]>col-1)//right bound
							coord[0]=col-1-c.getCol();
						if(c.getRow()+coord[1]<0)//up bound
							coord[1]=-c.getRow();
						if(c.getRow()+coord[1]>row-1)//down bound
							coord[1]=row-1-c.getRow();
						if(group.get(x+coord[0]).align>=6&&!group.get(x+coord[0]).isHidden)
							moveThing(c,group.get(x+coord[0]));//move left and right
						if(coord[1]!=0)
							if(group.get(x+(coord[1]*col)).align>=6&&!group.get(x+(coord[1]*col)).isHidden)
								moveThing(group.get(x+coord[0]),group.get(x+(coord[1]*col)));//move up and down
					}
				}
			}
    	}
    	reveal();
    }

    public cell findPlayer()
    {
    	for(cell c:group)
    		if(c.getThing()!=null&&c.getThing().type()==0)
    			return c;
    	return null;
    }

    public void hideAll()
    {
    	for(cell c:group)
    		c.isHidden=true;
    }

    public void reveal()
    {
		if(findPlayer().align==7)
		{
			reveal(findPlayer(),new ArrayList<cell>());
		}
		else if(findPlayer().align==6)
		{
			ArrayList<cell> adjacent=getAdjacent(findPlayer());
			for(int x=0; x<adjacent.size(); x++)
    		{
    			adjacent.get(x).isHidden=false;
    		}
		}
		else
		{
			ArrayList<cell> adjacent=getSquare(findPlayer());
			for(int x=0; x<adjacent.size(); x++)
    		{
    			if(adjacent.get(x).align==8||adjacent.get(x).align==6)
    				adjacent.get(x).isHidden=false;
    		}
		}
    }
    private void reveal(cell c, ArrayList<cell> visited)
    {
    	if(visited.contains(c))
    		return;
    	visited.add(c);
    	ArrayList<cell> adjacent=getSquare(c);
    	for(int x=0; x<adjacent.size(); x++)
    	{
    		adjacent.get(x).isHidden=false;
    		if(adjacent.get(x).align==7)
    			reveal(adjacent.get(x),visited);
    	}
    }

    public void generateMonsters()
    {

    }

    public void draw(Graphics window)
    {
    	for(cell c:group)
    		c.draw(window);
    	/*for(int x=0;x<group.size();x++)
    		group.get(x).draw(window,x);*/
    }
    public void moveThing(cell a, cell b)//from a to b
    {
    	b.addThing(a.removeThing());
    }
    public String toString()
    {
		String output="";
		for(cell c:group)
		{
			output+=c+"\n";
		}
		return output;
    }


}