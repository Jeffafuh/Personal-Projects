/**
 * @(#)line.java
 *
 *
 * @author
 * @version 1.00 2020/2/6
 */
import java.awt.*;
import java.util.*;
import static java.lang.System.*;

public class line extends Object{

	private cell a;
	private cell c;
	private Color color;

	//advanced g stuff
	private int gVal;
	private String len;

	//booleans
	private boolean selected;
	private boolean displayG;
	private boolean boxSelected;
	private boolean marked;
	private boolean specMark;

    public line() {

    }

    public line(cell b, cell d)
    {
    	a=b;
    	c=d;
    	gVal=1;
    	len="1";
    	color=Color.WHITE;
    	initBooleans();
    }

    public void initBooleans()
    {
    	selected=false;
    	displayG=false;
    	boxSelected=false;
    	marked=false;
    	specMark=false;
    }

    public int getG()
    {
    	return Integer.parseInt(len);
    }

    public void draw(Graphics window)
    {
    	int wordSize=30;
    	window.setFont(new Font("TimesRoman",Font.PLAIN,wordSize));
    	if(displayG)
    	{
    		if(boxSelected)
    			window.setColor(Color.GRAY);
    		else window.setColor(Color.WHITE);
    		window.fillRect((c.getMidX()+a.getMidX())/2,(c.getMidY()+a.getMidY())/2,15+15*(len.length()-1),25);//series of string length
    		window.setColor(Color.BLACK);
    		window.drawString(len,(c.getMidX()+a.getMidX())/2,(c.getMidY()+a.getMidY())/2+wordSize-7);
    	}
    	else
    	{
    		window.setColor(Color.WHITE);
    		window.drawString(len,(c.getMidX()+a.getMidX())/2,(c.getMidY()+a.getMidY())/2+wordSize-7);
    	}
    	if(marked)
    		window.setColor(Color.RED);
    	else if(selected)
    		window.setColor(Color.BLUE);
    	else if(specMark)
    		window.setColor(Color.GREEN);
    	else window.setColor(color);
    	window.drawLine(c.getMidX(),c.getMidY(),a.getMidX(),a.getMidY());
    }

    public void setCells(cell b,cell d)
    {
    	a=b;
    	c=d;
    }

    public void shiftCell(cell b)
    {
    	if(a.equals(b))
    		a=b;
    	else if(c.equals(b))
    		c=b;
    }

    public boolean isLine(cell b,cell d)
    {
    	if(a.equals(b)&&c.equals(d))
    		return true;
    	if(c.equals(b)&&a.equals(d))
    		return true;
    	return false;
    }

    public void setColor(Color colour)
    {
    	color=colour;
    }

    public void reveal()
    {
    	displayG=true;
    }

    public void hide()
    {
    	if(len.length()==0)
    		len="1";
		displayG=false;
    }

    public boolean checkInBox(int x,int y)
    {
    	if(x>(c.getMidX()+a.getMidX())/2&&x<(c.getMidX()+a.getMidX())/2+15+15*(len.length()-1))
    		if(y>(c.getMidY()+a.getMidY())/2&&y<(c.getMidY()+a.getMidY())/2+25)
    			return true;
    	return false;
    }

    public void appendG(char s)
    {
    	len+=s;
    }
    public void deleteLast()
    {
    	if(len.length()!=0)
    		len=len.substring(0,len.length()-1);
    }
    public void switchBoxSelect()
    {
    	boxSelected=!boxSelected;
    }
    public void setBoxSelect(boolean b)
    {
    	boxSelected=b;
    }

    public void switchSelected()
    {
    	selected=!selected;
    }
    public void setSelected(boolean b)
    {
    	selected=b;
    }
    public void setMarked(boolean b)
    {
    	marked=b;
    }
    public void setSpecMark(boolean b)
    {
    	specMark=b;
    }
    public boolean getSpecMark()
    {
    	return specMark;
    }


}