import java.util.ArrayList;

public class DevelopmentCardStash {

	private ArrayList<DevelopmentCard> cards;
	private DevelopmentCardStashGui devCardStashGui;
	
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
			if(card.upPlayed()) {
				return true;
			}
		}
		return false;
	}
	
}
