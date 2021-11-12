/**
 * @(#)rogueRunner.java
 *
 *
 * @author
 * @version 1.00 2019/8/23
 */
import javax.swing.JFrame;
import java.awt.Component;
public class rogueRunner extends JFrame{
	private static final int width=1500;
	private static final int height=1000;

    public rogueRunner(){
    	super("Rogue");
    	setSize(width,height);

    	labyrinth theGame = new labyrinth();
    	((Component)theGame).setFocusable(true);

    	getContentPane().add(theGame);
    	setVisible(true);

    }
    public static void main(String[] args) {
    	rogueRunner run=new rogueRunner();
    }
}
