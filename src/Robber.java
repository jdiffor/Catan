
public class Robber {

	private HexTile tile;
	
	public Robber(HexTile initialTile) {
		this.tile = initialTile;
		initialTile.addRobber();
	}
	
	public HexTile getCurrentTile() {
		return this.tile;
	}
	
	public void moveTo(HexTile tile) {
		this.tile = tile;
	}
	
}
