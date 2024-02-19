package com.johndiffor.Catan.Model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {
	
	public static final double SCALE = 1;
	private static final int AI_DELAY_TIME_IN_MILLIS = 1000;
	
	private Table table;
	private Menu menu;
	
	public GamePanel() {
		this.menu = new Menu();
		
		
		// JPanel properties
		this.setPreferredSize(GameWindow.WINDOW_DIM);
		this.setBackground(GameColors.TABLE_COLOR);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		
		this.addMouseMotionListener(new MouseAdapter() {
			public void mouseMoved(MouseEvent e) {
				if(menu.isActive()) {
					
				} else {
					table.mouseMoved(e.getPoint());
				}
				
			}
			
			public void mouseDragged(MouseEvent e) {
				if(menu.isActive()) {
					
				} else {
					table.mouseMoved(e.getPoint());
				}
			}
		});
		
		this.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if(menu.isActive()) {
					int val = menu.mouseClicked(e.getPoint());
					
					if(val == -1) {
						System.exit(0);
					} else if(val == 1) {
						menu.show();
						table = null;
					} else if(val == 3) {
						table = new Table(3);
						menu.hide();
					} else if(val == 4) {
						table = new Table(4);
						menu.hide();
					}
				} else {
					table.mouseClicked(e.getPoint());
				}
				
			}
		});
		
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					menu.toggleMidGame();
				}
			}
		});
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if(this.table != null) {
			this.table.draw(g2);
		}
		
		if(menu.isActive()) {
			menu.draw(g2);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		long prevTime = Utils.time();
		while(true) {
			if(Utils.time() - prevTime >= AI_DELAY_TIME_IN_MILLIS) {
				if(!menu.isActive() && table != null) {
					table.takeAITurnIfApplicable();
					if(table.updateScores()) {
						menu.show();
					}
				}
				
				prevTime = Utils.time();
			}
			
			repaint();
			Utils.wait(30);
		}
	}
	
}
