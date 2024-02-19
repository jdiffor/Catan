package com.johndiffor.Catan.Gui;

import com.johndiffor.Catan.Model.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class TradeProposalGui {

	private static final int GAP = 20;
	private static final int BUTTON_OFFSET = 30;
	
	private TradeProposal tradeProposal;
	private ResourceCard[] cards;
	
	public TradeProposalGui(TradeProposal tradeProposal) {
		this.tradeProposal = tradeProposal;
		
		cards = new ResourceCard[5];	
		cards[0] = new ResourceCard(Resource.Bricks);
		cards[1] = new ResourceCard(Resource.Ore);
		cards[2] = new ResourceCard(Resource.Sheep);
		cards[3] = new ResourceCard(Resource.Wheat);
		cards[4] = new ResourceCard(Resource.Wood);
	}
	
	public void draw(Graphics2D g) {
		
		int centerX = GameWindow.WINDOW_DIM.width / 2;
		int centerY = GameWindow.WINDOW_DIM.height / 2;
		
		int x = centerX - CardGui.WIDTH*3 + GAP*2;
		int y = centerY - CardGui.HEIGHT/2;
		
		for(ResourceCard card : cards) {
			card.draw(g, x, y);
			
			if(tradeProposal.getResourceCount(card.getResource()) >= 0) {
				g.setColor(Color.black);
				Utils.drawCenteredString(g, "+", new Point(x + CardGui.WIDTH/2, y + BUTTON_OFFSET), Utils.fontOfSize(32));
				Utils.drawCenteredString(g, tradeProposal.getResourceCount(card.getResource()) + "", new Point(x + CardGui.WIDTH/2, y + CardGui.HEIGHT/2), Utils.fontOfSize(32));
				Utils.drawCenteredString(g, "-", new Point(x + CardGui.WIDTH/2, y + CardGui.HEIGHT - BUTTON_OFFSET), Utils.fontOfSize(32));
			}
			
			x += (CardGui.WIDTH + GAP);
		}
	}
	
	public void mouseClicked(Point p) {
		int centerX = GameWindow.WINDOW_DIM.width / 2;
		int centerY = GameWindow.WINDOW_DIM.height / 2;
		
		int lowY = centerY - CardGui.HEIGHT/2;
		int highY = lowY + CardGui.HEIGHT;
		int midY = (lowY + highY) / 2;
		
		if(p.getY() < lowY || p.getY() > highY) {
			return;
		}
		
		boolean increase = false;
		if(p.getY() < midY) {
			increase = true;
		}
		
		int x = centerX - CardGui.WIDTH*3 + GAP*2;
		for(ResourceCard card : cards) {
			if(p.getX() >= x && p.getX() <= x + CardGui.WIDTH) {
				if(increase) {
					tradeProposal.increaseResourceCount(card.getResource());
				} else {
					tradeProposal.decreaseResourceCount(card.getResource());
				}
				
				return;
			}
			x += (CardGui.WIDTH + GAP);
		}
	}
	
}
