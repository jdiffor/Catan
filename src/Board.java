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
	
	private HexTile findDesert() {
		for(HexTile t : boardTiles) {
			if(t.getResource() == Resource.Desert) {
				return t;
			}
		}
		return null;
	}
}
