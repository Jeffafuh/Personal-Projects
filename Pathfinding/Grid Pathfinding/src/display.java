/**
 * @(#)display.java
 *
 *
 * @author
 * @version 1.00 2019/12/9
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

public class display extends Canvas implements MouseMotionListener,MouseListener,Runnable{

	private grid g;
	private boolean first;
	private BufferedImage back;
	private int frames;
	private String generating;

    public display() {
    	generating="no";
    	frames=5;
    	first=true;
    	setBackground(Color.LIGHT_GRAY);
    	this.addMouseMotionListener(this);
    	this.addMouseListener(this);
		new Thread(this).start();
		setVisible(true);
    }

    public void paint(Graphics window)
    {
    	Graphics2D twoDGraph = (Graphics2D)window;
    	if(back==null)
		   back = (BufferedImage)(createImage(getWidth(),getHeight()));
		Graphics graphToBack = back.createGraphics();

		graphToBack.fillRect(0,0,getWidth(),getHeight());

		if(first)
		{
			g=new grid(1100,1100,-10,-10);
			first=!first;
		}
		g.draw(graphToBack);
		if(generating.equals("greed"))
		{
			try{
			Thread.sleep(300);
			generating=g.Greedy();
			}
			catch(Exception e)
			{
			}
		}
		else if(generating.equals("flood"))
		{
			try{
			Thread.sleep(200);
			generating=g.find();
			}
			catch(Exception e)
			{
			}
		}

    	twoDGraph.drawImage(back, null, 0, 0);
    }

    public void doTheCoolThing()
    {
    	try{

    	}
    	catch(Exception e)
    	{

    	}
    }

    public void update(Graphics window)
    {
    	paint(window);
    }

	public void mouseClicked(MouseEvent x){}
    public void mouseExited(MouseEvent x){}
    public void mouseEntered(MouseEvent x){}
    public void mouseReleased(MouseEvent x)
    {
    	g.clearCache();
    }
	public void mousePressed(MouseEvent x)
	{
		String s="";
		if(SwingUtilities.isLeftMouseButton(x))
			s="left";
		else if(SwingUtilities.isRightMouseButton(x))
			s="right";
		generating=g.selectCell(x.getX(),x.getY(),s);
	}
	public void mouseMoved(MouseEvent x){}
	public void mouseDragged(MouseEvent x)
	{
		String s="";
		if(SwingUtilities.isMiddleMouseButton(x))
		{
			g.scroll(x.getX(),x.getY());
		}
		else{
		if(SwingUtilities.isLeftMouseButton(x))
			s="left";
		else if(SwingUtilities.isRightMouseButton(x))
			s="right";
		g.selectCell(x.getX(),x.getY(),s);
		}
	}

	public void run()
    {
    	try
  	 	{
   		while(true)
   		{
   		   Thread.currentThread().sleep(frames);
            repaint();
         }
   	   }catch(Exception e)
   	   {
   	   }
    }

}