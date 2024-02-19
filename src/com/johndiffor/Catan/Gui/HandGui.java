package com.johndiffor.Catan.Gui;

import com.johndiffor.Catan.Model.*;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class HandGui {

	private static final int HAND_WIDTH = 680;
	private static final int DEFAULT_HAND_SEPARATION = 20;
	private static final int CONTAINER_START_X = DEFAULT_HAND_SEPARATION;
	
	private Hand hand;	
	
	public HandGui(Hand hand) {
		this.hand = hand;
	}
	
	public void mouseClicked(Point p) {
		if(!inHandBounds(p) || this.hand.isEmpty()) {
			return;
		}
		
		ArrayList<ResourceCard> cards = this.hand.getCards();
		int allowedWidth = HAND_WIDTH / cards.size();
		int width = allowedWidth > CardGui.WIDTH + DEFAULT_HAND_SEPARATION ? CardGui.WIDTH + DEFAULT_HAND_SEPARATION : allowedWidth;
		int cardNum = (int) ((p.getX() - CONTAINER_START_X) / width);
		
		if(cardNum >= 0 && cardNum < cards.size()) {
			cards.get(cardNum).toggleSelect();
		}
	}
	
	public void draw(Graphics2D g) {
		if(this.hand.isEmpty()) {
			return;
		}
				
		ArrayList<ResourceCard> cards = this.hand.getCards();
		int allowedWidth = HAND_WIDTH / cards.size();
		int x = CONTAINER_START_X;
		
		int width = allowedWidth > CardGui.WIDTH + DEFAULT_HAND_SEPARATION ? CardGui.WIDTH + DEFAULT_HAND_SEPARATION : allowedWidth;
		
		for(int i = 0; i < cards.size(); i++) {
			cards.get(i).draw(g, x, calculateTopOfCard());
			x += width;
		}
	}
	
	private boolean inHandBounds(Point p) {
		if(p.getX() < CONTAINER_START_X) {
			return false;
		}
		
		if(p.getY() < calculateTopOfCard()) {
			return false;
		}
		
		if(p.getX() > CONTAINER_START_X + HAND_WIDTH) {
			return false;
		}
		
		if(p.getY() > GameWindow.WINDOW_DIM.height - DEFAULT_HAND_SEPARATION) {
			return false;
		}
		
		return true;
	}
	
	private int calculateTopOfCard() {
		return GameWindow.WINDOW_DIM.height - DEFAULT_HAND_SEPARATION - CardGui.HEIGHT;
	}
	
}
