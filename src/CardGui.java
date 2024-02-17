import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class CardGui {

	public static final int WIDTH = 144;
	public static final int HEIGHT = 200;
	
	private static final int SELECTED_HEIGHT = 40;
	private static final int TEXT_OFFSET_HEIGHT = 15;
	
	
	private Card card;
	
	public CardGui(Card card) {
		this.card = card;
	}
	
	public void draw(Graphics2D g, int x, int y) {
		if(card.isSelected()) {
			y -= SELECTED_HEIGHT;
		}
		
		if(card instanceof ResourceCard) {
			g.setColor(((ResourceCard) card).getResource().getColor());
		} else if(card instanceof DevelopmentCard) {
			g.setColor(((DevelopmentCard) card).getColor());
		} else {
			g.setColor(card.getColor());
		}
		
		g.fillRoundRect(x, y, WIDTH, HEIGHT, 20, 20);
		
		g.setColor(GameColors.CARD_BORDER_COLOR);
		g.setStroke(new BasicStroke(6));
		g.drawRoundRect(x, y, WIDTH, HEIGHT, 20, 20);
		
		g.setColor(Color.black);
		g.setStroke(new BasicStroke(2));
		g.drawRoundRect(x, y, WIDTH, HEIGHT, 20, 20);
		
		if(card instanceof DevelopmentCard) {
			if(((DevelopmentCard) card).getType() == DevelopmentCardType.VictoryPoint) {
				g.setColor(Color.black);
			} else {
				g.setColor(GameColors.TILE_NUMBER_COLOR);
			}
			
			Utils.drawCenteredString(g, ((DevelopmentCard) card).getTitle(), new Point(x + WIDTH/2, y + TEXT_OFFSET_HEIGHT), Utils.fontOfSize(16));
		}
	}
	
}
