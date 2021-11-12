/**
 * @(#)flagButton.java
 *
 *
 * @author
 * @version 1.00 2019/11/18
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.*;
import static java.lang.System.*;

public class flagButton extends cell{

    public flagButton() {
    }

    public flagButton(int x1,int y1,int size) {
    	super(x1,y1,size);

    }


    public void draw(Graphics window)
    {
    	window.setColor(LightGray);
    	window.fillRect(getTrueX(),getTrueY(),getSize(),getSize());
    	int i=3;
    		window.setColor(Color.WHITE);
    		window.fillRect(getTrueX(),getTrueY(),i,getSize());
    		window.fillRect(getTrueX(),getTrueY(),getSize(),i);

    		window.setColor(DarkGray);
    		window.fillRect(getTrueX()+getSize()-i,getTrueY(),i,getSize());
    		window.fillRect(getTrueX(),getTrueY()+getSize()-i,getSize(),i);
    	//if it's NOT hidden, then it's on flag boi mode
    	if(getHidden())
    	{
			window.setColor(Color.RED);
			window.fillRect(getTrueX(),getTrueY(),getSize(),getSize());
    	}
    	else
    	{
			window.setColor(Color.BLUE);
			window.fillRect(getTrueX(),getTrueY(),getSize(),getSize());
    	}
    }
}