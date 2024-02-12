
public class BoardClickEvent {

	private HexTile hexTile;
	private Pathway pathway;
	private Intersection intersection;
	
	public BoardClickEvent() {
		
	}
	
	public BoardClickEvent(HexTile hexTile) {
		this.hexTile = hexTile;
	}
	
	public BoardClickEvent(Pathway pathway) {
		this.pathway = pathway;
	}
	
	public BoardClickEvent(Intersection intersection) {
		this.intersection = intersection;
	}

	public HexTile getHexTile() {
		return hexTile;
	}

	public Pathway getPathway() {
		return pathway;
	}

	public Intersection getIntersection() {
		return intersection;
	}
}
