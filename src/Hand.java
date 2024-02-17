import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class Hand {

	private ArrayList<ResourceCard> cards;
	private HandGui handGui;
	
	public Hand() {
		cards = new ArrayList<ResourceCard>();
		this.handGui = new HandGui(this);
	}
	
	public boolean isEmpty() {
		return this.cards.size() == 0;
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
		if(r == Resource.Desert || r == Resource.Water) {
			return;
		}
		cards.add(new ResourceCard(r));
		sortCards();
	}
	
	public int removeAllCardsOfType(Resource r) {
		int removed = 0;
		ResourceCard removing = new ResourceCard(r);
		while(cards.contains(removing)) {
			cards.remove(removing);
			removed++;
		}
		return removed;
	}
	
	public ArrayList<ResourceCard> getCards() {
		return this.cards;
	}
	
	public int size() {
		return this.cards.size();
	}
	
	public void clearSelection() {
		for(ResourceCard card : cards) {
			card.unSelect();
		}
	}
	
	public ResourceCard removeRandomCard() {
		if(this.cards.size() == 0) {
			return null;
		}
		
		return cards.remove((int) (Math.random() * cards.size()));
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
		cards.remove(new ResourceCard(Resource.Ore));
		cards.remove(new ResourceCard(Resource.Wheat));
		cards.remove(new ResourceCard(Resource.Sheep));
		
		sortCards();
	}
	
	public Resource getSelectedType() {
		for(ResourceCard card : cards) {
			if(card.isSelected()) {
				return card.getResource();
			}
		}
		return null;
	}
	
	public boolean canExchangeSelectedCards(ArrayList<HarborType> tradeDeals) {
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
		
		if(r == null) {
			return false;
		}
		
		if((r == Resource.Bricks && tradeDeals.contains(HarborType.TwoForOneBricks)) ||
				(r == Resource.Ore && tradeDeals.contains(HarborType.TwoForOneOre)) ||
				(r == Resource.Sheep && tradeDeals.contains(HarborType.TwoForOneSheep)) ||
				(r == Resource.Wood && tradeDeals.contains(HarborType.TwoForOneWood)) ||
				(r == Resource.Wheat && tradeDeals.contains(HarborType.TwoForOneWheat))) {
			return count == 2;
		} else if(tradeDeals.contains(HarborType.ThreeForOne)) {
			return count == 3;
		} else {
			return count == 4;
		}
	}
	
	public void exchangeSelectedCards(Resource r) {
		for(int i = 0; i < cards.size(); i++) {
			if(cards.get(i).isSelected()) {
				cards.remove(i);
				i--;
			}
		}
		cards.add(new ResourceCard(r));
		sortCards();
	}
	
	private void sortCards() {
		this.cards.sort((o1, o2) -> o1.getResource().toString().compareTo(o2.getResource().toString()));
	}
	
}
