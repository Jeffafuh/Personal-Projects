/**
 * @(#)display.java
 *
 *
 * @author
 * @version 1.00 2020/3/22
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

public class display extends Canvas implements Runnable,MouseListener,MouseMotionListener,MouseWheelListener{

	private BufferedImage back;
	private coordinatePlane coordPlane;
	private swingThing swingy;
	private uiElements ui;
	private double theta;
	private double thetaDot;
	private double magicConstant;
	private double deltaT;

    public display()
    {
    	setBackground(Color.BLACK);
    	this.addMouseListener(this);
    	this.addMouseMotionListener(this);
    	this.addMouseWheelListener(this);
    	new Thread(this).start();
    	setVisible(true);
    	theta=Math.PI/2;
    	thetaDot=0;
    	magicConstant=0;
    	deltaT=0.009;
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

		coordPlane.draw(graphToBack);
		magicConstant=ui.getMagic();
		deltaT=ui.getDT();
		double[] coord=coordPlane.calcNewDeltas(graphToBack,theta,thetaDot,magicConstant,deltaT);
		theta=coord[0];
		thetaDot=coord[1];
		swingy.updateThetas(theta,thetaDot);
		swingy.draw(graphToBack);
		ui.draw(graphToBack);


		twoDGraph.drawImage(back,null,0,0);
    }

    public void update(Graphics window)
    {
    	paint(window);
    }

    private void setUp(int w, int h)
    {
		coordPlane=new coordinatePlane(w,h);
		swingy=new swingThing(50,50,300,300);
		ui=new uiElements(50,600,600,300);
    }

    public void mouseExited(MouseEvent x){}
    public void mouseEntered(MouseEvent x){}
	public void mousePressed(MouseEvent x){}
	public void mouseClicked(MouseEvent x){}
	public void mouseMoved(MouseEvent x){}
	public void mouseDragged(MouseEvent x)
	{
		if(swingy.checkWithinBounds(x.getX(),x.getY()))
		{
			swingy.moveBall(x.getX(),x.getY());
		}
		else if(ui.checkWithinBounds(x.getX(),x.getY()))
			ui.scroll(x.getX(),x.getY());
		else
			coordPlane.scroll(x.getX(),x.getY());
	}
	public void mouseReleased(MouseEvent x)
	{
		coordPlane.clearCache();
		swingy.clearCache();
		ui.clearCache();
	}
	public void mouseWheelMoved(MouseWheelEvent x)
	{
		coordPlane.dilate(x.getWheelRotation());
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