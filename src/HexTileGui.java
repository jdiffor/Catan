import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public class HexTileGui {

	public static final double RATIO = 0.8660254;
	
	
	private int boundingWidth;
	private int boundingHeight;
	private Point center;
	private Color color;
	private HexTile tile;
	private TileNumberGui number;
	private boolean hover;
	
	public HexTileGui(int centerX, int centerY, int size, HexTile tile) {
		center = new Point(centerX, centerY);
		this.boundingWidth = size;
		this.boundingHeight = (int) (size * RATIO);
		this.tile = tile;
		this.color = tile.getResource().getColor();
		this.number = new TileNumberGui(tile.getTileNumber());
	}
	
	public void createIntersectionGuis(ArrayList<IntersectionGui> intersectionGuis, ArrayList<Intersection> intersectionsAccountedFor) {
		if(!intersectionsAccountedFor.contains(tile.getOne())) {
			intersectionsAccountedFor.add(tile.getOne());
			intersectionGuis.add(new IntersectionGui(topRight(), tile.getOne()));
		}
	
		if(!intersectionsAccountedFor.contains(tile.getThree())) {
			intersectionsAccountedFor.add(tile.getThree());
			intersectionGuis.add(new IntersectionGui(midRight(), tile.getThree()));
		}
		
		if(!intersectionsAccountedFor.contains(tile.getFive())) {
			intersectionsAccountedFor.add(tile.getFive());
			intersectionGuis.add(new IntersectionGui(bottomRight(), tile.getFive()));
		}
		
		if(!intersectionsAccountedFor.contains(tile.getSeven())) {
			intersectionsAccountedFor.add(tile.getSeven());
			intersectionGuis.add(new IntersectionGui(bottomLeft(), tile.getSeven()));
		}
		
		if(!intersectionsAccountedFor.contains(tile.getNine())) {
			intersectionsAccountedFor.add(tile.getNine());
			intersectionGuis.add(new IntersectionGui(midLeft(), tile.getNine()));
		}
		
		if(!intersectionsAccountedFor.contains(tile.getEleven())) {
			intersectionsAccountedFor.add(tile.getEleven());
			intersectionGuis.add(new IntersectionGui(topLeft(), tile.getEleven()));
		}
	}
	
	public void createPathwayGuis(ArrayList<PathwayGui> pathwayGuis, ArrayList<Pathway> pathwaysAccountedFor) {
		if(!pathwaysAccountedFor.contains(tile.getTwelve())) {
			pathwaysAccountedFor.add(tile.getTwelve());
			pathwayGuis.add(new PathwayGui(topLeft(), topRight(), tile.getTwelve()));
		}
		
		if(!pathwaysAccountedFor.contains(tile.getTwo())) {
			pathwaysAccountedFor.add(tile.getTwo());
			pathwayGuis.add(new PathwayGui(topRight(), midRight(), tile.getTwo()));
		}
		
		if(!pathwaysAccountedFor.contains(tile.getFour())) {
			pathwaysAccountedFor.add(tile.getFour());
			pathwayGuis.add(new PathwayGui(midRight(), bottomRight(), tile.getFour()));
		}
		
		if(!pathwaysAccountedFor.contains(tile.getSix())) {
			pathwaysAccountedFor.add(tile.getSix());
			pathwayGuis.add(new PathwayGui(bottomRight(), bottomLeft(), tile.getSix()));
		}
		
		if(!pathwaysAccountedFor.contains(tile.getEight())) {
			pathwaysAccountedFor.add(tile.getEight());
			pathwayGuis.add(new PathwayGui(bottomLeft(), midLeft(), tile.getEight()));
		}
		
		if(!pathwaysAccountedFor.contains(tile.getTen())) {
			pathwaysAccountedFor.add(tile.getTen());
			pathwayGuis.add(new PathwayGui(midLeft(), topLeft(), tile.getTen()));
		}
	}
	
	public void draw(Graphics2D g) {
		Path2D.Double p = new Path2D.Double();
		p.moveTo(topLeft().getX(), topLeft().getY());
		p.lineTo(topRight().getX(), topLeft().getY());
		p.lineTo(midRight().getX(), midRight().getY());
		p.lineTo(bottomRight().getX(), bottomRight().getY());
		p.lineTo(bottomLeft().getX(), bottomLeft().getY());
		p.lineTo(midLeft().getX(), midLeft().getY());
		p.closePath();
		
		g.setColor(color);
		g.fill(p);
		g.setColor(hover ? Color.white : Color.black);
		g.setStroke(new BasicStroke(3));
		g.draw(p);
		
		this.number.draw(g, center);
		
		if(this.tile.hasRobber()) {
			RobberGui.draw(g, center);
		}
		
	}
	
	public double getDistance(Point p) {
		return center.distance(p);
	}
	
	public void addHover() {
		this.hover = true;
	}
	
	public void removeHover() {
		this.hover = false;
	}
	
	public String getResource() {
		return this.tile.getResource().toString();
	}
	
	public HexTile getHexTile() {
		return this.tile;
	}
	
	private Point topLeft() {
		return new Point((int) (center.getX() - boundingWidth/2 + boundingWidth/4), (int) (center.getY() - boundingHeight/2));
	}
	
	private Point topRight() {
		return new Point((int) (center.getX() - boundingWidth/2 + boundingWidth/4*3), (int) (center.getY() - boundingHeight/2));
	}
	
	private Point midRight() {
		return new Point((int) (center.getX() - boundingWidth/2 + boundingWidth), (int) (center.getY() - boundingHeight/2 + boundingHeight/2));
	}
	
	private Point bottomRight() {
		return new Point((int) (center.getX() - boundingWidth/2 + boundingWidth/4*3), (int) (center.getY() - boundingHeight/2 + boundingHeight));
	}
	
	private Point bottomLeft() {
		return new Point((int) (center.getX() - boundingWidth/2 + boundingWidth/4), (int) (center.getY() - boundingHeight/2 + boundingHeight));
	}
	
	private Point midLeft() {
		return new Point((int) (center.getX() - boundingWidth/2), (int) (center.getY() - boundingHeight/2 + boundingHeight/2));
	}
	
}
