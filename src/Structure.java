import java.awt.Color;

public class Structure {

	private Player owner;
	
	public Structure(Player owner) {		
		this.owner = owner;
	}
	
	public Player getOwner() {
		return this.owner;
	}
	
	public boolean ownedBy(Player player) {
		return player == owner;
	}
	
	public Color getOwnerColor() {
		return this.owner.getPieceColor();
	}
	
}
