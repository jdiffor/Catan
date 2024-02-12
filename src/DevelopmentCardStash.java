import java.util.ArrayList;

public class DevelopmentCardStash {

	private ArrayList<DevelopmentCard> cards;
	private HandGui handGui;
	
	public DevelopmentCardStash() {
		cards = new ArrayList<DevelopmentCard>();
		
		cards.add(new DevelopmentCard(DevelopmentCardType.Knight));
		cards.add(new DevelopmentCard(DevelopmentCardType.Monopoly));
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
