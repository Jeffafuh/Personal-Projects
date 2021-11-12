import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class display extends Canvas implements Runnable, MouseListener, MouseMotionListener{
	
	private BufferedImage back;
	private coordinatePlane coordPlane;
	private uiElements ui;
	private ArrayList<button> buttonList;
	private boolean drawtime;
	private double piCount;
	private button piDisplay;
	
	public display(int w, int h)
	{
		setSize(w,h);
		new Thread(this).start();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		setVisible(true);
		initButtons();
		drawtime = false;
		piCount = -1.0;
	}
	
	public void paint(Graphics window)
	{
		Graphics2D twoDGraph = (Graphics2D)window;
		if(back==null)
		{
			back=(BufferedImage)(createImage(getWidth(),getHeight()));
			coordPlane = new coordinatePlane(getWidth(), getHeight(), 1, 1, 3);
			ui = new uiElements(20,650,500,300);
		}
		Graphics2D graphToBack=back.createGraphics();
		graphToBack.setColor(Color.GRAY);
		graphToBack.fillRect(0, 0, getWidth(), getHeight());
		
		if(!drawtime)
		{
			coordPlane.draw(graphToBack);
			ui.draw(graphToBack);
			drawButtons(graphToBack);
			if(piCount != -1)
			{
				piDisplay.draw(graphToBack);
			}
		}
		else
		{
			int aniCount = 0;
			try{
				aniCount = coordPlane.animate(graphToBack);
			}
			catch(Exception e)
			{
			}
			
			if(aniCount == 0)
			{
				drawtime = false;
				piCount = (2.0*ui.getDT()*ui.getMagic())/((1.0*ui.getGrav()*coordPlane.getCrossCount()));
				piDisplay = new button(20,575,375,50,"Congratulations! Pi = " + piCount);
			}
		}
		
		twoDGraph.drawImage(back, null, 0, 0);
	}
	
	/*public void drawPiCount(Graphics window)
	{
		window.setFont(new Font("TimesRoman", Font.BOLD, 19));
    	window.setColor(Color.GRAY);
    	window.fillRect(20, 575, 300, 50);
    	window.setColor(Color.BLACK);
    	window.drawString("Congratulations! Pi = " + String.format("%,.2f", piCount), 22, 600);
	}
	*/
	
	public void mouseMoved(MouseEvent x){}
	public void mouseDragged(MouseEvent x)
	{
		if(ui.checkWithinBounds(x.getX(),x.getY()))
			ui.scroll(x.getX(),x.getY());
	}
	public void mouseReleased(MouseEvent x)
	{
		ui.clearCache();
	}
	public void mouseExited(MouseEvent x){}
    public void mouseEntered(MouseEvent x){}
	public void mousePressed(MouseEvent x){}
	public void mouseClicked(MouseEvent x)
	{
		for(int i=0;i<buttonList.size();i++)
		{
			if(buttonList.get(i).isClicked(x.getX(),x.getY()))
			{
				if(i==0)
				{
					coordPlane = new coordinatePlane(getWidth(), getHeight(), ui.getMagic(), ui.getDT(), ui.getGrav());
					if(buttonList.get(1).getToggle())
					{
						drawtime = true;
					}
					else
					{
						coordPlane.skipAnimation();
						piCount = (2.0*ui.getDT()*ui.getMagic())/((1.0*ui.getGrav()*coordPlane.getCrossCount()));
						piDisplay = new button(20,575,375,50,"Congratulations! Pi = " + piCount);
					}
				}
				else if(i==1)
				{
					buttonList.get(i).toggleButton();
				}
				else if(i==2)
				{
					double piEst = 0.0;
					int n = 1000;
					for(int j = 0; j < n; j++)
					{
						coordPlane = new coordinatePlane(getWidth(), getHeight(), ui.getMagic(), ui.getDT(), ui.getGrav());
						coordPlane.skipAnimation();
						piEst += (2.0*ui.getDT()*ui.getMagic())/((1.0*ui.getGrav()*coordPlane.getCrossCount()));
					}
					piEst /= n;
					double percError = 100*(Math.abs(piEst - Math.PI)/Math.PI);
					System.out.println("After "+ n +" simulations dropping n = " + ui.getMagic() + " sticks each time, (l = " + ui.getDT() + ", t = " + ui.getGrav() + "), Pi = " + piEst + "!");
					System.out.println("That's " + n*ui.getMagic() + " sticks dropped total with (only) a "+ String.format("%,.4f", percError) + "% error! :)\n");
				}
			}
		}
	}
	
	
	public void initButtons()
	{
		buttonList = new ArrayList<button>();
		buttonList.add(new button(10,10,100,100,"Generate!"));
		buttonList.add(new button(10,120,100,100, "Animation"));
		buttonList.add(new button(10,230,100,100, "     ???"));
	}
	
	public void drawButtons(Graphics window)
	{
		for(int i = 0; i < buttonList.size(); i++)
		{
			buttonList.get(i).draw(window);
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
				Thread.currentThread().sleep(5);;
				repaint();
			}
		}
		catch(Exception e)
		{
			
		}
	}

}
