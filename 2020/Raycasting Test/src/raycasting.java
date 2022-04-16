/**
 * @(#)raycasting.java
 *
 * raycasting application
 *
 * @author
 * @version 1.00 2020/5/2
 */
import javax.swing.JFrame;
import java.awt.Component;
import java.util.*;
import static java.lang.System.*;

public class raycasting extends JFrame{

	private final static int width=1024;
	private final static int height=512;

	public raycasting()
	{
		super("hmmm");

		display theThing = new display(width,height);
		((Component)theThing).setFocusable(true);

		getContentPane().add(theThing);
		pack();
		setVisible(true);
	}


    public static void main(String[] args) {
		raycasting run=new raycasting();
    }
}