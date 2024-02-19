package com.johndiffor.Catan.Model;

import com.johndiffor.Catan.Gui.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class Menu {
	
	private static final int MENU_WIDTH = 500;
	private static final int MENU_HEIGHT = 275;
	private static final int Y_OFFSET = 40;
	private static final int BORDER_SIZE = 10;
	private static final int BUTTON_WIDTH = 120;
	private static final int BUTTON_HEIGHT = 60;

	private ActionButton threePlayerButton;
	private ActionButton fourPlayerButton;
	private ActionButton quitButton;
	private ActionButton resumeButton;
	private ActionButton homeButton;
	private boolean isActive;
	private boolean isOutsideOfGame;
	
	public Menu() {
		threePlayerButton = new ActionButton("3 Players", null, new Point(GameWindow.WINDOW_DIM.width/2 - BUTTON_WIDTH*3/5,  GameWindow.WINDOW_DIM.height/2 - BUTTON_HEIGHT*2/3), BUTTON_WIDTH, BUTTON_HEIGHT);
		fourPlayerButton = new ActionButton("4 Players", null, new Point(GameWindow.WINDOW_DIM.width/2 + BUTTON_WIDTH*3/5,  GameWindow.WINDOW_DIM.height/2 - BUTTON_HEIGHT*2/3), BUTTON_WIDTH, BUTTON_HEIGHT);
		quitButton = new ActionButton("Quit", null, new Point(GameWindow.WINDOW_DIM.width/2,  GameWindow.WINDOW_DIM.height/2 + BUTTON_HEIGHT*2/3), BUTTON_WIDTH, BUTTON_HEIGHT); 
		resumeButton = new ActionButton("Resume", null, new Point(GameWindow.WINDOW_DIM.width/2 - BUTTON_WIDTH*3/5,  GameWindow.WINDOW_DIM.height/2 - BUTTON_HEIGHT*2/3), BUTTON_WIDTH, BUTTON_HEIGHT);
		homeButton = new ActionButton("Main Menu", null, new Point(GameWindow.WINDOW_DIM.width/2 + BUTTON_WIDTH*3/5,  GameWindow.WINDOW_DIM.height/2 - BUTTON_HEIGHT*2/3), BUTTON_WIDTH, BUTTON_HEIGHT);
		
		
		isActive = true;
		isOutsideOfGame = true;
		
		threePlayerButton.setActive(true);
		threePlayerButton.setHidden(false);
		fourPlayerButton.setActive(true);
		fourPlayerButton.setHidden(false);
		quitButton.setActive(true);
		quitButton.setHidden(false);
		
		resumeButton.setActive(true);
		resumeButton.setHidden(true);
		homeButton.setActive(true);
		homeButton.setHidden(true);
	}
	
	public int mouseClicked(Point p) {
		if(threePlayerButton.clicked(p)) {
			this.isOutsideOfGame = false;
			return 3;
		}
		
		if(fourPlayerButton.clicked(p)) {
			this.isOutsideOfGame = false;
			return 4;
		}
		
		if(quitButton.clicked(p)) {
			return -1;
		}
		
		if(resumeButton.clicked(p)) {
			toggleMidGame();
			return 0;
		}
		
		if(homeButton.clicked(p)) {
			return 1;
		}

		return 0;
	}
	
	public void draw(Graphics2D g) {
		
		g.setColor(Color.black);
		g.fillRoundRect(GameWindow.WINDOW_DIM.width/2 - MENU_WIDTH/2 - BORDER_SIZE, GameWindow.WINDOW_DIM.height/2 - MENU_HEIGHT/2 - Y_OFFSET - BORDER_SIZE, MENU_WIDTH + BORDER_SIZE*2, MENU_HEIGHT + BORDER_SIZE*2, 15, 15);
		
		g.setColor(GameColors.WHEAT_COLOR);
		g.fillRect(GameWindow.WINDOW_DIM.width/2 - MENU_WIDTH/2, GameWindow.WINDOW_DIM.height/2 - MENU_HEIGHT/2 - Y_OFFSET, MENU_WIDTH, MENU_HEIGHT);
		
		g.setColor(Color.black);
		Utils.drawCenteredString(g, "Settlers of Catan", new Point(GameWindow.WINDOW_DIM.width/2, GameWindow.WINDOW_DIM.height/2 - 130), Utils.fontOfSize(40));
		
		threePlayerButton.draw(g);
		fourPlayerButton.draw(g);
		quitButton.draw(g);
		
		resumeButton.draw(g);
		homeButton.draw(g);
	}
	
	public boolean isActive() {
		return this.isActive;
	}
	
	public void hide() {
		this.isActive = false;
	}
	
	public void show() {
		this.threePlayerButton.setHidden(false);
		this.fourPlayerButton.setHidden(false);
		this.resumeButton.setHidden(true);
		this.homeButton.setHidden(true);
		this.isOutsideOfGame = true;
		this.isActive = true;
	}
	
	public void toggleMidGame() {
		if(!this.isOutsideOfGame) {
			this.isActive = !this.isActive;
			this.threePlayerButton.setHidden(true);
			this.fourPlayerButton.setHidden(true);
			this.resumeButton.setHidden(false);
			this.homeButton.setHidden(false);
		}
	}
	
	public boolean getIsOutsideOfGame() {
		return this.isOutsideOfGame;
	}
	
	public void setOutsideOfGame() {
		this.isOutsideOfGame = true;
	}
	
}
