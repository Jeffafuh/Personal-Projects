/**
 * @(#)MineSweeperRunner.java
 *
 *
 * @author
 * @version 1.00 2019/11/13
 */
import javax.swing.JFrame;
import java.awt.Component;
public class MineSweeperRunner extends JFrame{

	private static final int width=1000;
	private static final int height=1000;

    public MineSweeperRunner() {
    	super("MineSweeper");
    	setSize(width,height);

    	Menu theGame= new Menu();
    	((Component)theGame).setFocusable(true);

    	getContentPane().add(theGame);
    	setVisible(true);
    }

    public static void main(String[] args) {
        MineSweeperRunner run = new MineSweeperRunner();
    }
}
