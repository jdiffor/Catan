package com.johndiffor.Catan.Gui;

import com.johndiffor.Catan.Model.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class RobberGui {
	
	private static final int MAIN_BODY_WIDTH = (int) (40 * GamePanel.SCALE);
	private static final int MAIN_BODY_HEIGHT = (int) (60 * GamePanel.SCALE);
	private static final int HEAD_OFFSET = (int) (30 * GamePanel.SCALE);
	private static final int HEAD_SIZE = (int) (25 * GamePanel.SCALE);
	private static final int BASE_WIDTH = (int) (40 * GamePanel.SCALE);
	private static final int BASE_HEIGHT = (int) (15 * GamePanel.SCALE);
	private static final int BASE_OFFSET = (int) (25 * GamePanel.SCALE);
	private static final int OUTLINE = (int) (2 * GamePanel.SCALE);

	public static void draw(Graphics2D g, Point center) {
		g.setColor(Color.black);
		g.fillOval((int) (center.getX() - MAIN_BODY_WIDTH/2) - OUTLINE, (int) (center.getY() - MAIN_BODY_HEIGHT/2)-OUTLINE, MAIN_BODY_WIDTH+OUTLINE*2, MAIN_BODY_HEIGHT+OUTLINE*2);
		g.fillOval((int) (center.getX() - HEAD_SIZE/2) - OUTLINE, (int) (center.getY() - HEAD_SIZE/2) - HEAD_OFFSET - OUTLINE, HEAD_SIZE +OUTLINE*2 , HEAD_SIZE+OUTLINE*2);
		g.fillRect((int) (center.getX() - BASE_WIDTH/2) - OUTLINE, (int) (center.getY() - BASE_HEIGHT/2) + BASE_OFFSET - OUTLINE, BASE_WIDTH+OUTLINE*2, BASE_HEIGHT+OUTLINE*2);
		
		g.setColor(GameColors.ROBBER_COLOR);
		g.fillOval((int) (center.getX() - MAIN_BODY_WIDTH/2), (int) (center.getY() - MAIN_BODY_HEIGHT/2), MAIN_BODY_WIDTH, MAIN_BODY_HEIGHT);
		g.fillOval((int) (center.getX() - HEAD_SIZE/2), (int) (center.getY() - HEAD_SIZE/2) - HEAD_OFFSET, HEAD_SIZE, HEAD_SIZE);
		g.fillRect((int) (center.getX() - BASE_WIDTH/2), (int) (center.getY() - BASE_HEIGHT/2) + BASE_OFFSET, BASE_WIDTH, BASE_HEIGHT);
	}
	
}
