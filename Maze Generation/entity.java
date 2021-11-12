/**
 * @(#)entity.java
 *
 *
 * @author
 * @version 1.00 2019/8/23
 */
import java.util.*;
import static java.lang.System.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.*;
import static java.lang.System.*;

public abstract class entity {
	int size;
	boolean isHidden;

    public entity() {
    }
    public abstract int[] mvmt(String s);

    public abstract int type();

    public abstract void draw(Graphics window,cell c);

    public int getCol(cell c)
    {
    	return (c.getAdjustedCol()+(c.getWidth()/2))-size/2;
    }
    public int getRow(cell c)
    {
    	return (c.getAdjustedRow()+(c.getWidth()/2))-size/2;
    }


}