/**
 * @(#)button.java
 *
 *
 * @author
 * @version 1.00 2020/4/14
 */
import static java.lang.System.*;
import java.util.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;

public class button extends Object{

	private int x;
	private int y;
	private int height;
	private int width;
	private String text;
	private boolean toggle;
	public final Color LightGray=new Color(204,204,204);
	public final Color Gray=new Color(187,187,187);
	public final Color DarkGray=new Color(153,153,153);

    public button() {
		out.println("oopsie");
    }

    public button(int startX,int startY,int w,int h,String word)
    {
    	x=startX;
    	y=startY;
    	height=h;
    	width=w;
    	text=word;
    	toggle=false;
    }

    public void draw(Graphics window)
    {
    	window.setColor(LightGray);
    	window.fillRect(x,y,width,height);
    	int i=5;
    		window.setColor(Color.WHITE);
    		window.fillRect(x,y,i,height);
    		window.fillRect(x,y,width,i);
    		window.setColor(DarkGray);
    		window.fillRect(x+width-i,y,i,height);
    		window.fillRect(x,y+height-i,width,i);

    	window.setFont(new Font("TimesRoman", Font.BOLD, 19));
    	window.setColor(Color.BLACK);
    	window.drawString(text,x+8,y+height/2+5);
    }

    public boolean isClicked(int newX,int newY)
    {
    	if(newX>x&&newX<x+width)
    		if(newY>y&&newY<y+height)
    			return true;
    	return false;
    }

    public void toggleButton()
    {
    	toggle=!toggle;
    }


}