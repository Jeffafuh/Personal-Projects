/**
 * @(#)StartScreen.java
 *
 *
 * @author
 * @version 1.00 2019/11/13
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.*;
import static java.lang.System.*;

public class StartScreen extends Object{
	private int width;
	private int height;
	private int[] states;
	private boolean begin;

	public StartScreen() {
    	width=0;
    	height=0;
    	begin=false;
    }

    public StartScreen(int w, int h) {
    	width=w;
    	height=h;
    }

    public void setDim(int w, int h)
    {
    	width=w;
    	height=h;
    }

    public void draw(Graphics window)
    {
		window.setColor(Color.LIGHT_GRAY);
		window.fillRect(0,0,width,height);
		window.setColor(Color.BLACK);
		window.drawString("Minesweeper",10,10);
    }



    public boolean getBegin()
    {
    	return begin;
    }


}