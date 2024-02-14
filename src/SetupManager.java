import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

public class SetupManager {
	
	private ArrayList<Resource> resourcePool;
	private ArrayList<TileNumber> numberPool;
	private ArrayList<Color> colorPool;
	
	public SetupManager() {
		this.colorPool = createColorPool();
	}
	
	public HexTile[][] generateBoard() {
		HexTile[][] board = new HexTile[Rules.BOARD_WIDTH][Rules.BOARD_HEIGHT]; 
		boolean setupCorrectly = true;
		
		do {
			this.resourcePool = createTilePool();
			this.numberPool = createNumberPool();
			setupCorrectly = true;
			for(int i = 0; i < board.length; i++) {
				for(int j = 0; j < board[0].length; j++) {
					if((i + j) % 2 == 0 && !Utils.isCorner(i, j, board)) {
						board[i][j] = new HexTile(this);
						
						if(board[i][j].getNumber() == 6 || board[i][j].getNumber() == 8) {
							if(Utils.isInArrayRange(i-1, j-1, board) && board[i-1][j-1] != null && (board[i-1][j-1].getNumber() == 6 || board[i-1][j-1].getNumber() == 8)) {
								setupCorrectly = false;
							}
							if(Utils.isInArrayRange(i-2, j, board) && board[i-2][j] != null && (board[i-2][j].getNumber() == 6 || board[i-2][j].getNumber() == 8)) {
								setupCorrectly = false;
							}
							if(Utils.isInArrayRange(i-1, j+1, board) && board[i-1][j+1] != null && (board[i-1][j+1].getNumber() == 6 || board[i-1][j+1].getNumber() == 8)) {
								setupCorrectly = false;
							}
						}
						
					}
				}
			}
		} while(!setupCorrectly);
		
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[0].length; j++) {
				HexTile t = board[i][j];
				if(t != null) {
					setPathways(i, j, t, board);
					setIntersections(i, j, t, board);
					addIntersectionsToPathways(i, j, t, board);
					addPathwaysToIntersections(i, j, t, board);
					t.doneWithSetup();
				}
			}
		}
		
		return board;		
	}
	
	private void setPathways(int i, int j, HexTile t, HexTile[][] arr) {
		if(t.getTen() == null) {
			t.setTen(new Pathway());
		}
		if(t.getTwelve() == null) {
			t.setTwelve(new Pathway());
		}
		if(t.getTwo() == null) {
			t.setTwo(new Pathway());
		}
		if(t.getEight() == null) {
			t.setEight(new Pathway());
		}
		if(t.getSix() == null) {
			t.setSix(new Pathway());
		}
		if(t.getFour() == null) {
			t.setFour(new Pathway());
		}
		
		if(Utils.isInArrayRange(i+1, j-1, arr) && arr[i+1][j-1] != null) {
			arr[i+1][j-1].setTwo(t.getEight());
		}
		if(Utils.isInArrayRange(i+2, j, arr) && arr[i+2][j] != null) {
			arr[i+2][j].setTwelve(t.getSix());
		}
		if(Utils.isInArrayRange(i+1, j+1, arr) && arr[i+1][j+1] != null) {
			arr[i+1][j+1].setTen(t.getFour());
		}
	}
	
	private void setIntersections(int i, int j, HexTile t, HexTile[][] arr) {
		if(t.getEleven() == null) {
			t.setEleven(new Intersection());
		}
		if(t.getOne() == null) {
			t.setOne(new Intersection());
		}
		if(t.getThree() == null) {
			t.setThree(new Intersection());
		}
		if(t.getFive() == null) {
			t.setFive(new Intersection());
		}
		if(t.getSeven() == null) {
			t.setSeven(new Intersection());
		}
		if(t.getNine() == null) {
			t.setNine(new Intersection());
		}
		
		if(Utils.isInArrayRange(i+1, j-1, arr) && arr[i+1][j-1] != null) {
			arr[i+1][j-1].setThree(t.getSeven());
			arr[i+1][j-1].setOne(t.getNine());
		}
		if(Utils.isInArrayRange(i+2, j, arr) && arr[i+2][j] != null) {
			arr[i+2][j].setOne(t.getFive());
			arr[i+2][j].setEleven(t.getSeven());
		}
		if(Utils.isInArrayRange(i+1, j+1, arr) && arr[i+1][j+1] != null) {
			arr[i+1][j+1].setEleven(t.getThree());
			arr[i+1][j+1].setNine(t.getFive());
		}
	}
	
	private void addIntersectionsToPathways(int i, int j, HexTile t, HexTile[][] arr) {
		t.getTwelve().addIntersection(t.getEleven());
		t.getTwelve().addIntersection(t.getOne());
		
		t.getTwo().addIntersection(t.getOne());
		t.getTwo().addIntersection(t.getThree());
		
		t.getFour().addIntersection(t.getThree());
		t.getFour().addIntersection(t.getFive());
		
		t.getSix().addIntersection(t.getFive());
		t.getSix().addIntersection(t.getSeven());
		
		t.getEight().addIntersection(t.getSeven());
		t.getEight().addIntersection(t.getNine());
		
		t.getTen().addIntersection(t.getNine());
		t.getTen().addIntersection(t.getEleven());
	}
	
	private void addPathwaysToIntersections(int i, int j, HexTile t, HexTile[][] arr) {
		t.getOne().addPathway(t.getTwelve());
		t.getOne().addPathway(t.getTwo());
		
		t.getThree().addPathway(t.getTwo());
		t.getThree().addPathway(t.getFour());
		
		t.getFive().addPathway(t.getFour());
		t.getFive().addPathway(t.getSix());
		
		t.getSeven().addPathway(t.getSix());
		t.getSeven().addPathway(t.getEight());
		
		t.getNine().addPathway(t.getEight());
		t.getNine().addPathway(t.getTen());
		
		t.getEleven().addPathway(t.getTen());
		t.getEleven().addPathway(t.getTwelve());
	}
	
	public Resource getNextResource() {
		if(this.resourcePool == null || this.resourcePool.size() == 0) {
			System.err.println("NOTHING IN RESOURCE POOL");
		}
		return this.resourcePool.remove(0);
	}
	
	public TileNumber getNextNumber() {
		if(this.numberPool == null || this.numberPool.size() == 0) {
			System.err.println("NOTHING IN NUMBER POOL");
		}
		return this.numberPool.remove(0);
	}
	
	public Color getNextColor() {
		if(this.colorPool == null || this.colorPool.size() == 0) {
			System.err.println("TOO MANY PLAYERS - NOT ENOUGH COLORS");
		}
		return this.colorPool.remove(0);
	}
	
	public ArrayList<DevelopmentCard> createDevCardDeck() {
		ArrayList<DevelopmentCard> devCards = new ArrayList<DevelopmentCard>();
		Utils.addAmountOfItemToList(new DevelopmentCard(DevelopmentCardType.Knight), 14, devCards);
		Utils.addAmountOfItemToList(new DevelopmentCard(DevelopmentCardType.RoadBuilding), 14, devCards);
		Utils.addAmountOfItemToList(new DevelopmentCard(DevelopmentCardType.YearOfPlenty), 2, devCards);
		Utils.addAmountOfItemToList(new DevelopmentCard(DevelopmentCardType.Monopoly), 2, devCards);
		Utils.addAmountOfItemToList(new DevelopmentCard(DevelopmentCardType.VictoryPoint), 5, devCards);
		return devCards;
	}
	
	private ArrayList<Resource> createTilePool() {
		ArrayList<Resource> resources = new ArrayList<Resource>();
		Utils.addAmountOfItemToList(Resource.Bricks, 3, resources);
		Utils.addAmountOfItemToList(Resource.Ore, 3, resources);
		Utils.addAmountOfItemToList(Resource.Sheep, 4, resources);
		Utils.addAmountOfItemToList(Resource.Wheat, 4, resources);
		Utils.addAmountOfItemToList(Resource.Wood, 4, resources);
		Utils.addAmountOfItemToList(Resource.Desert, 1, resources);
		Collections.shuffle(resources);
		return resources;
	}
	
	private ArrayList<TileNumber> createNumberPool() {
		ArrayList<TileNumber> numbers = new ArrayList<TileNumber>();
		for(int i = 2; i <= 12; i++) {
			if(i != 7) {
				if(i != 2 && i != 12) {
					numbers.add(new TileNumber(i));
				}
				numbers.add(new TileNumber(i));
			}
		}
		Collections.shuffle(numbers);
		return numbers;
	}
	
	public ArrayList<Color> createColorPool() {
		ArrayList<Color> colors = new ArrayList<Color>();
		colors.add(Color.blue);
		colors.add(Color.red);
		colors.add(Color.yellow);
		colors.add(Color.green);
		return colors;
	}
	
}
