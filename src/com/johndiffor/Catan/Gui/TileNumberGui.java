package com.johndiffor.Catan.Gui;

import com.johndiffor.Catan.Model.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

public class TileNumberGui {
	
	private final static double PARENT_SIZE_RATIO = 0.3;
	private final static double FONT_SIZE_RATIO = 0.15;
	private final static int[] SPECIAL_NUMBERS = new int[] {6, 8};
	public final static Font NUMBER_FONT = new Font("Algerian", Font.BOLD, (int) (BoardGui.BASE_SIZE * FONT_SIZE_RATIO));
	
	private TileNumber number;
	private int size;
	private Color color;

	public TileNumberGui(TileNumber number) {
		this.number = number;
		this.size = (int) (BoardGui.BASE_SIZE * PARENT_SIZE_RATIO);
		
		// Desert does not have a number
		if(number != null) {
			this.color = Utils.intArrayContains(SPECIAL_NUMBERS, number.getNumber()) ? Color.red : Color.black;
		}
		
	}
	
	public void draw(Graphics2D g, Point center) {
		
		// Desert does not have a number
		if(this.number != null) {
			g.setColor(GameColors.TILE_NUMBER_COLOR);
			g.fillOval((int) (center.getX() - size/2), (int) (center.getY() - size/2), size, size);
			
			g.setColor(this.color);
			Utils.drawCenteredString(g, number.getNumberString(), center, NUMBER_FONT);
		}
	}
	
}
