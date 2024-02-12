import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class ResourceCardGui {

	public static final int WIDTH = 144;
	public static final int HEIGHT = 200;
	
	private static final int SELECTED_HEIGHT = 40;
	
	private ResourceCard card;
	
	public ResourceCardGui(ResourceCard card) {
		this.card = card;
	}
	
	public void draw(Graphics2D g, int x, int y) {
		if(card.isSelected()) {
			y -= SELECTED_HEIGHT;
		}
		
		g.setColor(card.getResource().getColor());
		g.fillRoundRect(x, y, WIDTH, HEIGHT, 20, 20);
		
		g.setColor(GameColors.CARD_BORDER_COLOR);
		g.setStroke(new BasicStroke(6));
		g.drawRoundRect(x, y, WIDTH, HEIGHT, 20, 20);
		
		g.setColor(Color.black);
		g.setStroke(new BasicStroke(2));
		g.drawRoundRect(x, y, WIDTH, HEIGHT, 20, 20);
	}
	
}
