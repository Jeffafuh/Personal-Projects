/**
 * @(#)display.java
 *
 *
 * @author
 * @version 1.00 2020/1/27
 */
import java.util.*;
import static java.lang.System.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;

public class display extends Canvas implements KeyListener,MouseMotionListener,MouseListener,MouseWheelListener,Runnable{

	private BufferedImage back;
	private place p;


    public display() {
    	setBackground(Color.LIGHT_GRAY);
    	this.addMouseMotionListener(this);
    	this.addMouseListener(this);
    	this.addMouseWheelListener(this);
    	this.addKeyListener(this);
		new Thread(this).start();
		setVisible(true);
		p=new place();
    }

    public void paint(Graphics window)
    {
    	Graphics2D twoDGraph = (Graphics2D)window;
    	if(back==null)
		   back = (BufferedImage)(createImage(getWidth(),getHeight()));
		Graphics graphToBack = back.createGraphics();
		graphToBack.setColor(new Color(0,0,0));
		graphToBack.fillRect(0,0,getWidth(),getHeight());

		p.draw(graphToBack);
		p.checkPathFinding();
		if(p.isPathFinding())
		{
			try{
				Thread.sleep(600);
				p.doPathFinding();
			}
			catch(Exception e)
			{
			}
		}

		twoDGraph.drawImage(back, null, 0, 0);
		if(p.getWidth()==0)
			p.setDimensions(getWidth(),getHeight());
    }

    public void mouseClicked(MouseEvent x)
    {
    	p.clickButton(x.getX(),x.getY());
    	p.selectLine(x.getX(),x.getY());
		p.selectCell(x.getX(),x.getY());
    }
    public void mouseExited(MouseEvent x){}
    public void mouseEntered(MouseEvent x){}
    public void mouseReleased(MouseEvent x)
    {
		p.clearCache();
    }
	public void mousePressed(MouseEvent x){}
	public void mouseMoved(MouseEvent x){}
	public void mouseDragged(MouseEvent x)
	{
		p.scroll(x.getX(),x.getY());
	}
	public void mouseWheelMoved(MouseWheelEvent x)
	{
		p.dilate(2*x.getWheelRotation());
	}

	public void keyReleased(KeyEvent e){}
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode()==KeyEvent.VK_ENTER)
			p.terminateInput();
		if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE)
			p.backspace();
		p.sendInput(e.getKeyChar());
	}
	public void keyTyped(KeyEvent e){}

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
   	   }catch(Exception e)
   	   {
   	   }
    }


}