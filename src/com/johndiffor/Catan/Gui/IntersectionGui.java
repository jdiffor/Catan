package com.johndiffor.Catan.Gui;

import com.johndiffor.Catan.Model.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class IntersectionGui {

	private static final double SIZE_RATIO = 0.2;
	
	private Point center;
	private Intersection intersection;
	private int size;
	private boolean hover;
	
	public IntersectionGui(Point center, Intersection intersection) {
		this.center = center;
		this.intersection = intersection;
		this.size = (int) (BoardGui.BASE_SIZE * SIZE_RATIO);
	}
	
	public void draw(Graphics2D g) {
		if(hover) {
			g.setColor(Color.white);
			g.setStroke(new BasicStroke(4));
			g.drawOval((int) (center.getX() - size/2), (int) (center.getY() - size/2), size, size);
		}
		
		if(this.intersection.hasStructure()) {
			g.setColor(this.intersection.getStructure().getOwnerColor());
			
			if(this.intersection.getStructure() instanceof Settlement) {
				g.fillOval((int) (center.getX() - size/2), (int) (center.getY() - size/2), size, size);
				g.setColor(Color.black);
				g.setStroke(new BasicStroke(2));
				g.drawOval((int) (center.getX() - size/2), (int) (center.getY() - size/2), size, size);
			} else if(this.intersection.getStructure() instanceof City) {
				g.fillRect((int) (center.getX() - size/2), (int) (center.getY() - size/2), size, size);
				g.setColor(Color.black);
				g.setStroke(new BasicStroke(2));
				g.drawRect((int) (center.getX() - size/2), (int) (center.getY() - size/2), size, size);
			} else {
				// This should never happen
			}
		}
	}
	
	public void addHover() {
		this.hover = true;
	}
	
	public void removeHover() {
		this.hover = false;
	}
	
	public boolean inRange(Point p) {
		return center.distance(p) <= size;
	}
	
	public Intersection getIntersection() {
		return this.intersection;
	}
}
