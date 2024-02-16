import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

public class PlayerGui {
	
	private static final int WIDTH = 400;
	private static final int HEIGHT = 70;
	private static final int COLOR_BOX_WIDTH = HEIGHT;
	private static final int GAP = 10;
	private static final int HIGHLIGHT_BORDER_OUTER = 5;
	private static final int HIGHLIGHT_BORDER_INNER = 3;
	private static final Font NAME_FONT = new Font("Algerian", Font.BOLD, 16);
	
	private static final int TRADE_BUTTON_WIDTH = 80;
	private static final int TRADE_BUTTON_HEIGHT = (int) (HEIGHT * 0.8);
	private static final int TRADE_BUTTON_RIGHT_BORDER = 5;

	private Player player;
	private int playerNum;
	private ActionButton tradeButton;
	private ActionButton stealButton;
	
	public PlayerGui(Player player, int playerNum) {
		this.player = player;
		this.tradeButton = new ActionButton("Trade", Action.Trade, new Point((int) (GameWindow.WINDOW_DIM.getWidth() - TRADE_BUTTON_WIDTH/2 - TRADE_BUTTON_RIGHT_BORDER), (int) GAP + (HEIGHT+GAP)*playerNum + HEIGHT/2), TRADE_BUTTON_WIDTH, TRADE_BUTTON_HEIGHT);
		this.stealButton = new ActionButton("Steal", Action.Steal, new Point((int) (GameWindow.WINDOW_DIM.getWidth() - TRADE_BUTTON_WIDTH/2 - TRADE_BUTTON_RIGHT_BORDER), (int) GAP + (HEIGHT+GAP)*playerNum + HEIGHT/2), TRADE_BUTTON_WIDTH, TRADE_BUTTON_HEIGHT);
		this.playerNum = playerNum;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public void draw(Graphics2D g, boolean myTurn) {
		
		// Highlighted if turn
		if(myTurn) {
			g.setColor(Color.black);
			g.fillRect((int) (GameWindow.WINDOW_DIM.getWidth() - WIDTH) - HIGHLIGHT_BORDER_OUTER, GAP + (HEIGHT+GAP)*playerNum - HIGHLIGHT_BORDER_OUTER, WIDTH+HIGHLIGHT_BORDER_OUTER*2, HEIGHT+HIGHLIGHT_BORDER_OUTER*2);
			
			g.setColor(Color.white);
			g.fillRect((int) (GameWindow.WINDOW_DIM.getWidth() - WIDTH) - HIGHLIGHT_BORDER_INNER, GAP + (HEIGHT+GAP)*playerNum - HIGHLIGHT_BORDER_INNER, WIDTH+HIGHLIGHT_BORDER_INNER*2, HEIGHT+HIGHLIGHT_BORDER_INNER*2);	
		} else {
			g.setColor(Color.black);
			g.fillRect((int) (GameWindow.WINDOW_DIM.getWidth() - WIDTH) - HIGHLIGHT_BORDER_INNER, GAP + (HEIGHT+GAP)*playerNum - HIGHLIGHT_BORDER_INNER, WIDTH+HIGHLIGHT_BORDER_INNER*2, HEIGHT+HIGHLIGHT_BORDER_INNER*2);
		}
		
		// Background
		g.setColor(GameColors.SAND_OUTLINE_COLOR);
		g.fillRect((int) (GameWindow.WINDOW_DIM.getWidth() - WIDTH), GAP + (HEIGHT+GAP)*playerNum, WIDTH, HEIGHT);
		
		// Player color as banner		
		g.setColor(player.getPieceColor());
		g.fillRect((int) (GameWindow.WINDOW_DIM.getWidth() - WIDTH), GAP + (HEIGHT+GAP)*playerNum, COLOR_BOX_WIDTH, HEIGHT);
		
		// Player name
		g.setColor(Color.black);
		Utils.drawCenteredHeightString(g, player.getName() + "  -  " + player.getHand().size() + " cards", new Point((int) (GameWindow.WINDOW_DIM.getWidth() - WIDTH + COLOR_BOX_WIDTH + GAP), GAP + (HEIGHT+GAP)*playerNum + HEIGHT/2), NAME_FONT);
		
		// Player score
		int score = player.getScore();
		Utils.drawCenteredString(g, score + "", new Point((int) (GameWindow.WINDOW_DIM.getWidth() - WIDTH + COLOR_BOX_WIDTH/2), GAP + (HEIGHT+GAP)*playerNum + HEIGHT/2), NAME_FONT);
		
		// Player number of cards
		//Utils.drawCenteredHeightString(g, player.getHand().size() + " cards", new Point((int) (GameWindow.WINDOW_DIM.getWidth() - WIDTH + COLOR_BOX_WIDTH + GAP), GAP + (HEIGHT+GAP)*playerNum + HEIGHT/2), NAME_FONT);
		
		// Buttons
		this.tradeButton.draw(g);
		this.stealButton.draw(g);
	}
	
	public boolean buttonClicked(Point p) {
		return this.tradeButtonClicked(p) || this.stealButtonClicked(p);
	}
	
	public boolean tradeButtonClicked(Point p) {
		return this.tradeButton.clicked(p);
	}
	
	public boolean stealButtonClicked(Point p) {
		return this.stealButton.clicked(p);
	}
	
	public void setTradeButtonAllowed(boolean allowed) {
		this.tradeButton.setActive(allowed);
		this.tradeButton.setHidden(!allowed);
	}
	
	public void setStealButtonAllowed(boolean allowed) {
		this.stealButton.setActive(allowed);
		this.stealButton.setHidden(!allowed);
	}
	
}
