/**
 * @(#)snake.java
 *
 *
 * @author
 * @version 1.00 2019/9/5
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

public class snake extends entity{
	private final int[] loop={1,0,4,0,2,0,3,0};//or a 1-2-2-1 pattern idk
	private int pointer;
    public snake(int s) {
    	pointer=0;
    	size=s;
    }
    public void draw(Graphics window,cell c)
    {
    	window.setColor(Color.GRAY);
    	window.fillOval(getCol(c),getRow(c),size,size);
    }
    public int[] mvmt(String s)
    {
    	int[] out=new int[2];
		switch(loop[pointer])
		{
			case 0: break;
			case 1: out[1]=1; break;
			case 2: out[1]=-1; break;
			case 3: out[0]=1; break;
			case 4: out[0]=-1;
		}
		if(pointer==loop.length-1)
			pointer=0;
		else pointer++;
		return out;
    }

    public int type()
    {
    	return 1;
    }


}