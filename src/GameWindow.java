
import java.awt.Dimension;

import javax.swing.JFrame;

public class GameWindow extends JFrame {

	public static final Dimension WINDOW_DIM = new Dimension(1920, 1080);
	
	private GamePanel gamePanel;
	
	public GameWindow(String gameTitle, Table table) {
		super();
		
		// Frame properties
		this.setTitle(gameTitle);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(WINDOW_DIM);
		//this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
	    this.setLocationRelativeTo(null);
	    
	    gamePanel = new GamePanel(table);
	    this.setContentPane(gamePanel);
	    
	    this.setVisible(true);
	    
	    gamePanel.run();
	}
}
