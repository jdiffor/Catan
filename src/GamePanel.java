
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
	
	public static final double SCALE = 1;
	private static final int AI_DELAY_TIME_IN_MILLIS = 1000;
	
	private Table table;
	
	public GamePanel(Table table) {
		
		this.table = table;
		
		// JPanel properties
		this.setPreferredSize(GameWindow.WINDOW_DIM);
		this.setBackground(GameColors.TABLE_COLOR);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		
		this.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				table.mouseMoved(e.getPoint());
			}
		});
		
		this.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				/*
				boolean shouldRepaint = table.mouseClicked(e.getPoint());
				if(shouldRepaint) {
					System.out.println("repaint?");
					repaint();
				}
				*/
				table.mouseClicked(e.getPoint());
			}
		});
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		this.table.draw(g2);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		long prevTime = Utils.time();
		while(true) {
			if(Utils.time() - prevTime >= AI_DELAY_TIME_IN_MILLIS) {
				table.takeAITurnIfApplicable();
				prevTime = Utils.time();
			}
			
			repaint();
			Utils.wait(30);
		}
	}
	
}
