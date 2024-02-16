import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class Board {

	private ArrayList<HexTile> boardTiles;
	private Robber robber;
	private DevelopmentCardDeck devCardDeck;
	
	private BoardGui boardGui;
	private StateManager stateManager;
	
	
	public Board(SetupManager setupManager, StateManager stateManager) {
		this.stateManager = stateManager;
		this.devCardDeck = new DevelopmentCardDeck(setupManager);
				
		HexTile[][] boardTileArray = setupManager.generateBoard();
		this.boardGui = new BoardGui(boardTileArray, stateManager);
		this.boardTiles = Utils.array2DToList(boardTileArray, true);
		this.robber = new Robber(findDesert());
	}
	
	public void draw(Graphics2D g) {
		this.boardGui.draw(g);
	}
	
	public boolean mouseMoved(Point p, Player player) {
		return this.boardGui.mouseMoved(p, player);
	}
	
	public BoardClickEvent mouseClicked(Point p) {
		return this.boardGui.mouseClicked(p);
	}
	
	public void clearHover() {
		this.boardGui.clearHover();
	}
	
	public boolean canMoveRobberHere(HexTile tile) {
		return this.robber.getCurrentTile() != tile;
	}
	
	public ArrayList<Player> moveRobberHere(HexTile tile) {
		this.robber.getCurrentTile().removeRobber();
		tile.addRobber();
		robber.moveTo(tile);
		
		ArrayList<Player> playersOnRobbedTile = new ArrayList<Player>();
		for(Intersection i : tile.getIntersections()) {
			if(i.hasStructure()) {
				playersOnRobbedTile.add(i.getStructure().getOwner());
			}
		}
		return playersOnRobbedTile;
	}
	
	public DevelopmentCardDeck getDevCardDeck() {
		return this.devCardDeck;
	}
	
	public ArrayList<HexTile> getBoardTiles() {
		return this.boardTiles;
	}
	
	public void distributeResources(int diceRoll) {
		for(HexTile t : boardTiles) {
			if(t.getNumber() == diceRoll) {
				t.distributeResources();
			}
		}
	}
	
	public boolean canBuildSettlementSomewhere(Player player ) {
		for(HexTile tile : boardTiles) {
			for(Intersection intersection : tile.getIntersections()) {
				if(intersection.validForSettlement(player)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean canBuildCitySomewhere(Player player ) {
		for(HexTile tile : boardTiles) {
			for(Intersection intersection : tile.getIntersections()) {
				if(intersection.validForCity(player)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean canBuildRoadSomewhere(Player player ) {
		for(HexTile tile : boardTiles) {
			for(Intersection intersection : tile.getIntersections()) {
				for(Pathway pathway : intersection.getPathways()) {
					if(pathway.validForRoad(player)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private HexTile findDesert() {
		for(HexTile t : boardTiles) {
			if(t.getResource() == Resource.Desert) {
				return t;
			}
		}
		return null;
	}
}
