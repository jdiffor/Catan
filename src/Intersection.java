
public class Intersection {

	private Pathway[] pathways;
	private Structure structure;
	
	public Intersection() {
		this.pathways = new Pathway[3];
	}
	
	public void addPathway(Pathway p) {
		if(pathways[0] == null) {
			pathways[0] = p;
		}
		
		if(pathways[1] == null && !pathways[0].equals(p)) {
			pathways[1] = p;
		}
		
		if(pathways[2] == null && !pathways[0].equals(p) && !pathways[1].equals(p)) {
			pathways[2] = p;
		}
	}
	
	public boolean hasStructure() {
		return this.structure != null;
	}
	
	public Structure getStructure() {
		return this.structure;
	}
	
	public void buildSettlement(Player player) {
		this.structure = new Settlement(player);
	}
	
	public void buildCity(Player player) {
		this.structure = new City(player);
	}
	
	public boolean validForSettlement(Player player, boolean initialSetup) {
		for(int i = 0; i < pathways.length; i++) {
			if(pathways[i] != null && pathways[i].hasIntersectionWithStructure()) {
				return false;
			}
		}
		
		if(!initialSetup) {
			// TODO: check for roads
		}
		
		return true;
	}
	
	public boolean validForCity(Player player) {
		return structure != null && structure instanceof Settlement && structure.ownedBy(player);
	}
	
	public boolean playerCanBuildRoadFrom(Player player) {
		return (structure != null && structure.ownedBy(player)) || touchesOwnedRoad(player);
	}
	
	private boolean touchesOwnedRoad(Player player) {
		for(int i = 0; i < pathways.length; i++) {
			if(pathways[i] != null && pathways[i].ownedBy(player)) {
				return true;
			}
		}
		return false;
	}
	
	
}
