package com.johndiffor.Catan.Model;

import java.awt.Graphics2D;

public class ResourceCard extends Card {

	private Resource resource;
	
	public ResourceCard(Resource r) {
		super();
		this.resource = r;
	}
	
	public Resource getResource() {
		return this.resource;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == this) {
			return true;
		}
		
		if(!(o instanceof ResourceCard)) {
			return false;
		}
		
		ResourceCard c = (ResourceCard) o;
		return this.resource == c.resource;
	}
	
	public void draw(Graphics2D g, int x, int y) {
		gui.draw(g, x, y);
	}
	
}
