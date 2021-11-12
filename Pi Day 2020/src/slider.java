import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import static java.lang.System.*;

public class slider extends Object{
	private double[] box;
	private double boxWidth;
	private double boxHeight;
	private int Start;
	private int Stop;
	private int Y;

    public slider() {
    	out.println("whoops");
    }
    public slider(int start,int stop,int yVal)
    {
    	Start=start;
    	Stop=stop;
    	Y=yVal;
    	box=new double[2];
    	box[0]=Start;
    	box[1]=yVal;
    	boxWidth=20;
    	boxHeight=35;
    }

    public void draw(Graphics2D window)
    {
    	window.setColor(Color.WHITE);
    	drawSlider(window);
    }

    public boolean checkWithinBounds(int x1,int y1)
    {
    	if(x1>box[0]-boxWidth/2&&x1<box[0]+boxWidth/2)
    		if(y1>box[1]-boxHeight/2&&y1<box[1]+boxHeight/2)
    			return true;
    	return false;
    }

    private void drawSlider(Graphics2D window)
    {
    	window.setColor(Color.WHITE);
    	double dY=25;
    	drawSmallerLines(window,Start,Stop,dY,Y,1);
    	window.drawLine(Start,Y,Stop,Y);
    	window.draw(new Line2D.Double(Start,Y+dY,Start,Y-dY));
    	window.draw(new Line2D.Double(Stop,Y+dY,Stop,Y-dY));

    	window.setColor(Color.GRAY);
    	window.fill(new Rectangle2D.Double(box[0]-boxWidth/2,box[1]-boxHeight/2,boxWidth,boxHeight));
    	window.setColor(Color.BLACK);
    	window.draw(new Rectangle2D.Double(box[0]-boxWidth/2,box[1]-boxHeight/2,boxWidth,boxHeight));
    }

    private void drawSmallerLines(Graphics2D window,double start,double end,double dY,int permY,int divisions)
    {
    	if(divisions>4)
    		return;
    	double mid=(end+start)/2;
    	window.draw(new Line2D.Double(mid,permY+dY,mid,permY-dY));
    	dY/=2;
    	divisions++;
    	drawSmallerLines(window,start,mid,dY,permY,divisions);
    	drawSmallerLines(window,mid,end,dY,permY,divisions);
    }

    public double getConstant()
    {
    	return box[0];
    }

    public void scroll(int x,int y)
    {
    	if(box[0]-x>Start&&box[0]-x<Stop)
    		box[0]-=x;
    }

    public int getStart()
    {
    	return Start;
    }
    public int getStop()
    {
    	return Stop;
    }
    
    public void setBox(int x)
    {
    	box[0] = x;
    }



}