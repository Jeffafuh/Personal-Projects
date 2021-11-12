/**
 * @(#)fourierSeries.java
 *
 * fourierSeries application
 *
 * @author
 * @version 1.00 2020/4/12
 */
import javax.swing.JFrame;
import java.awt.Component;
import java.util.*;
import static java.lang.System.*;

public class fourierSeries extends JFrame{

	private final static int width=1500;
	private final static int height=1000;

	public fourierSeries()
	{
		super("please work");
		setSize(width,height);

		display theThing=new display();
		((Component)theThing).setFocusable(true);

		getContentPane().add(theThing);
		setVisible(true);
	}

    public static void main(String[] args) {
		fourierSeries run=new fourierSeries();
    }
}
