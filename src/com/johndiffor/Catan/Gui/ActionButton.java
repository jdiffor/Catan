package com.johndiffor.Catan.Gui;

import com.johndiffor.Catan.Model.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

public class ActionButton {
	
	public static final int BUTTON_WIDTH = 200;
	public static final int BUTTON_HEIGHT = 100;
	public static final Font BUTTON_FONT = new Font("Algerian", Font.BOLD, 16);
	
	private String title;
	private Action action;
	private Point center;
	private boolean active;
	private boolean hidden;
	private int width;
	private int height;

	public ActionButton(String title, Action action, Point center) {
		this(title, action, center, BUTTON_WIDTH, BUTTON_HEIGHT);
	}
	
	public ActionButton(String title, Action action, Point center, int width, int height) {
		this.title = title;
		this.action = action;
		this.center = center;
		
		this.width = width;
		this.height = height;
	}
	
	public Action getAction() {
		return this.action;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	
	public boolean clicked(Point p) {
		return this.active && !this.hidden && inRange(p);
	}
	
	public boolean inRange(Point p) {
		return p.getX() >= center.getX() - width / 2 &&
				p.getY() >= center.getY() - height / 2 &&
				p.getX() <= center.getX() + width / 2 &&
				p.getY() < center.getY() + height / 2;
	}
	
	
	public void draw(Graphics2D g) {
		if(this.hidden) {
			return;
		}
		
		if(this.active) {
			g.setColor(GameColors.BUTTON_ACITVE_COLOR);
		} else {
			g.setColor(GameColors.BUTTON_INACITVE_COLOR);
		}
		g.fillRoundRect((int) (center.getX() - width/2), (int) (center.getY() - height/2), width, height, 0, 0);
		
		if(this.active) {
			g.setColor(Color.black);
		} else {
			g.setColor(Utils.averageColor(Color.black, GameColors.TABLE_COLOR));
		}
		
		g.setStroke(new BasicStroke(1));
		g.drawRoundRect((int) (center.getX() - width/2), (int) (center.getY() - height/2), width, height, 0, 0);

		Utils.drawCenteredString(g, title, center, BUTTON_FONT);
		
	}
}
