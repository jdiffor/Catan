package com.johndiffor.Catan.Gui;

import com.johndiffor.Catan.Model.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class PathwayGui {
	
	private static final double THICKNESS_RATIO = 0.04;

	private Point start;
	private Point end;
	private Pathway pathway;
	private HarborGui harborGui;
	private boolean hover;
	
	public PathwayGui(Point start, Point end, Pathway pathway) {
		this.start = start;
		this.end = end;
		this.pathway = pathway;
		
		if(pathway.hasHarbor()) {
			this.harborGui =  new HarborGui(pathway.getHarbor());
		}
	}
	
	public void draw(Graphics2D g) {
		if(this.harborGui != null) {
			harborGui.draw(g, start, end);
		}
		
		if(hover) {
			g.setColor(Color.white);
			g.setStroke(new BasicStroke((int) (BoardGui.BASE_SIZE * THICKNESS_RATIO)));
			g.drawLine((int) start.getX(), (int) start.getY(), (int) end.getX(), (int) end.getY());
		}
		
		if(this.pathway.hasRoad()) {
			g.setColor(Color.black);
			g.setStroke(new BasicStroke((int) (BoardGui.BASE_SIZE * THICKNESS_RATIO * 1.4)));
			g.drawLine((int) start.getX(), (int) start.getY(), (int) end.getX(), (int) end.getY());
			
			g.setColor(this.pathway.getRoad().getOwnerColor());
			g.setStroke(new BasicStroke((int) (BoardGui.BASE_SIZE * THICKNESS_RATIO)));
			g.drawLine((int) start.getX(), (int) start.getY(), (int) end.getX(), (int) end.getY());
		}
	}
	
	public void addHover() {
		this.hover = true;
	}
	
	public void removeHover() {
		this.hover = false;
	}
	
	public double getDistance(Point p) {
		Point midpoint = new Point((int) ((start.getX() + end.getX()) / 2), (int) ((start.getY() + end.getY()) / 2)); 
		return midpoint.distance(p);
	}
	
	public Pathway getPathway() {
		return this.pathway;
	}
	
}
