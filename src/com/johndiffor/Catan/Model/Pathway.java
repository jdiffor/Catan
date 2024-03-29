package com.johndiffor.Catan.Model;

public class Pathway {

	private Intersection[] intersections;
	private Road road;
	private Harbor harbor;
	
	public Pathway() {
		this.intersections = new Intersection[2];
	}

	public void addIntersection(Intersection i) {
		if(intersections[0] == null) {
			intersections[0] = i;
		}
		
		if(intersections[1] == null && !intersections[0].equals(i)) {
			intersections[1] = i;
		}
	}
	
	public void addHarbor(HarborType type) {
		harbor = new Harbor(type);
	}
	
	public boolean hasHarbor() {
		return this.harbor != null;
	}
	
	public Harbor getHarbor() {
		return this.harbor;
	}
	
	public boolean hasIntersectionWithStructure() {
		return intersections[0].hasStructure() || intersections[1].hasStructure();
	}
	
	public boolean validForRoad(Player player) {
		if(this.hasRoad()) {
			return false;
		}
		
		for(int i = 0; i < intersections.length; i++) {
			if(intersections[i] != null && intersections[i].playerCanBuildRoadFrom(player)) {
				// If player is building an initial road, make sure it touches most recent settlement
				if(player.hasInitialSettlementLeft()) {
					if(player.isLastInitialSettlementLocation(intersections[i])) {
						return true;
					} else {
						// Do nothing, check next settlement
					}
				} else {
					return true;
				}
			}
		}
		return false;
		
	}
	
	public Intersection getOtherIntersection(Intersection i) {
		return intersections[1] == i ? intersections[0] : intersections[1];
	}
	
	public Intersection getIntersectionNotTouchingPlayer(Player p) {
		for(int i = 0; i < intersections.length; i++) {
			if(!intersections[i].touchesOwnedRoad(p)) {
				return intersections[i];
			}
		}
		return null;
	}
	
	public boolean hasRoad() {
		return this.road != null;
	}
	
	public Road getRoad() {
		return this.road;
	}
	
	public void buildRoad(Player player) {
		this.road = new Road(player);
	}
	
	public boolean ownedBy(Player player) {
		if(this.road == null) {
			return false;
		}
		return this.road.ownedBy(player);
	}
	
	public String toString() {
		String s = "";
		for(int i = 0; i < intersections.length; i++) {
			s += "( ";
			if(intersections[i] != null) {
				for(HexTile tile : intersections[i].getHexTiles()) {
					s += tile.getNumber() + " ";
				}
			}
			s += ") ";
		}
		return s;
	}
}
