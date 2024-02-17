import java.awt.Color;

public class Card {

	protected CardGui gui;
	private boolean selected;
	
	public Card() {
		this.gui = new CardGui(this);
	}
	
	public boolean isSelected() {
		return this.selected;
	}
	
	public void toggleSelect() {
		this.selected = !this.selected;
	}
	
	public void select() {
		this.selected = true;
	}
	
	public void unSelect() {
		this.selected = false;
	}
	
	public Color getColor() {
		return Color.white;
	}
	
}
