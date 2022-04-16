/**
 * @(#)faceButton.java
 *
 *
 * @author
 * @version 1.00 2019/12/2
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.*;
import static java.lang.System.*;


public class faceButton extends cell{

    public faceButton() {
    }

    public faceButton(int x1,int y1,int size) {
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
    	int r=7;
    	window.setColor(Color.YELLOW);
    	window.fillOval(getTrueX()+r,getTrueY()+r,getSize()-2*r-1,getSize()-2*r-1);
    	window.setColor(Color.BLACK);
    	window.drawOval(getTrueX()+r,getTrueY()+r,getSize()-2*r-1,getSize()-2*r-1);
    	r=(getSize()/2)-(2*r);
    	if(!getHidden())
    	{
    		window.drawArc(getTrueX()+(getSize()/2)-(r/2+r/4)-1,getTrueY()+(getSize()/2)-(1*r/8),3*r/2,(r/2+r/4),0,-180);
    		window.fillRect(getTrueX()+(getSize()/2)-(2*r/3),getTrueY()+(getSize()/2)-(5*r/8),r/3,r/3);
    		window.fillRect(getTrueX()+(getSize()/2)+(r/3),getTrueY()+(getSize()/2)-(5*r/8),r/3,r/3);
    	}
    	else
    	{
    		window.drawArc(getTrueX()+(getSize()/2)-(r/2+r/4)-1,getTrueY()+(getSize()/2)+(1*r/8),3*r/2,(r/2+r/4),0,180);
    		//window.drawLine();

    	}
    }


}