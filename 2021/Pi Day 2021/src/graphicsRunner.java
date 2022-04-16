import javax.swing.JFrame;
import java.awt.Component;

public class graphicsRunner extends JFrame{
	
	public graphicsRunner()
	{
		super("please work");
		
		display theThing = new display(1500,1000);
		((Component)theThing).setFocusable(true);
		
		getContentPane().add(theThing);
		pack();
		setVisible(true);
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		graphicsRunner run = new graphicsRunner();

	}

}
