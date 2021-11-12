/**
 * @(#)block.java
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


public class block extends cell{

    public block() {
    	super();
    }
    public block(int row,int col,int wid,int adjRow,int adjCol)
    {
    	super(row,col,wid,adjRow,adjCol);
    }
    public void setAlign(int a)
    {
    	align=a;
    }

    public void draw(Graphics window)
    {
    	int k=(w-w/8)/2;
    	window.setColor(Color.BLACK);
    	window.fillRect(adjc+c*w,adjr+r*w,w,w);
    	if(!isHidden)
    	{
    		if(align==8)
    		{
    			window.setColor(Color.BLACK);
    			boolean color=true;
  	  			for(int y=0; y<4;y++)
    			{
    				color=!color;
    				for(int x=0;x<4;x++)
    				{
    					if(color)
    						window.setColor(Color.BLACK);
    					else
    						window.setColor(colour);
    					color=!color;
    					window.fillRect(adjc+c*w+x*(w/4),adjr+r*w+y*(w/4),w/4+1,w/4+1);
    				}
    			}
    		}
    		else
    		{
    			window.setColor(Color.GREEN);
    			window.fillOval(adjc+c*w+k,adjr+r*w+k,w/8,w/8);
    		}
    		//window.drawRect(adjc+(c*w),adjr+(r*w),w,w);
    	}
    	for(entity e:thing)
    		e.draw(window,this);
    }


}