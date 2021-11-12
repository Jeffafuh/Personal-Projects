/**
 * @(#)Grid.java
 *
 *
 * @author
 * @version 1.00 2019/11/13
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.KeyEvent;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.*;
import javax.swing.*;
import static java.lang.System.*;

public class Menu extends Canvas implements MouseListener,Runnable{

	private int[] coord;
	private boolean first;
	private int size;
	private StartScreen start;
	private Grid grid;
	private BufferedImage back;

    public Menu() {
    	coord=new int[2];
    	setBackground(Color.LIGHT_GRAY);
    	this.addMouseListener(this);
    	this.addMouseListener(this);
		new Thread(this).start();
		setVisible(true);
		start=new StartScreen();
		first=true;
    }

    public void paint(Graphics window)
    {
    	Graphics2D twoDGraph = (Graphics2D)window;
    	if(back==null)
		   back = (BufferedImage)(createImage(getWidth(),getHeight()));
		Graphics graphToBack = back.createGraphics();

		if(start.getBegin())
		{
			start.setDim(getWidth(),getHeight());
			start.draw(graphToBack);
		}
		else
		{
			if(first)
			{
				grid=new Grid(700,800,50,50);
				first=!first;
				grid.setMines(40);
			}
			grid.draw(graphToBack);
		}




    	twoDGraph.drawImage(back, null, 0, 0);

    }

    public void update(Graphics window)
    {
    	paint(window);
    }

    public void mouseClicked(MouseEvent x){}
    public void mouseExited(MouseEvent x){}
    public void mouseEntered(MouseEvent x){}
    public void mouseReleased(MouseEvent x){}

    public void mousePressed(MouseEvent x)
    {
    	if(SwingUtilities.isLeftMouseButton(x))
    		grid.reveal(x.getX(),x.getY());
    	else
    	{
			grid.flagCell(x.getX(),x.getY());
    	}
    }

    public void run()
    {
    	try
  	 	{
   		while(true)
   		{
   		   Thread.currentThread().sleep(5);
            repaint();
         }
   	   }catch(Exception e)
   	   {
   	   }
    }


}