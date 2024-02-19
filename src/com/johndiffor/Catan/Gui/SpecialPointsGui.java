package com.johndiffor.Catan.Gui;

import com.johndiffor.Catan.Model.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class SpecialPointsGui {

	private static final int WIDTH = 180;
	private static final int HEIGHT = 120;
	private static final int GAP = 15;
	private static final int BORDER_SIZE = 3;
	
	private SpecialPoints specialPoints;
	private int numPlayers;
	
	public SpecialPointsGui(SpecialPoints sp, int numPlayers) {
		this.specialPoints = sp;
		this.numPlayers = numPlayers;
	}
	
	public void draw(Graphics2D g, int playerHeight, int playerGap) {
		int yOffset = playerHeight*numPlayers + playerGap*(numPlayers + 1);
		int baseX = GameWindow.WINDOW_DIM.width - (WIDTH*2 + GAP*2);
		
		// Longest road
		g.setColor(Color.black);
		g.fillRect(baseX - BORDER_SIZE, yOffset-BORDER_SIZE, WIDTH + BORDER_SIZE*2, HEIGHT + BORDER_SIZE*2);
		g.setColor(GameColors.PLAYER_BACKGROUND_COLOR);
		g.fillRect(baseX, yOffset, WIDTH, HEIGHT);
		
		int centerX = baseX + WIDTH/2;
		int centerY = yOffset + HEIGHT/2;
		
		g.setColor(GameColors.ROAD_BUILDING_COLOR);
		g.fillRect(baseX, yOffset, WIDTH, GAP*2);
		g.setColor(GameColors.PLAYER_BACKGROUND_COLOR);
		Utils.drawCenteredString(g, "Longest Road", new Point(centerX, yOffset + GAP), Utils.fontOfSize(14));
		
		Point boxCenter = new Point(centerX, centerY);
		Point boxBelowCenter = new Point(centerX, centerY + GAP);
		
		g.setColor(Color.black);
		if(specialPoints.getLongestRoadHolder() == null) {
			Utils.drawCenteredString(g, "Unowned", boxCenter, Utils.fontOfSize(14));
		} else {
			Utils.drawCenteredString(g, "Owned by " + specialPoints.getLongestRoadHolder().getName(), boxCenter, Utils.fontOfSize(14));
			Utils.drawCenteredString(g, specialPoints.getLongestRoadLength() + " roads", boxBelowCenter, Utils.fontOfSize(14));
		}
		
		
		
		// Largest army
		g.setColor(Color.black);
		g.fillRect(baseX + WIDTH + GAP - BORDER_SIZE, yOffset-BORDER_SIZE, WIDTH + BORDER_SIZE*2, HEIGHT + BORDER_SIZE*2);
		g.setColor(GameColors.PLAYER_BACKGROUND_COLOR);
		g.fillRect(baseX + WIDTH + GAP, yOffset, WIDTH, HEIGHT);
		
		g.setColor(GameColors.KNIGHT_COLOR);
		g.fillRect(baseX + WIDTH + GAP, yOffset, WIDTH, GAP*2);
		g.setColor(GameColors.PLAYER_BACKGROUND_COLOR);
		Utils.drawCenteredString(g, "Largest Army", new Point(baseX + WIDTH + GAP + WIDTH/2, yOffset + GAP), Utils.fontOfSize(14));
		
		centerX += (WIDTH + GAP);
		boxCenter = new Point(centerX, centerY);
		boxBelowCenter = new Point(centerX, centerY + GAP);
		
		g.setColor(Color.black);
		if(specialPoints.getLargestArmyHolder() == null) {
			Utils.drawCenteredString(g, "Unowned", boxCenter, Utils.fontOfSize(14));
		} else {
			Utils.drawCenteredString(g, "Owned by " + specialPoints.getLargestArmyHolder().getName(), boxCenter, Utils.fontOfSize(14));
			Utils.drawCenteredString(g, specialPoints.getLargestArmySize() + " knights", boxBelowCenter, Utils.fontOfSize(14));
		}
	}
	
}
