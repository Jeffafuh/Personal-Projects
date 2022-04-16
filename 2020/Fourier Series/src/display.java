/**
 * @(#)display.java
 *
 *
 * @author
 * @version 1.00 2020/4/12
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

public class display extends Canvas implements Runnable,MouseListener,MouseMotionListener,MouseWheelListener,KeyListener{

	private BufferedImage back;
	private coordinatePlane coordPlane;
	private drawingPlane drawPlane;
	private double time;
	private ArrayList<button> buttonList;

    public display() {
    	new Thread(this).start();
    	this.addMouseListener(this);
    	this.addMouseMotionListener(this);
    	this.addMouseWheelListener(this);
    	this.addKeyListener(this);
    	setVisible(true);
    }

    public void paint(Graphics window)
    {
		Graphics2D twoDGraph=(Graphics2D)window;
		if(back==null)
		{
			setUp(getWidth(),getHeight());
			back=(BufferedImage)(createImage(getWidth(),getHeight()));
		}
		Graphics2D graphToBack=back.createGraphics();
		graphToBack.setColor(Color.BLACK);
		graphToBack.fillRect(0,0,getWidth(),getHeight());

		if(drawPlane==null)
		{
			coordPlane.draw(graphToBack,time);
			time+=0.001;
		}
		else
		{
			drawPlane.draw(graphToBack);
		}
		drawButtons(graphToBack);

		twoDGraph.drawImage(back,null,0,0);
    }

    public void keyReleased(KeyEvent e){}
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode()==KeyEvent.VK_ENTER)
			coordPlane.terminateInput();
		if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE)
			coordPlane.backspace();
		else coordPlane.sendInput(e.getKeyChar());
	}
	public void keyTyped(KeyEvent e){}

    public void mouseExited(MouseEvent x){}
    public void mouseEntered(MouseEvent x){}
	public void mousePressed(MouseEvent x){}
	public void mouseClicked(MouseEvent x)
	{
		if(drawPlane!=null)
		{
			for(int i=0;i<buttonList.size();i++)
			{
				if(buttonList.get(i).isClicked(x.getX(),x.getY()))
				{
					if(i==0)
					{
						ArrayList<complexNumber> function=drawPlane.getFunction();
						drawPlane=null;
						setUp(getWidth(),getHeight(),function);
					}
					if(i==1)
					{
						drawPlane.reset();
					}
				}
			}
		}
		else
		{
			for(int i=0;i<buttonList.size();i++)
			{
				if(buttonList.get(i).isClicked(x.getX(),x.getY()))
				{
					if(i==0)
					{
						coordPlane.addVector();
					}
					if(i==1)
					{
						coordPlane.toggleRealGraph();
					}
					if(i==2)
					{
						coordPlane.toggleComplexGraph();
					}
					if(i==3)
					{
						drawPlane=new drawingPlane(getWidth(),getHeight());
						initalizeDrawButtons();
					}
					if(i==4)
					{
						buttonList.get(i).toggleButton();
						coordPlane.toggleAxis();
					}
					if(i==5)
					{
						coordPlane.deleteVector();
					}
				}
			}
			coordPlane.checkClick(x.getX(),x.getY());
		}
	}
	public void mouseMoved(MouseEvent x){}
	public void mouseDragged(MouseEvent x)
	{
		if(drawPlane==null)
		{
			coordPlane.scroll(x.getX(),x.getY());
		}
		else
		{
			drawPlane.sendCoords(x.getX(),x.getY());
		}
	}
	public void mouseReleased(MouseEvent x)
	{
		if(drawPlane==null)
		{
			coordPlane.clearCache();
		}
	}
	public void mouseWheelMoved(MouseWheelEvent x)
	{
		if(coordPlane.checkWithinBoundsofEquationList(x.getX(),x.getY()))
		{
			coordPlane.shiftEquationListOffSet(x.getWheelRotation());
		}
		else
		{
			coordPlane.dilate(x.getWheelRotation());
		}
	}

    public void setUp(int w,int h)
    {
    	time=0;
		coordPlane=new coordinatePlane(w,h);
		initalizeCoordButtons();
    }

    public void setUp(int w,int h,ArrayList<complexNumber> f)
    {
    	time=0;
		coordPlane=new coordinatePlane(w,h,f);
		initalizeCoordButtons();
    }

    public void initalizeCoordButtons()
    {
    	buttonList=new ArrayList<button>();
		buttonList.add(new button(10,10,100,100,"add vector"));
		buttonList.add(new button(10,230,100,100,"real"));
		buttonList.add(new button(10,340,100,100,"imaginary"));
		buttonList.add(new button(10,450,100,100,"draw!"));
		buttonList.add(new button(10,560,100,100,"axis"));
		buttonList.add(new button(10,120,100,100,"delete"));
    }

    public void initalizeDrawButtons()
    {
    	buttonList=new ArrayList<button>();
    	buttonList.add(new button(800,800,100,100,"done"));
		buttonList.add(new button(1000,800,100,100,"reset"));
    }

    public void drawButtons(Graphics window)
    {
    	for(button b:buttonList)
    	{
    		b.draw(window);
    	}
    }

    public void update(Graphics window)
    {
    	paint(window);
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
    	}
    	catch(Exception e)
    	{

    	}
    }


}