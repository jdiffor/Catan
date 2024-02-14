import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

public class DiceGui {
	
	private static final int DIE_SIZE = 100;
	private static final int DIE_ROUNDING = 20;
	private static final int DIE_GAP = 10;
	private static final int DIE_X = DIE_GAP;
	private static final int DIE_Y = DIE_GAP;
	private final static Font DIE_FONT = new Font("Algerian", Font.BOLD, 48);
	
	private Dice dice;

	public DiceGui(Dice dice) {
		this.dice = dice;
	}
	
	public void draw(Graphics2D g) {
		g.setStroke(new BasicStroke(3));
		
		// Die 1 cube
		g.setColor(GameColors.DICE_RED);
		g.fillRoundRect(DIE_X, DIE_Y, DIE_SIZE, DIE_SIZE, DIE_ROUNDING, DIE_ROUNDING);
		g.setColor(Color.black);
		g.drawRoundRect(DIE_X, DIE_Y, DIE_SIZE, DIE_SIZE, DIE_ROUNDING, DIE_ROUNDING);
		
		// Die 1 number
		g.setColor(GameColors.DICE_YELLOW);
		Utils.drawCenteredString(g, dice.getD1() + "", new Point(DIE_X + (DIE_SIZE / 2), DIE_Y + (DIE_SIZE / 2)), DIE_FONT);
		
		// Die 2 cube
		g.setColor(GameColors.DICE_YELLOW);
		g.fillRoundRect(DIE_X + DIE_SIZE + DIE_GAP, DIE_Y, DIE_SIZE, DIE_SIZE, DIE_ROUNDING, DIE_ROUNDING);
		g.setColor(Color.black);
		g.drawRoundRect(DIE_X + DIE_SIZE + DIE_GAP, DIE_Y, DIE_SIZE, DIE_SIZE, DIE_ROUNDING, DIE_ROUNDING);
		
		// Die 2 number
		g.setColor(GameColors.DICE_RED);
		Utils.drawCenteredString(g, dice.getD2() + "", new Point(DIE_X + DIE_SIZE + DIE_GAP + (DIE_SIZE / 2), DIE_Y + (DIE_SIZE / 2)), DIE_FONT);
	}
	
}
