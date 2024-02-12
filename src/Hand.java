import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class Hand {

	private ArrayList<ResourceCard> cards;
	private HandGui handGui;
	
	public Hand() {
		cards = new ArrayList<ResourceCard>();
		this.handGui = new HandGui(this);
		
		cards.add(new ResourceCard(Resource.Bricks));
		cards.add(new ResourceCard(Resource.Ore));
		cards.add(new ResourceCard(Resource.Sheep));
		cards.add(new ResourceCard(Resource.Wheat));
		cards.add(new ResourceCard(Resource.Wood));
		cards.add(new ResourceCard(Resource.Bricks));
		cards.add(new ResourceCard(Resource.Ore));
		cards.add(new ResourceCard(Resource.Wheat));
		cards.add(new ResourceCard(Resource.Ore));
		cards.add(new ResourceCard(Resource.Wheat));
		cards.add(new ResourceCard(Resource.Ore));
	
		sortCards();
	}
	
	public void mouseClicked(Point p) {
		this.handGui.mouseClicked(p);
	}
	
	public void draw(Graphics2D g) {
		if(cards.size() > 0) {
			this.handGui.draw(g);
		}
	}
	
	public void addCardOfResourceType(Resource r) {
		cards.add(new ResourceCard(r));
		sortCards();
	}
	
	public ArrayList<ResourceCard> getCards() {
		return this.cards;
	}
	
	public void clearSelection() {
		for(ResourceCard card : cards) {
			card.unSelect();
		}
	}
	
	public boolean canBuildSettlement() {
		return cards.contains(new ResourceCard(Resource.Bricks)) &&
				cards.contains(new ResourceCard(Resource.Wood)) &&
				cards.contains(new ResourceCard(Resource.Sheep)) &&
				cards.contains(new ResourceCard(Resource.Wheat));
	}
	
	public void buildSettlement() {
		cards.remove(new ResourceCard(Resource.Bricks));
		cards.remove(new ResourceCard(Resource.Wood));
		cards.remove(new ResourceCard(Resource.Sheep));
		cards.remove(new ResourceCard(Resource.Wheat));
		
		sortCards();
	}
	
	public boolean canBuildCity() {
		int wheatCount = 0;
		int oreCount = 0;
		for(ResourceCard card : cards) {
			if(card.getResource() == Resource.Wheat) {
				wheatCount++;
			} else if(card.getResource() == Resource.Ore) {
				oreCount++;
			}
		}
		return wheatCount >= 2 && oreCount >= 3;
	}
	
	public void buildCity() {
		cards.remove(new ResourceCard(Resource.Wheat));
		cards.remove(new ResourceCard(Resource.Wheat));
		cards.remove(new ResourceCard(Resource.Ore));
		cards.remove(new ResourceCard(Resource.Ore));
		cards.remove(new ResourceCard(Resource.Ore));
		
		sortCards();
	}
	
	public boolean canBuildRoad() {
		return cards.contains(new ResourceCard(Resource.Bricks)) &&
				cards.contains(new ResourceCard(Resource.Wood));
	}
	
	public void buildRoad() {
		cards.remove(new ResourceCard(Resource.Bricks));
		cards.remove(new ResourceCard(Resource.Wood));
		
		sortCards();
	}
	
	public boolean canBuildDevCard() {
		return cards.contains(new ResourceCard(Resource.Ore)) &&
				cards.contains(new ResourceCard(Resource.Wheat)) &&
				cards.contains(new ResourceCard(Resource.Sheep));
	}
	
	public void buildDevCard() {
		cards.remove(new ResourceCard(Resource.Bricks));
		cards.remove(new ResourceCard(Resource.Wood));
		cards.remove(new ResourceCard(Resource.Sheep));
		
		sortCards();
	}
	
	public boolean canExchangeSelectedCards() {
		int count = 0;
		Resource r = null;
		for(ResourceCard card : cards) {
			if(card.isSelected()) {
				if(r == null) {
					r = card.getResource();
				} else if(card.getResource() != r) {
					return false;
				}
				count++;
			}
		}
		
		// TODO: change return based on ports
		return count == 4;
	}
	
	private void sortCards() {
		this.cards.sort((o1, o2) -> o1.getResource().toString().compareTo(o2.getResource().toString()));
	}
	
}
