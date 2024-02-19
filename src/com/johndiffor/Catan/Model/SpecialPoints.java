package com.johndiffor.Catan.Model;

import com.johndiffor.Catan.Gui.*;
import java.awt.Graphics2D;

public class SpecialPoints {

	private Player largestArmyHolder;
	private int largestArmyAmount;
	private Player longestRoadHolder;
	private int longestRoadAmount;
	private SpecialPointsGui gui;
	
	public SpecialPoints(int numPlayers) {
		this.largestArmyHolder = null;
		this.largestArmyAmount = 2;
		this.longestRoadHolder = null;
		this.longestRoadAmount = 4;
		this.gui = new SpecialPointsGui(this, numPlayers);
	}
	
	public int getLongestRoadLength() {
		return this.longestRoadAmount;
	}
	
	public Player getLongestRoadHolder() {
		return this.longestRoadHolder;
	}
	
	public void setLongestRoad(int length, Player player) {
		this.longestRoadAmount = length;
		this.longestRoadHolder = player;
	}
	
	public int getLargestArmySize() {
		return this.largestArmyAmount;
	}
	
	public Player getLargestArmyHolder() {
		return this.largestArmyHolder;
	}
	
	public void setLargestArmy(int size, Player player) {
		this.largestArmyAmount = size;
		this.largestArmyHolder = player;
	}
	
	public boolean holdsLargestArmy(Player player) {
		return player == this.largestArmyHolder;
	}
	
	public boolean holdsLongesetRoad(Player player) {
		return player == this.longestRoadHolder;
	}
	
	public void draw(Graphics2D g, int playerHeight, int playerGap) {
		gui.draw(g, playerHeight, playerGap);
	}
	
}
