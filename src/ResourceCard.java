import java.awt.Graphics2D;

public class ResourceCard extends Card {

	private Resource resource;
	private ResourceCardGui gui;
	private boolean selected;
	
	public ResourceCard(Resource r) {
		this.resource = r;
		this.gui = new ResourceCardGui(this);
	}
	
	public Resource getResource() {
		return this.resource;
	}
	
	public void toggleSelect() {
		this.selected = !this.selected;
	}
	
	public void unSelect() {
		this.selected = false;
	}
	
	public boolean isSelected() {
		return this.selected;
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
