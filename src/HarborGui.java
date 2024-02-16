
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Path2D;

public class HarborGui {
	
	private static final double SHIP_DISTANCE = 40;
	private static final double OUTLET_LENGTH = 30;
	private static final int CIRCLE_SIZE = 40;
	public final static Font HARBOR_FONT = new Font("Algerian", Font.BOLD, 16);

	Harbor harbor;
	
	public HarborGui(Harbor harbor) {
		this.harbor = harbor;
	}
	
	public void draw(Graphics2D g, Point start, Point end) {
		
		
		double rise = end.getY() - start.getY();
		double run = end.getX() - start.getX();
		double length = Math.sqrt(rise * rise + run * run);
		Point center = Utils.averagePoint(start, end);
		
		Point shipCenterPoint = new Point((int) ((SHIP_DISTANCE * rise/length) + center.getX()), (int) ((SHIP_DISTANCE * -run/length) + center.getY()));
		
		Point startCenter = Utils.averagePoint(start, center);
		Point endCenter = Utils.averagePoint(end, center);
		
		Point midStartCenter = Utils.averagePoint(start, startCenter);
		Point midEndCenter = Utils.averagePoint(end, endCenter);
		
		Point startOutPoint = new Point((int) ((OUTLET_LENGTH * rise/length) + midStartCenter.getX()), (int) ((OUTLET_LENGTH * -run/length) + midStartCenter.getY()));
		Point endOutPoint = new Point((int) ((OUTLET_LENGTH * rise/length) + midEndCenter.getX()), (int) ((OUTLET_LENGTH * -run/length) + midEndCenter.getY()));
		
		Point startCenterOutPoint = new Point((int) ((OUTLET_LENGTH * rise/length) + startCenter.getX()), (int) ((OUTLET_LENGTH * -run/length) + startCenter.getY()));
		Point endCenterOutPoint = new Point((int) ((OUTLET_LENGTH * rise/length) + endCenter.getX()), (int) ((OUTLET_LENGTH * -run/length) + endCenter.getY()));
		
		Path2D.Double startPath = new Path2D.Double();
		startPath.moveTo(start.getX(), start.getY());
		startPath.lineTo(startOutPoint.getX(), startOutPoint.getY());
		startPath.lineTo(startCenterOutPoint.getX(), startCenterOutPoint.getY());
		startPath.lineTo(startCenter.getX(), startCenter.getY());
		startPath.closePath();
		
		Path2D.Double endPath = new Path2D.Double();
		endPath.moveTo(end.getX(), end.getY());
		endPath.lineTo(endOutPoint.getX(), endOutPoint.getY());
		endPath.lineTo(endCenterOutPoint.getX(), endCenterOutPoint.getY());
		endPath.lineTo(endCenter.getX(), endCenter.getY());
		endPath.closePath();
		
		g.setColor(GameColors.SAND_OUTLINE_COLOR);
		g.fill(startPath);
		g.fill(endPath);
		
		String tradeDescription = "2:1";
		Color textColor = Color.black;
		
		switch(harbor.getType()) {
		case ThreeForOne:
			g.setColor(Color.WHITE);
			tradeDescription = "3:1";
			break;
		case TwoForOneBricks:
			g.setColor(GameColors.BRICK_COLOR);
			textColor = Color.WHITE;
			break;
		case TwoForOneOre:
			g.setColor(GameColors.ORE_COLOR);
			textColor = Color.WHITE;
			break;
		case TwoForOneSheep:
			g.setColor(GameColors.SHEEP_COLOR);
			break;
		case TwoForOneWood:
			g.setColor(GameColors.WOOD_COLOR);
			textColor = Color.WHITE;
			break;
		case TwoForOneWheat:
			g.setColor(GameColors.WHEAT_COLOR);
			break;
		}
		
		Utils.fillCenteredOval(g, shipCenterPoint, CIRCLE_SIZE, CIRCLE_SIZE);
		g.setColor(textColor);
		Utils.drawCenteredString(g, tradeDescription, shipCenterPoint, HARBOR_FONT);
		
	}
	
}
