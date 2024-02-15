
public class Pathway {

	private Intersection[] intersections;
	private Road road;
	
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
	
	public boolean hasIntersectionWithStructure() {
		return intersections[0].hasStructure() || intersections[1].hasStructure();
	}
	
	public boolean validForRoad(Player player) {
		for(int i = 0; i < intersections.length; i++) {
			if(intersections[i] != null && intersections[i].playerCanBuildRoadFrom(player)) {
				return true;
			}
		}
		return false;
	}
	
	public Intersection getOtherIntersection(Intersection i) {
		return intersections[1] == i ? intersections[0] : intersections[1];
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
}
