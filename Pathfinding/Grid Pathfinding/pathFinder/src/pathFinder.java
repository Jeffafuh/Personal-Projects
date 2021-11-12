/**
 * @(#)pathFinder.java
 *
 * pathFinder application
 *
 * @author
 * @version 1.00 2019/12/9
 */

import javax.swing.JFrame;
import java.awt.Component;
import java.util.*;
import static java.lang.System.*;
public class pathFinder extends JFrame{

	private static final int width=1000;
	private static final int height=1000;

    public pathFinder() {
    	super("please work");
    	setSize(width,height);

    	display theGame= new display();
    	((Component)theGame).setFocusable(true);

    	getContentPane().add(theGame);
    	setVisible(true);
    }

    public static void main(String[] args){
        pathFinder run = new pathFinder();
    }
}
