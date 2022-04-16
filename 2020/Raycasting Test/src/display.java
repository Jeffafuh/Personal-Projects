/**
 * @(#)display.java
 *
 *
 * @author
 * @version 1.00 2020/5/2
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

public class display extends Canvas implements Runnable,KeyListener{

	private BufferedImage back;
	private grid g;
	private boolean[] keys;
	private int moveTickCount;

    public display(int w,int h) {
    	setSize(w,h);
    	new Thread(this).start();
    	this.addKeyListener(this);
    	setVisible(true);
    }

    public void paint(Graphics window)
    {
		Graphics2D twoDGraph = (Graphics2D)window;
		if(back == null)
		{
			initalize();
			back = (BufferedImage)(createImage(getWidth() , getHeight()));
		}
		Graphics2D graphToBack = back.createGraphics();
		graphToBack.setColor(Color.GRAY);
		graphToBack.fillRect(0,0,getWidth(),getHeight());

		if(moveTickCount==5)
		{
			sendInputs();
			moveTickCount=0;
		}
		moveTickCount++;
		g.draw(graphToBack);

		twoDGraph.drawImage(back,null,0,0);
    }

    public void keyReleased(KeyEvent e)
    {
    	char c=e.getKeyChar();
		if(c=='w')
			keys[0]=false;
		if(c=='a')
			keys[1]=false;
		if(c=='s')
			keys[2]=false;
		if(c=='d')
			keys[3]=false;
    }
	public void keyPressed(KeyEvent e)
	{
		char c=e.getKeyChar();
		if(c=='w')
			keys[0]=true;
		if(c=='a')
			keys[1]=true;
		if(c=='s')
			keys[2]=true;
		if(c=='d')
			keys[3]=true;
		if(e.getKeyCode()==KeyEvent.VK_SPACE)
		{
			g.print();
		}
	}
	public void keyTyped(KeyEvent e){}

	public void sendInputs()
	{
		if(keys[0])
			g.move('w');
		if(keys[1])
			g.move('a');
		if(keys[2])
			g.move('s');
		if(keys[3])
			g.move('d');
	}

    public void update(Graphics window)
    {
    	paint(window);
    }

    public void initalize()
    {
    	g=new grid();
    	keys= new boolean[4];
    	moveTickCount=0;
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