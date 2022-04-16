/**
 * @(#)physicsRunner.java
 *
 *
 * @author
 * @version 1.00 2020/3/22
 */
import javax.swing.JFrame;
import java.awt.Component;
import java.util.*;
import static java.lang.System.*;

public class physicsRunner extends JFrame{

	private final static int width=1500;
	private final static int height=1000;

    public physicsRunner() {
    	super("please work");
    	setSize(width,height);

    	display theThingy=new display();
    	((Component)theThingy).setFocusable(true);

    	getContentPane().add(theThingy);
    	setVisible(true);
    }

    public static void main(String[] args){
        physicsRunner run = new physicsRunner();
    }


}