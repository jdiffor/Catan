import java.util.HashMap;

public class Harbor {
	
	public static HashMap<Resource, HarborType> resourceToHarbor = setMap();
	
	private HarborType type;
	
	public Harbor(HarborType type) {
		this.type = type;
	}
	
	public HarborType getType() {
		return this.type;
	}
	
	public static HashMap<Resource, HarborType> setMap() {
		HashMap<Resource, HarborType> map = new HashMap<Resource, HarborType>();
		map.put(Resource.Bricks, HarborType.TwoForOneBricks);
		map.put(Resource.Ore, HarborType.TwoForOneOre);
		map.put(Resource.Sheep, HarborType.TwoForOneSheep);
		map.put(Resource.Wheat, HarborType.TwoForOneWheat);
		map.put(Resource.Wood, HarborType.TwoForOneWood);
		return map;
	}
	
}
