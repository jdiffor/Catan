import java.util.ArrayList;

public class DevelopmentCardDeck {

	private ArrayList<DevelopmentCard> cards;
	
	public DevelopmentCardDeck(SetupManager setupManager) {
		this.cards = setupManager.createDevCardDeck();
	}
	
	public DevelopmentCard drawCard() {
		DevelopmentCard card = cards.remove(0);
		card.drawn();
		return card;
	}
	
	public boolean hasCardsLeft() {
		return this.cards.size() > 0;
	}
	
}
