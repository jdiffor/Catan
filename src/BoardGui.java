import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class BoardGui {
	
	public static final int BASE_SIZE = (int) (180 * GamePanel.SCALE);
	private static final int TILE_WIDTH = BASE_SIZE;
	private static final int TILE_HEIGHT = (int) (TILE_WIDTH * HexTileGui.RATIO);
	
	private int screenWidth = 1920;
	private int screenHeight = 1080;
	
	private StateManager state;
	private HexTileGui background;
	
	private ArrayList<HexTileGui> boardGui;
	private ArrayList<IntersectionGui> intersections;
	private ArrayList<PathwayGui> pathways;
	
	private HexTileGui hoverTile;
	private IntersectionGui hoverIntersection;
	private PathwayGui hoverPathway;
	
	
	
	public BoardGui(HexTile[][] board, StateManager state) {
		this.state = state;
		background = new HexTileGui(screenWidth / 2, screenHeight / 2, (int) (TILE_WIDTH * 5.5), new HexTile(Resource.Water));
		generateBoardGui(board);
	}
	
	private void generateBoardGui(HexTile[][] board) {
		generateHexTilesGui(board);
	}
	
	private void generateHexTilesGui(HexTile[][] board) {
		boardGui = new ArrayList<HexTileGui>();
		intersections = new ArrayList<IntersectionGui>();
		ArrayList<Intersection> intersectionsAccountedFor = new ArrayList<Intersection>();
		pathways = new ArrayList<PathwayGui>();
		ArrayList<Pathway> pathwaysAccountedFor = new ArrayList<Pathway>();
		
		int drawY = (int) (screenHeight/2 - 2*TILE_HEIGHT);
		int centerX = screenWidth / 2;
		
		for(int i = 0; i < board.length; i++) {
			int count = 0;
			for(int j = 0; j < board[0].length; j++) {
				if(board[i][j] != null) {
					count++;
				}
			}
			
			int drawX = (int) (centerX - ((count-1) * TILE_WIDTH*HexTileGui.RATIO*HexTileGui.RATIO));
			for(int j = 0; j < board[0].length; j++) {
				if(board[i][j] != null) {
					HexTileGui tile = new HexTileGui(drawX, drawY, TILE_WIDTH, board[i][j]);
					
					tile.createIntersectionGuis(intersections, intersectionsAccountedFor);
					tile.createPathwayGuis(pathways, pathwaysAccountedFor);
					
					boardGui.add(tile);
					drawX += (TILE_WIDTH*2*HexTileGui.RATIO*HexTileGui.RATIO);
				}
				
			}
			drawY += TILE_HEIGHT/2;
		}
	}

	public void draw(Graphics2D g) {
		AffineTransform af = g.getTransform();
		g.rotate(Math.toRadians(30), screenWidth/2, screenHeight/2);
		background.draw(g);
		g.setTransform(af);
		
		for(HexTileGui tile : boardGui) {
			tile.draw(g);
		}
		if(hoverTile != null) {
			hoverTile.draw(g);
		}
		
		for(PathwayGui pathway : pathways) {
			pathway.draw(g);
		}
		if(hoverPathway != null) {
			hoverPathway.draw(g);
		}
		
		for(IntersectionGui intersection : intersections) {
			intersection.draw(g);
		}
		if(hoverIntersection != null) {
			hoverIntersection.draw(g);
		}
	}
	
	/*
	 * Returns true if we should repaint
	 */
	public boolean mouseMoved(Point p) {
		switch(state.getActionState()) {
			case BuildingInitialRoad:
			case BuildingRoad:
				return highlightHoveredPathway(p);
			case MovingRobber:
				return highlightHoveredTile(p);
			case BuildingRegularSettlement:
			case BuildingInitialSettlement:
			case BuildingCity:
				return highlightHoveredIntersection(p);
			case YourTurn:
			default:
				return false;
		}
	}
	
	/*
	 * Returns true if we should repaint
	 */
	public BoardClickEvent mouseClicked(Point p) {
		switch(state.getActionState()) {
			case BuildingInitialRoad:
			case BuildingRoad:
				return new BoardClickEvent(clickPathway(p));
			case MovingRobber:
				return new BoardClickEvent(clickTile(p));
			case BuildingRegularSettlement:
			case BuildingInitialSettlement:
			case BuildingCity:
				return new BoardClickEvent(clickIntersection(p));
			case YourTurn:
				return new BoardClickEvent();
			default:
				return new BoardClickEvent();
		}
	}
	
	public void clearHover() {
		if(this.hoverIntersection != null) {
			this.hoverIntersection.removeHover();
			this.hoverIntersection = null;
		}
		
		if(this.hoverPathway != null) {
			this.hoverPathway.removeHover();
			this.hoverPathway = null;
		}
		
		if(this.hoverTile != null) {
			this.hoverTile.removeHover();
			this.hoverTile = null;
		}
	}
	
	private boolean highlightHoveredTile(Point p) {
		double shortestDist = Double.MAX_VALUE;
		HexTileGui closestTile = null;
		for(HexTileGui tile : boardGui) {
			double distance = tile.getDistance(p);
			if(distance < shortestDist) {
				shortestDist = distance;
				closestTile = tile;
			}
		}
		
		if(shortestDist > TILE_WIDTH / 2) {
			if(hoverTile != null) {
				hoverTile.removeHover();
			}
			hoverTile = null;
			return true;
		}
		
		if(closestTile != null && closestTile != hoverTile) {
			if(hoverTile != null) {
				hoverTile.removeHover();
			}
			closestTile.addHover();
			hoverTile = closestTile;
			return true;
		}
		return false;
	}
	
	private boolean highlightHoveredIntersection(Point p) {
		for(IntersectionGui intersection : intersections) {
			if(intersection.inRange(p)) {
				if(hoverIntersection != null) {
					hoverIntersection.removeHover();	
				}
				intersection.addHover();
				hoverIntersection = intersection;
				return true;
			}
		}
		if(hoverIntersection != null) {
			hoverIntersection.removeHover();
			hoverIntersection = null;
			return true;
		}
		return false;
	}
	
	private boolean highlightHoveredPathway(Point p) {
		double shortestDist = Double.MAX_VALUE;
		PathwayGui closestPathway = null;
		for(PathwayGui path : pathways) {
			double distance = path.getDistance(p);
			if(distance < shortestDist) {
				shortestDist = distance;
				closestPathway = path;
			}
		}
		
		if(shortestDist > TILE_WIDTH / 4) {
			if(hoverPathway != null) {
				hoverPathway.removeHover();
			}
			hoverPathway = null;
			return true;
		}
		
		if(closestPathway != null && closestPathway != hoverPathway) {
			if(hoverPathway != null) {
				hoverPathway.removeHover();
			}
			closestPathway.addHover();
			hoverPathway = closestPathway;
			return true;
		}
		return false;
	}
	
	private HexTile clickTile(Point p) {
		if(hoverTile != null) {
			System.out.println(hoverTile.getResource());
			return hoverTile.getHexTile();
		}
		return null;
	}
	
	private Intersection clickIntersection(Point p) {
		if(hoverIntersection != null) {
			System.out.println(hoverIntersection);
			return hoverIntersection.getIntersection();
		}
		return null;
	}
	
	private Pathway clickPathway(Point p) {
		if(hoverPathway != null) {
			System.out.println(hoverPathway);
			return hoverPathway.getPathway();
		}
		return null;
	}
	
}
