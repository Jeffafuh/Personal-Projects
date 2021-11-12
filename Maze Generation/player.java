/**
 * @(#)player.java
 *
 *
 * @author
 * @version 1.00 2019/8/29
 */
import java.util.*;
import static java.lang.System.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.*;
import static java.lang.System.*;

public class player extends entity{
    public player(int s) {
    	size=s;
    }

    public void draw(Graphics window,cell c)
    {
    	window.setColor(Color.RED);
    	window.fillOval(getCol(c),getRow(c),size,size);
    }

   	public int[] mvmt(String s)
   	{
   		int[] out=new int[2];
   		if(s.equals("LEFT"))
    	{
    		out[0]=-1;
    	}
    	if(s.equals("RIGHT"))
    	{
    		out[0]=1;
    	}
    	if(s.equals("UP"))
    	{
    		out[1]=-1;
    	}
   		if(s.equals("DOWN"))
    	{
    		out[1]=1;
    	}
    	return out;
   	}

   	public int type()
    {
    	return 0;
    }


}