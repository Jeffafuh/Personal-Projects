/**
 * @(#)path.java
 *
 *
 * @author
 * @version 1.00 2019/9/12
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.*;
import static java.lang.System.*;


public class path extends cell{

    public path() {
    	super();
    	align=8;
    }

    public path(int row,int col,int wid,int adjRow,int adjCol)
    {
    	super(row,col,wid,adjRow,adjCol);
    	align=8;
    }

    public void draw(Graphics window)
    {
    	window.setColor(Color.BLACK);
    	window.fillRect(adjc+c*w,adjr+r*w,w,w);
    	boolean color=true;
    	if(!isHidden)
    	for(int y=0; y<4;y++)
    	{
    		color=!color;
    		for(int x=0;x<4;x++)
    		{
    			if(color)
    				window.setColor(Color.BLACK);
    			else
    				window.setColor(Color.GRAY);
    			color=!color;
    			window.fillRect(adjc+c*w+x*(w/4),adjr+r*w+y*(w/4),w/4,w/4);
    		}
    	}
    	for(entity e:thing)
    		e.draw(window,this);
    }


}