/**
 * @(#)wall.java
 *
 *
 * @author
 * @version 1.00 2019/9/19
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.*;
import static java.lang.System.*;

public class wall extends cell{

    public wall() {
    	super();
    	align=0;
    }

    public wall(int row,int col,int wid,int adjRow,int adjCol)
    {
    	super(row,col,wid,adjRow,adjCol);
    	align=0;
    }

    public wall(int row,int col,int wid,int adjRow,int adjCol,int a)
    {
    	super(row,col,wid,adjRow,adjCol);
    	align=a;
    }


    public void draw(Graphics window)
    {
    	window.setColor(Color.BLACK);
    	window.fillRect(adjc+c*w,adjr+r*w,w,w);
    	//0=vertical,1=horizontal,2=TL,3=TR,4=BL,5=BR,6=opening
    	window.setColor(Color.ORANGE);
    	if(!isHidden){
    	if(align==0)
    	{
    		window.fillRect(adjc+c*w+(3*w)/9,adjr+r*w,w/9-1,w);
    		window.fillRect(adjc+c*w+(5*w)/9+1,adjr+r*w,w/9-1,w);
    	}
    	if(align==1)
    	{
    		window.fillRect(adjc+c*w,adjr+r*w+(3*w)/9,w,w/9-1);
    		window.fillRect(adjc+c*w,adjr+r*w+(5*w)/9+1,w,w/9-1);
    	}
    	if(align==2)
    	{
    		//horizontal
    		window.fillRect(adjc+c*w+(3*w)/9,adjr+r*w+(3*w)/9,(6*w)/9+1,w/9);
    		window.fillRect(adjc+c*w+(3*w)/9,adjr+r*w+(5*w)/9,(6*w)/9+1,w/9);
    		//vertical
    		window.fillRect(adjc+c*w+(3*w)/9,adjr+r*w+(4*w)/9,w/9,(5*w)/9+1);
    		window.fillRect(adjc+c*w+(5*w)/9,adjr+r*w+(4*w)/9,w/9,(5*w)/9+1);
			//fix that middle part thingy
			window.setColor(Color.BLACK);
			window.fillRect(adjc+c*w+(4*w)/9-1,adjr+r*w+(4*w)/9-1,(5*w)/9+2,w/9+2);//hor
			window.fillRect(adjc+c*w+(4*w)/9-1,adjr+r*w+(4*w)/9,w/9+2,(5*w)/9+1);//vert
    	}
    	if(align==3)
    	{
    		//horizontal
    		window.fillRect(adjc+c*w,adjr+r*w+(5*w)/9,(5*w)/9+1,w/9);
    		window.fillRect(adjc+c*w,adjr+r*w+(3*w)/9,(5*w)/9+1,w/9);
    		//vertical
    		window.fillRect(adjc+c*w+(3*w)/9,adjr+r*w+(3*w)/9,w/9,(6*w)/9+1);
    		window.fillRect(adjc+c*w+(5*w)/9,adjr+r*w+(3*w)/9,w/9,(6*w)/9+1);
			//fix that middle part thingy
			window.setColor(Color.BLACK);
			window.fillRect(adjc+c*w,adjr+r*w+(4*w)/9-1,(4*w)/9,w/9+2);//hor
			window.fillRect(adjc+c*w+(4*w)/9-1,adjr+r*w+(4*w)/9-1,w/9+2,(5*w)/9+2);//vert
    	}
    	if(align==4)
    	{
    		//horizontal
    		window.fillRect(adjc+c*w+(3*w)/9,adjr+r*w+(3*w)/9,(6*w)/9+1,w/9);
    		window.fillRect(adjc+c*w+(3*w)/9,adjr+r*w+(5*w)/9,(6*w)/9+1,w/9);
    		//vertical
    		window.fillRect(adjc+c*w+(3*w)/9,adjr+r*w,w/9,(6*w)/9-1);
    		window.fillRect(adjc+c*w+(5*w)/9,adjr+r*w,w/9,(6*w)/9-1);
			//fix that middle part thingy
			window.setColor(Color.BLACK);
			window.fillRect(adjc+c*w+(4*w)/9-1,adjr+r*w+(4*w)/9-1,(5*w)/9+2,w/9+2);//hor
			window.fillRect(adjc+c*w+(4*w)/9-1,adjr+r*w,w/9+2,(5*w)/9);//vert
    	}
    	if(align==5)
    	{
    		//horizontal
    		window.fillRect(adjc+c*w,adjr+r*w+(3*w)/9,(6*w)/9-1,w/9);
    		window.fillRect(adjc+c*w,adjr+r*w+(5*w)/9,(6*w)/9-1,w/9);
    		//vertical
    		window.fillRect(adjc+c*w+(3*w)/9,adjr+r*w,w/9,(6*w)/9-1);
    		window.fillRect(adjc+c*w+(5*w)/9,adjr+r*w,w/9,(6*w)/9-1);
			//fix that middle part thingy
			window.setColor(Color.BLACK);
			window.fillRect(adjc+c*w,adjr+r*w+(4*w)/9-1,(5*w)/9,w/9+2);//hor
			window.fillRect(adjc+c*w+(4*w)/9-1,adjr+r*w,w/9+2,(5*w)/9+1);//vert
    	}
    	if(align==6)
    	{
    		window.fillRect(adjc+c*w+(3*w)/9,adjr+r*w,w/9,w);
    		window.fillRect(adjc+c*w+(5*w)/9,adjr+r*w,w/9,w);

    		window.fillRect(adjc+c*w,adjr+r*w+(3*w)/9,w,w/9);
    		window.fillRect(adjc+c*w,adjr+r*w+(5*w)/9,w,w/9);

			window.setColor(Color.BLACK);
    		window.fillRect(adjc+c*w+(4*w)/9-1,adjr+r*w,w/9+2,w);
    		window.fillRect(adjc+c*w,adjr+r*w+(4*w)/9-1,w,w/9+2);
    	}
    	}
    	for(entity e:thing)
    		e.draw(window,this);

    }


}