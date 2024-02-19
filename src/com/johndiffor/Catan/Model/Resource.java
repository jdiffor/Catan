package com.johndiffor.Catan.Model;

import java.awt.Color;

public enum Resource {
	
	Bricks(GameColors.BRICK_COLOR),
	Ore(GameColors.ORE_COLOR),
	Sheep(GameColors.SHEEP_COLOR),
	Wheat(GameColors.WHEAT_COLOR),
	Wood(GameColors.WOOD_COLOR),
	Desert(GameColors.DESERT_COLOR),
	Water(GameColors.WATER_COLOR);
	
	private Color color;
	
	private Resource(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
	}
	
}
