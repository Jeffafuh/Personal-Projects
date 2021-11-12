/**
 * @(#)labyrinth.java
 *
 *
 * @author
 * @version 1.00 2019/8/23
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.*;
import static java.lang.System.*;
public class labyrinth extends Canvas implements KeyListener, Runnable{


	private boolean[] keys;
	private boolean first;
	private BufferedImage back;
	private maze group;
	private static final int size= 30;//40 is a good size
	private player Player;
	private int tick;

    public labyrinth() {
		keys=new boolean[4];
		first=true;
		setBackground(Color.black);
		this.addKeyListener(this);
		new Thread(this).start();
		setVisible(true);
		tick=0;
    }

    public void paint(Graphics window)
    {
    	if(first)
    	{
    		Player=new player(size);
    		group=new maze(getWidth(),getHeight(),size,Player);
    		first=!first;
    		//group.get(12).addThing(new snake(size));
    	}
    	Graphics2D twoDGraph = (Graphics2D)window;
		if(back==null)
		   back = (BufferedImage)(createImage(getWidth(),getHeight()));
		Graphics graphToBack = back.createGraphics();
		boolean dir=false;
		if(tick==40)//40 is a good actual delay
		{
			if(keys[0]&&!dir)
			{
				group.move("LEFT");
				tick=0;
				dir=true;
			}
			if(keys[1]&&!dir)
			{
				group.move("RIGHT");
				tick=0;
				dir=true;
			}
			if(keys[2]&&!dir)
			{
				group.move("UP");
				tick=0;
				dir=true;
			}
			if(keys[3]&&!dir)
			{
				group.move("DOWN");
				tick=0;
				dir=true;
			}
		}
		else tick++;


		group.draw(graphToBack);

    	twoDGraph.drawImage(back, null, 0, 0);
    }


    public void update(Graphics window)
    {
    	paint(window);
    }

    public void keyPressed(KeyEvent x)
    {
    	if(x.getKeyCode()==KeyEvent.VK_LEFT)
    		keys[0]=true;
    	if(x.getKeyCode()==KeyEvent.VK_RIGHT)
    		keys[1]=true;
    	if(x.getKeyCode()==KeyEvent.VK_UP)
    		keys[2]=true;
    	if(x.getKeyCode()==KeyEvent.VK_DOWN)
    		keys[3]=true;
    	repaint();
    }
    public void keyReleased(KeyEvent x)
    {
    	if(x.getKeyCode()==KeyEvent.VK_LEFT)
    		keys[0]=false;
    	if(x.getKeyCode()==KeyEvent.VK_RIGHT)
    		keys[1]=false;
    	if(x.getKeyCode()==KeyEvent.VK_UP)
    		keys[2]=false;
    	if(x.getKeyCode()==KeyEvent.VK_DOWN)
    		keys[3]=false;
    	repaint();
    }
    public void keyTyped(KeyEvent x)
    {
    	// :)
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