import java.util.ArrayList;

public class DevelopmentCardStash {

	private ArrayList<DevelopmentCard> cards;
	private DevelopmentCardStashGui devCardStashGui;
	
	private static int largestArmySize = 2;
	private static Player largestArmyOwner = null;
	
	public DevelopmentCardStash() {
		cards = new ArrayList<DevelopmentCard>();
		devCardStashGui = new DevelopmentCardStashGui(this);
	}
	
	public ArrayList<DevelopmentCard> getCards() {
		return this.cards;
	}
	
	public void addCard(DevelopmentCard card) {
		this.cards.add(card);
	}
	
	public boolean hasUnplayedDevCard() {
		for(DevelopmentCard card : cards) {
			if(!card.played()) {
				return true;
			}
		}
		return false;
	}
	
	public int getPoints(Player player) {
		int points = 0;
		int playedKnightCount = 0;
		for(DevelopmentCard card : cards) {
			if(card.getType() == DevelopmentCardType.VictoryPoint) {
				points++;
			} else if(card.getType() == DevelopmentCardType.Knight && card.played()) {
				playedKnightCount++;
			}
		}
		
		if(playedKnightCount > largestArmySize) {
			largestArmySize = playedKnightCount;
			largestArmyOwner = player;
		}
		
		if(largestArmyOwner == player) {
			points += 2;
		}
		
		return points;
	}
	
}
