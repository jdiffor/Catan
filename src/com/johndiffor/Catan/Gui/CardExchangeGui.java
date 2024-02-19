package com.johndiffor.Catan.Gui;

import com.johndiffor.Catan.Model.*;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class CardExchangeGui {
	
	private static final int GAP = 20;
	
	private ArrayList<ResourceCard> cards;
	
	public CardExchangeGui() {
		cards = new ArrayList<ResourceCard>();		
		cards.add(new ResourceCard(Resource.Bricks));
		cards.add(new ResourceCard(Resource.Ore));
		cards.add(new ResourceCard(Resource.Sheep));
		cards.add(new ResourceCard(Resource.Wheat));
		cards.add(new ResourceCard(Resource.Wood));
	}
	
	public void draw(Graphics2D g) {
		
		int centerX = GameWindow.WINDOW_DIM.width / 2;
		int centerY = GameWindow.WINDOW_DIM.height / 2;
		
		int x = centerX - CardGui.WIDTH*3 + GAP*2;
		int y = centerY - CardGui.HEIGHT/2;
		
		for(ResourceCard card : cards) {
			card.draw(g, x, y);
			x += (CardGui.WIDTH + GAP);
		}
	}
	
	public Resource mouseClicked(Point p) {
		int centerX = GameWindow.WINDOW_DIM.width / 2;
		int centerY = GameWindow.WINDOW_DIM.height / 2;
		
		int lowY = centerY - CardGui.HEIGHT/2;
		int highY = lowY + CardGui.HEIGHT;
		
		if(p.getY() < lowY || p.getY() > highY) {
			return null;
		}
		
		int x = centerX - CardGui.WIDTH*3 + GAP*2;
		for(ResourceCard card : cards) {
			if(p.getX() >= x && p.getX() <= x + CardGui.WIDTH) {
				return card.getResource();
			}
			x += (CardGui.WIDTH + GAP);
		}
		
		return null;
	}
	
}
