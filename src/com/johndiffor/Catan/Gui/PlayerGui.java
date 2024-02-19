package com.johndiffor.Catan.Gui;

import com.johndiffor.Catan.Model.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

public class PlayerGui {
	
	private static final int WIDTH = 400;
	private static final int COLOR_BOX_WIDTH_RATIO = 1;
	private static final int HIGHLIGHT_BORDER_OUTER = 5;
	private static final int HIGHLIGHT_BORDER_INNER = 3;
	private static final Font NAME_FONT = new Font("Algerian", Font.BOLD, 16);
	
	private static final int TRADE_BUTTON_WIDTH = 80;
	private static final double TRADE_BUTTON_HEIGTH_RATIO = 0.8;
	private static final int TRADE_BUTTON_RIGHT_BORDER = 5;

	private int height;
	private int gap;
	private Player player;
	private int playerNum;
	private ActionButton tradeButton;
	private ActionButton tradeAllButton;
	private ActionButton stealButton;
	
	public PlayerGui(Player player, int playerNum, int height, int gap) {
		this.player = player;
		this.height = height;
		this.gap = gap;
		this.tradeButton = new ActionButton("Trade", null, new Point((int) (GameWindow.WINDOW_DIM.getWidth() - TRADE_BUTTON_WIDTH/2 - TRADE_BUTTON_RIGHT_BORDER), (int) gap + (height+gap)*playerNum + height/2), TRADE_BUTTON_WIDTH, (int) (height * TRADE_BUTTON_HEIGTH_RATIO));
		this.tradeAllButton = new ActionButton("Trade All", null, new Point((int) (GameWindow.WINDOW_DIM.getWidth() - TRADE_BUTTON_WIDTH/2 - TRADE_BUTTON_RIGHT_BORDER), (int) gap + (height+gap)*playerNum + height/2), TRADE_BUTTON_WIDTH, (int) (height * TRADE_BUTTON_HEIGTH_RATIO));
		this.stealButton = new ActionButton("Steal", Action.Steal, new Point((int) (GameWindow.WINDOW_DIM.getWidth() - TRADE_BUTTON_WIDTH/2 - TRADE_BUTTON_RIGHT_BORDER), (int) gap + (height+gap)*playerNum + height/2), TRADE_BUTTON_WIDTH, (int) (height * TRADE_BUTTON_HEIGTH_RATIO));
		this.playerNum = playerNum;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void draw(Graphics2D g, boolean myTurn) {
		
		// Highlighted if turn
		if(myTurn) {
			g.setColor(Color.black);
			g.fillRect((int) (GameWindow.WINDOW_DIM.getWidth() - WIDTH) - HIGHLIGHT_BORDER_OUTER, gap + (height+gap)*playerNum - HIGHLIGHT_BORDER_OUTER, WIDTH+HIGHLIGHT_BORDER_OUTER*2, height+HIGHLIGHT_BORDER_OUTER*2);
			
			g.setColor(Color.white);
			g.fillRect((int) (GameWindow.WINDOW_DIM.getWidth() - WIDTH) - HIGHLIGHT_BORDER_INNER, gap + (height+gap)*playerNum - HIGHLIGHT_BORDER_INNER, WIDTH+HIGHLIGHT_BORDER_INNER*2, height+HIGHLIGHT_BORDER_INNER*2);	
		} else {
			g.setColor(Color.black);
			g.fillRect((int) (GameWindow.WINDOW_DIM.getWidth() - WIDTH) - HIGHLIGHT_BORDER_INNER, gap + (height+gap)*playerNum - HIGHLIGHT_BORDER_INNER, WIDTH+HIGHLIGHT_BORDER_INNER*2, height+HIGHLIGHT_BORDER_INNER*2);
		}
		
		// Background
		g.setColor(GameColors.PLAYER_BACKGROUND_COLOR);
		g.fillRect((int) (GameWindow.WINDOW_DIM.getWidth() - WIDTH), gap + (height+gap)*playerNum, WIDTH, height);
		
		// Player color as banner		
		g.setColor(player.getPieceColor());
		g.fillRect((int) (GameWindow.WINDOW_DIM.getWidth() - WIDTH), gap + (height+gap)*playerNum, (height * COLOR_BOX_WIDTH_RATIO), height);
		
		// Player name
		g.setColor(Color.black);
		Utils.drawCenteredHeightString(g, player.getName() + "  -  " + player.getHand().size() + " cards", new Point((int) (GameWindow.WINDOW_DIM.getWidth() - WIDTH + (height * COLOR_BOX_WIDTH_RATIO) + gap), gap + (height+gap)*playerNum + height/2), NAME_FONT);
		
		// Player score
		int knownScore = player.getKnownScore();
		int possibleExtraPoints = player.getUnplayedDevelopmentCardsCount();
		String knownScoreString = possibleExtraPoints > 0 ? knownScore + "+" + possibleExtraPoints + "?" : knownScore + "";
		String realScoreString = player.getRealScore() + "";
		
		Utils.drawCenteredString(g, player instanceof PlayerAI ? knownScoreString : realScoreString, new Point((int) (GameWindow.WINDOW_DIM.getWidth() - WIDTH + (height * COLOR_BOX_WIDTH_RATIO)/2), gap + (height+gap)*playerNum + height/2), NAME_FONT);
		
		// Player number of cards
		//Utils.drawCenteredHeightString(g, player.getHand().size() + " cards", new Point((int) (GameWindow.WINDOW_DIM.getWidth() - WIDTH + COLOR_BOX_WIDTH + GAP), GAP + (HEIGHT+GAP)*playerNum + HEIGHT/2), NAME_FONT);
		
		// Buttons
		this.tradeButton.draw(g);
		this.tradeAllButton.draw(g);
		this.stealButton.draw(g);
	}
	
	public boolean buttonClicked(Point p) {
		return this.tradeButtonClicked(p) || this.tradeAllButtonClicked(p) || this.stealButtonClicked(p);
	}
	
	public boolean tradeButtonClicked(Point p) {
		return this.tradeButton.clicked(p);
	}
	
	public boolean tradeAllButtonClicked(Point p) {
		return this.tradeAllButton.clicked(p);
	}
	
	public boolean stealButtonClicked(Point p) {
		return this.stealButton.clicked(p);
	}
	
	public void setTradeButtonAllowed(boolean allowed) {
		this.tradeButton.setActive(allowed);
		this.tradeButton.setHidden(!allowed);
	}
	
	public void setTradeAllButtonAllowed(boolean allowed) {
		this.tradeAllButton.setActive(allowed);
		this.tradeAllButton.setHidden(!allowed);
	}
	
	public void setStealButtonAllowed(boolean allowed) {
		this.stealButton.setActive(allowed);
		this.stealButton.setHidden(!allowed);
	}
	
}
