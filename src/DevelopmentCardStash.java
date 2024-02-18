import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class DevelopmentCardStash {

	private ArrayList<DevelopmentCard> unplayedCards;
	private ArrayList<DevelopmentCard> playedCards;
	private DevelopmentCardStashGui devCardStashGui;
	
	public DevelopmentCardStash() {
		unplayedCards = new ArrayList<DevelopmentCard>();
		playedCards = new ArrayList<DevelopmentCard>();
		devCardStashGui = new DevelopmentCardStashGui(this);
		
		unplayedCards.add(new DevelopmentCard(DevelopmentCardType.Knight));
	}
	
	public void mouseClicked(Point p) {
		this.devCardStashGui.mouseClicked(p);
	}
	
	public void draw(Graphics2D g) {
		devCardStashGui.draw(g);
	}
	
	public DevelopmentCardType playSelectedCard() {
		for(DevelopmentCard card : unplayedCards) {
			if(card.isSelected()) {
				unplayedCards.remove(card);
				playedCards.add(card);
				card.play();
				return card.getType();
			}
		}
		return null;
	}
	
	public ArrayList<DevelopmentCard> getUnplayedCards() {
		return this.unplayedCards;
	}
	
	public ArrayList<DevelopmentCard> getPlayedCards() {
		return this.playedCards;
	}
	
	public void addCard(DevelopmentCard card) {
		this.unplayedCards.add(card);
	}
	
	public void clearSelection() {
		for(DevelopmentCard card : unplayedCards) {
			card.unSelect();
		}
	}
	
	public void selectCard(DevelopmentCard c) {
		if(c == null) {
			return;
		}
		
		for(DevelopmentCard card : unplayedCards) {
			if(card == c) {
				card.toggleSelect();
			} else {
				card.unSelect();
			}
		}
	}
	
	public boolean hasPlayableDevCardSelected() {
		for(DevelopmentCard card : unplayedCards) {
			if(card.getTurnAcquired() != TurnManager.TURN_NUMBER && !card.played() && card.isSelected() && card.getType() != DevelopmentCardType.VictoryPoint) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isEmpty() {
		return this.unplayedCards.isEmpty();
	}
	
	public int getPointsPrivate(Player player, SpecialPoints specialPoints) {
		return getPoints(player, specialPoints, false);
	}
	
	public int getPointsPublic(Player player, SpecialPoints specialPoints) {
		return getPoints(player, specialPoints, true);
	}
	
	private int getPoints(Player player, SpecialPoints specialPoints, boolean isPublic) {
		int points = 0;
		int playedKnightCount = 0;
		
		if(!isPublic) {
			for(DevelopmentCard card : unplayedCards) {
				if(card.getType() == DevelopmentCardType.VictoryPoint) {
					points++;
				}
			}
		}
		
		for(DevelopmentCard card : playedCards) {
			if(card.getType() == DevelopmentCardType.Knight && card.played()) {
				playedKnightCount++;
			}
		}
		
		if(playedKnightCount > specialPoints.getLargestArmySize()) {
			specialPoints.setLargestArmy(playedKnightCount, player);
		}
		
		if(specialPoints.holdsLargestArmy(player)) {
			points += 2;
		}
		
		return points;
	}
	
}
