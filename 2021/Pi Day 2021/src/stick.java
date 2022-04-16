import java.awt.geom.Line2D;
import java.awt.Color;

public class stick extends Object{
	
	private int[] coords;
	private double theta;
	private Line2D line; 
	private Color color;
	
	public stick(int maxX, int maxY, double iHat, double jHat, double len)
	{
		coords = new int[2];
		coords[0] = (int)(Math.random()*maxX);
		coords[1] = (int)(Math.random()*maxY);
		theta = Math.random()*2*Math.PI;
		color = new Color(210,171,86);
		adjustCoords(maxX, maxY, iHat, jHat, len);
	}
	
	public stick(int x, int y, double angle)
	{
		coords= new int[2];
		coords[0] = x;
		coords[1] = y;
		theta = angle;
	}
	
	public void adjustCoords(int width, int height, double iHat, double jHat, double stickLen)
	{
		double x1 = coords[0];
		double y1 = coords[1];
		
		double dX = (stickLen * iHat * Math.cos(theta));
		double dY = (stickLen * jHat * Math.sin(theta));
		
		double x2 = x1 + dX;
		double y2 = y1 + dY;
		
		if(y2 < 0)
		{
			y1 -= y2;
		}
		else if(y2 > height)
		{
			y1 -= y2 - height;
		}
		else if(x2 > width)
		{
			x1 -= x2 - width;
		}
		else if(x2 < 0)
		{
			x1 -= x2;
		}
		
		x2 = x1 + dX;
		y2 = y1 + dY;
		
		setLine(new Line2D.Double(x1, y1, x2, y2));
	}
	
	public void setLine(Line2D l)
	{
		line = l;
	}
	
	public void setColor(Color c)
	{
		color = c;
	}
	
	public int[] getCoords()
	{
		return coords;
	}
	
	public double getTheta()
	{
		return theta;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public Line2D getLine()
	{
		return line;
	}
	
	public String toString()
	{
		return coords[0] + " " + coords[1] + " " + theta;
	}

}
