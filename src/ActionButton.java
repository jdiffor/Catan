import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

public class ActionButton {
	
	public static final int BUTTON_WIDTH = 200;
	public static final int BUTTON_HEIGHT = 100;
	public static final Font BUTTON_FONT = new Font("Algerian", Font.BOLD, 16);
	
	private String title;
	private Action action;
	private Point center;
	private boolean active;
	private boolean hidden;

	public ActionButton(String title, Action action, Point center) {
		this.title = title;
		this.action = action;
		this.center = center;
		if(Math.random() > 0) {
			this.active = true;
		}
	}
	
	public Action getAction() {
		return this.action;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	
	public boolean clicked(Point p) {
		return this.active && !this.hidden && inRange(p);
	}
	
	public boolean inRange(Point p) {
		return p.getX() >= center.getX() - BUTTON_WIDTH / 2 &&
				p.getY() >= center.getY() - BUTTON_HEIGHT / 2 &&
				p.getX() <= center.getX() + BUTTON_WIDTH / 2 &&
				p.getY() < center.getY() + BUTTON_HEIGHT / 2;
	}
	
	
	public void draw(Graphics2D g) {
		if(this.hidden) {
			return;
		}
		
		if(this.active) {
			g.setColor(GameColors.BUTTON_ACITVE_COLOR);
		} else {
			g.setColor(GameColors.BUTTON_INACITVE_COLOR);
		}
		g.fillRoundRect((int) (center.getX() - BUTTON_WIDTH/2), (int) (center.getY() - BUTTON_HEIGHT/2), BUTTON_WIDTH, BUTTON_HEIGHT, 0, 0);
		
		if(this.active) {
			g.setColor(Color.black);
		} else {
			g.setColor(Utils.averageColor(Color.black, GameColors.TABLE_COLOR));
		}
		
		g.setStroke(new BasicStroke(1));
		g.drawRoundRect((int) (center.getX() - BUTTON_WIDTH/2), (int) (center.getY() - BUTTON_HEIGHT/2), BUTTON_WIDTH, BUTTON_HEIGHT, 0, 0);

		Utils.drawCenteredString(g, title, center, BUTTON_FONT);
		
	}
}
