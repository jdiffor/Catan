import java.awt.Graphics2D;
import java.awt.Point;

public class Board {

	private HexTile[][] boardTiles;
	private Robber robber;
	private DevelopmentCardDeck devCardDeck;
	
	private BoardGui boardGui;
	private StateManager stateManager;
	
	
	public Board(SetupManager setupManager, StateManager stateManager) {
		this.stateManager = stateManager;
		this.boardTiles = setupManager.generateBoard();
		this.robber = new Robber(findDesert()); 
		this.devCardDeck = new DevelopmentCardDeck(setupManager);
				
		Utils.printArray2D(this.boardTiles);
		this.boardGui = new BoardGui(this.boardTiles, stateManager);
	}
	
	public void draw(Graphics2D g) {
		this.boardGui.draw(g);
	}
	
	public boolean mouseMoved(Point p) {
		return this.boardGui.mouseMoved(p);
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
	
	public void moveRobberHere(HexTile tile) {
		this.robber.getCurrentTile().removeRobber();
		tile.addRobber();
		robber.moveTo(tile);
	}
	
	public DevelopmentCardDeck getDevCardDeck() {
		return this.devCardDeck;
	}
	
	public void distributeResources(int diceRoll) {
		for(int i = 0; i < boardTiles.length; i++) {
			for(int j = 0; j < boardTiles[0].length; j++) {
				HexTile t = boardTiles[i][j];
				if(t != null && t.getNumber() == diceRoll) {
					t.distributeResources();
				}
			}
		}
	}
	
	private HexTile findDesert() {
		for(int i = 0; i < boardTiles.length; i++) {
			for(int j = 0; j < boardTiles[0].length; j++) {
				HexTile t = boardTiles[i][j];
				if(t != null && t.getResource() == Resource.Desert) {
					return t;
				}
			}
		}
		return null;
	}
}
