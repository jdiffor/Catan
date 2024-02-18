import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class DevelopmentCardStashGui {
	
	private static final int STASH_WIDTH = 680;
	private static final int DEFAULT_CARD_SEPARATION = 20;
	private static final int CONTAINER_START_X = GameWindow.WINDOW_DIM.width - DEFAULT_CARD_SEPARATION - STASH_WIDTH;
	
	private static final int PLAYED_AREA_X = GameWindow.WINDOW_DIM.width - CardGui.WIDTH - DEFAULT_CARD_SEPARATION;
	private static final int PLAYED_AREA_Y = GameWindow.WINDOW_DIM.height / 2 - CardGui.HEIGHT/2;
	private static final int PLAYED_CARD_SEPARATION = 40;

	private DevelopmentCardStash stash;
	
	public DevelopmentCardStashGui(DevelopmentCardStash stash) {
		this.stash = stash;
	}
	
	public void mouseClicked(Point p) {
		if(!inStashBounds(p) || this.stash.isEmpty()) {
			return;
		}
		
		ArrayList<DevelopmentCard> cards = this.stash.getUnplayedCards();
		int allowedWidth = STASH_WIDTH / cards.size();
		int width = allowedWidth > CardGui.WIDTH + DEFAULT_CARD_SEPARATION ? CardGui.WIDTH + DEFAULT_CARD_SEPARATION : allowedWidth;
		int cardNum = (int) ((p.getX() - CONTAINER_START_X) / width);
		
		if(cardNum >= 0 && cardNum < cards.size()) {
			System.out.println(cards.get(cardNum).getType());
			stash.selectCard(cards.get(cardNum));
		}
	}
	
	public void draw(Graphics2D g) {

		if(!this.stash.isEmpty()) {
			ArrayList<DevelopmentCard> unplayedCards = this.stash.getUnplayedCards();
			int allowedWidth = STASH_WIDTH / unplayedCards.size();
			int x = CONTAINER_START_X;
			
			int width = allowedWidth > CardGui.WIDTH + DEFAULT_CARD_SEPARATION ? CardGui.WIDTH + DEFAULT_CARD_SEPARATION : allowedWidth;
			
			for(int i = 0; i < unplayedCards.size(); i++) {
				if(!unplayedCards.get(i).played()) {
					unplayedCards.get(i).draw(g, x, calculateTopOfCard());
					x += width;
				}
			}
		}
		
		ArrayList<DevelopmentCard> playedCards = this.stash.getPlayedCards();
		int y = PLAYED_AREA_Y;
		
		for(int i = 0; i < playedCards.size(); i++) {
			if(playedCards.get(i).played() && playedCards.get(i).getType() == DevelopmentCardType.Knight) {
				playedCards.get(i).draw(g, PLAYED_AREA_X, y);
				y += PLAYED_CARD_SEPARATION;
			}
		}
		
	}
	
	private int calculateTopOfCard() {
		return GameWindow.WINDOW_DIM.height - DEFAULT_CARD_SEPARATION - CardGui.HEIGHT;
	}
	
	private boolean inStashBounds(Point p) {
		if(p.getX() < CONTAINER_START_X) {
			return false;
		}
		
		if(p.getY() < calculateTopOfCard()) {
			return false;
		}
		
		if(p.getX() > CONTAINER_START_X + STASH_WIDTH) {
			return false;
		}
		
		if(p.getY() > GameWindow.WINDOW_DIM.height - DEFAULT_CARD_SEPARATION) {
			return false;
		}
		
		return true;
	}
	
}
