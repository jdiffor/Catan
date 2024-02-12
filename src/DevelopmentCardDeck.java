import java.util.ArrayList;

public class DevelopmentCardDeck {

	private ArrayList<DevelopmentCard> cards;
	
	public DevelopmentCardDeck(SetupManager setupManager) {
		this.cards = setupManager.createDevCardDeck();
	}
	
	public DevelopmentCard drawCard() {
		return this.cards.remove(0);
	}
	
	public boolean hasCardsLeft() {
		return this.cards.size() > 0;
	}
	
}
