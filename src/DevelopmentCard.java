import java.awt.Color;
import java.awt.Graphics2D;

public class DevelopmentCard extends Card {

	private DevelopmentCardType type;
	private boolean played;
	private int turnAcquired = -1;
	
	public DevelopmentCard(DevelopmentCardType type) {
		this.type = type;
		this.played = false;
	}
	
	public DevelopmentCardType getType() {
		return this.type;
	}
	
	public void drawn() {
		this.turnAcquired = TurnManager.TURN_NUMBER;
	}
	
	public int getTurnAcquired() {
		return this.turnAcquired;
	}
	
	public void play() {
		this.played = true;
	}
	
	public boolean played() {
		return this.played;
	}
	
	public Color getColor() {
		switch(type) {
		case Knight:
			return GameColors.KNIGHT_COLOR;
		case VictoryPoint:
			return GameColors.VICTORY_POINT_COLOR;
		case Monopoly:
			 return GameColors.MONOPOLY_COLOR;
		case YearOfPlenty:
			 return GameColors.YEAR_OF_PLENTY_COLOR;
		case RoadBuilding:
			return GameColors.ROAD_BUILDING_COLOR;
		default:
			return Color.white;
		}
	}
	
	public String getTitle() {
		switch(type) {
		case Knight:
			return "Knight";
		case VictoryPoint:
			return "Victory Point";
		case Monopoly:
			 return "Monopoly";
		case YearOfPlenty:
			 return "Year of Plenty";
		case RoadBuilding:
			return "Road Building";
		default:
			return "";
		}
	}
	
	public void draw(Graphics2D g, int x, int y) {
		gui.draw(g, x, y);
	}
	
}
