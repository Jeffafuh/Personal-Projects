import java.awt.Color;
import java.util.*;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class coordinatePlane {
	
	private int width;
	private int height;
	private double iHat;
	private double jHat;
	private double stickLen;
	private double stripLen;
	private ArrayList<stick> stickList;
	private ArrayList<Rectangle2D> stripList;
	
	private int aniCount;
	private int crossCount;

	public coordinatePlane(int w, int h, int numSticks, int stickLength, int stripWidth)
	{
		width = w;
		height = h;
		iHat = 50;
		jHat = -50;
		stripLen = stripWidth;
		stickLen = stickLength;
		stickList = new ArrayList<stick>();
		stripList = new ArrayList<Rectangle2D>();
		crossCount = 0;
		aniCount = 0;
		
		initStrips();
		generateSticks(numSticks);
	}
	
	public void generateSticks(int numSticks)
	{
		stickList = new ArrayList<stick>();
		for(int i = 0; i < numSticks; i++)
		{
			stick temp = new stick(width, height, iHat, jHat, stickLen);
			stickList.add(temp);
		}
	}
	
	public void initStrips()
	{
		stripList = new ArrayList<Rectangle2D>();
		for(double i = 0; i <= width; i+= stripLen * iHat)
		{
			Rectangle2D temp = new Rectangle2D.Double(i,0,stripLen * iHat, height);
			stripList.add(temp);
		}
	}
	
	public void draw(Graphics2D window)
	{
		drawStrips(window);
		drawSticks(window);
		window.setStroke(new BasicStroke(1));
	}
	
	public int animate(Graphics2D window)
	{
		drawStrips(window);
		return drawStick(window);
	}
	
	public void skipAnimation()
	{
		for(int i = 0; i < stickList.size(); i++)
		{
			stick currStick = stickList.get(i);
			int tempCount = countCrosses(currStick);
			if(tempCount > 1)
			{
				crossCount++;
				currStick.setColor(Color.RED);
			}
		}
	}
	
	public void drawSticks(Graphics2D window)
	{
		window.setStroke(new BasicStroke((int)(2 * stickLen)));
		for(int i = 0; i < stickList.size(); i++)
		{
			stick currStick = stickList.get(i);
			window.setColor(currStick.getColor());
			window.draw(currStick.getLine());
		}
	}
	
	public int drawStick(Graphics2D window)
	{
		window.setStroke(new BasicStroke((int)(2 * stickLen)));
		if(aniCount < stickList.size())
		{
			for(int i = 0; i < aniCount; i++)
			{
				stick currStick = stickList.get(i);
				window.setColor(currStick.getColor());
				window.draw(currStick.getLine());
			}
		}
		else
		{
			drawSticks(window);
			stick currStick = stickList.get(aniCount - stickList.size());
			int tempCount = countCrosses(currStick);
			if(tempCount > 1)
			{
				crossCount++;
				currStick.setColor(Color.RED);
			}
		}
		
		aniCount++;
		window.setStroke(new BasicStroke(1));
		if(aniCount == 2*stickList.size())
			return 0;
		else return 1;
	}
	
	public void drawStrips(Graphics2D window)
	{
		for(int i = 0; i < stripList.size(); i++)
		{
			if(i % 2 == 0)
				window.setColor(new Color(96,79,57)); // dark
			else window.setColor(new Color(130,106,75)); // light  
			
			window.fill(stripList.get(i));
		}
	}
	
	public int countCrosses(stick s)
	{
		int rectCrosses = 0;
		
			for(int j = 0; j < stripList.size(); j++)
			{
				if(stripList.get(j).intersectsLine(s.getLine()))
				{
					rectCrosses++;
				}
			}
			
		return rectCrosses;
	}
	
	public int getCrossCount()
	{
		return crossCount;
	}
}
