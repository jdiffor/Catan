
public class HexTile {

	private Pathway twelve;
	private Pathway two;
	private Pathway four;
	private Pathway six;
	private Pathway eight;
	private Pathway ten;
	private Intersection one;
	private Intersection three;
	private Intersection five;
	private Intersection seven;
	private Intersection nine;
	private Intersection eleven;
	private TileNumber tileNumber;
	private Resource resource;
	private boolean hasRobber;
	
	/*
	 * This constructor is a hack to get the board background.
	 * Should be removed and the board background should be its own thing. 
	 */
	public HexTile(Resource r) {
		this.resource = r;
	}
	
	public HexTile() {
		this(new SetupManager());
	}
	
	public HexTile(SetupManager sm) {
		this.resource = sm.getNextResource();
		if(this.resource != Resource.Desert) {
			this.tileNumber = sm.getNextNumber();
		}
	}
	
	public Pathway getTwelve() {
		return twelve;
	}

	public void setTwelve(Pathway twelve) {
		this.twelve = twelve;
	}

	public Pathway getTwo() {
		return two;
	}

	public void setTwo(Pathway two) {
		this.two = two;
	}

	public Pathway getFour() {
		return four;
	}

	public void setFour(Pathway four) {
		this.four = four;
	}

	public Pathway getSix() {
		return six;
	}

	public void setSix(Pathway six) {
		this.six = six;
	}

	public Pathway getEight() {
		return eight;
	}

	public void setEight(Pathway eight) {
		this.eight = eight;
	}

	public Pathway getTen() {
		return ten;
	}

	public void setTen(Pathway ten) {
		this.ten = ten;
	}

	public Intersection getOne() {
		return one;
	}

	public void setOne(Intersection one) {
		this.one = one;
	}

	public Intersection getThree() {
		return three;
	}

	public void setThree(Intersection three) {
		this.three = three;
	}

	public Intersection getFive() {
		return five;
	}

	public void setFive(Intersection five) {
		this.five = five;
	}

	public Intersection getSeven() {
		return seven;
	}

	public void setSeven(Intersection seven) {
		this.seven = seven;
	}

	public Intersection getNine() {
		return nine;
	}

	public void setNine(Intersection nine) {
		this.nine = nine;
	}

	public Intersection getEleven() {
		return eleven;
	}

	public void setEleven(Intersection eleven) {
		this.eleven = eleven;
	}
	
	public int getNumber() {
		if(this.tileNumber == null) {
			return 0;
		}
		
		return this.tileNumber.getNumber();
	}

	public TileNumber getTileNumber() {
		return this.tileNumber;
	}

	public Resource getResource() {
		return resource;
	}

	public String toString() {
		return "____";
	}
	
	public void addRobber() {
		hasRobber = true;
	}
	
	public void removeRobber() {
		hasRobber = false;
	}
	
	public boolean hasRobber() {
		return hasRobber;
	}
	
	public void distributeResources() {
		if(hasRobber) {
			return;
		}
		
		Intersection[] intersections = new Intersection[]{one, three, five, seven, nine, eleven};
		for(Intersection i : intersections) {
			if(i.hasStructure()) {
				Structure s = i.getStructure();
				if(s instanceof Settlement) {
					s.getOwner().addResource(this.getResource());
				} else if(s instanceof City) {
					s.getOwner().addResource(this.getResource());
					s.getOwner().addResource(this.getResource());
				} else {
					// This should never happen
				}
			}
		}
	}
}
