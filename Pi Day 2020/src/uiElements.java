import java.awt.*;
import static java.lang.System.*;

public class uiElements extends Object{
	private slider magicSlider;
	private slider dtSlider;
	private slider gravSlider;
	private int x;
	private int y;
	private int width;
	private int height;
	private int deltaX;
	private int deltaY;
	private String select;

    public uiElements() {
    	out.println("whoops");
    }

    public uiElements(int x1,int y1,int w,int h)
    {
    	x=x1;
    	y=y1;
    	width=w;
    	height=h;
    	magicSlider=new slider(50,500,700);
    	dtSlider=new slider(50,500,800);
    	dtSlider.setBox(110);
    	gravSlider=new slider(50,500,900);
    	gravSlider.setBox(280);
    	select="";
    }

    public void draw(Graphics2D window)
    {
    	window.setColor(Color.GRAY);
    	window.fillRect(x,y,width,height);
    	magicSlider.draw(window);
    	dtSlider.draw(window);
    	gravSlider.draw(window);
    	window.setFont(new Font("TimesRoman", Font.BOLD, 19));
    	window.setColor(Color.BLACK);
    	window.drawString("Num Sticks: " + getMagic(), 30, 670);
    	window.drawString("Stick Length: " + getDT(), 30, 770);
    	window.drawString("Strip Width: " + getGrav(), 30, 870);
    }

    public boolean checkWithinBounds(int x1,int y1)
    {
		if(x1>x&&x1<x+width)
			if(y1>y&&y1<y+width)
			{
				if(magicSlider.checkWithinBounds(x1,y1))
					select="magicslide";
				else if(dtSlider.checkWithinBounds(x1,y1))
					select="dtslide";
				else if(gravSlider.checkWithinBounds(x1, y1))
					select = "gravslide";
				return true;
			}
		return false;
    }

    public void scroll(int x,int y)
    {
		if(deltaX!=0||deltaY!=0)
    	{
			deltaX=deltaX-x;
			deltaY=deltaY-y;
			if(select.equals("magicslide"))
				magicSlider.scroll(deltaX,deltaY);
			else if(select.equals("dtslide"))
				dtSlider.scroll(deltaX,deltaY);
			else if(select.equals("gravslide"))
				gravSlider.scroll(deltaX, deltaY);
    	}
    	deltaX=x;
    	deltaY=y;
    }

    public void clearCache()
    {
		deltaX=0;
    	deltaY=0;
    	select="";
    }

    public int getMagic()
    {
    	return 1 + (int)((Math.pow(2, 11))*(magicSlider.getConstant()-magicSlider.getStart())/(magicSlider.getStop()-magicSlider.getStart()));
    }

    public int getDT()
    {
    	return 1 + (int)(5*(dtSlider.getConstant()-dtSlider.getStart())/(dtSlider.getStop()-dtSlider.getStart()));
    }
    
    public int getGrav()
    {
    	return 1 + (int)(5*(gravSlider.getConstant()-gravSlider.getStart())/(gravSlider.getStop()-gravSlider.getStart()));
    }


}