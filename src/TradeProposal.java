import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

public class TradeProposal {

	private TradeProposalGui tradeProposalGui;
	private HashMap<Resource, Integer> amountSelected;
	private Player offeringTo;
	
	public TradeProposal() {
		this.tradeProposalGui = new TradeProposalGui(this);
		this.amountSelected = new HashMap<Resource, Integer>();
	}
	
	public void draw(Graphics2D g) {
		this.tradeProposalGui.draw(g);
	}
	
	public void mouseClicked(Point p) {
		tradeProposalGui.mouseClicked(p);
	}
	
	public void setPlayerOfferingTo(Player p) {
		this.offeringTo = p;
	}
	
	public Player getPlayerOfferingTo() {
		return this.offeringTo;
	}
	
	public void clear() {
		for(Resource r : amountSelected.keySet()) {
			amountSelected.put(r, 0);
		}
	}
	
	public int getResourceCount(Resource r) {
		return amountSelected.getOrDefault(r, 0);
	}
	
	public boolean hasAnyCardsSelected() {
		for(Resource r : amountSelected.keySet()) {
			if(amountSelected.get(r) > 0) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Resource> getSelectedCardTypes() {
		ArrayList<Resource> types = new ArrayList<Resource>();
		for(Resource r : amountSelected.keySet()) {
			if(amountSelected.get(r) > 0) {
				types.add(r);
			}
		}
		return types;
	}
	
	public ArrayList<ResourceCard> getSelectedCards() {
		ArrayList<ResourceCard> cards = new ArrayList<ResourceCard>();
		for(Resource r : amountSelected.keySet()) {
			for(int i = 0; i < amountSelected.get(r); i++) {
				cards.add(new ResourceCard(r));
			}
		}
		return cards;
	}
	
	public void increaseResourceCount(Resource r) {
		amountSelected.put(r, amountSelected.getOrDefault(r, 0) + 1);
	}
	
	public void decreaseResourceCount(Resource r) {
		int newAmount = amountSelected.getOrDefault(r, 0) - 1;
		if(newAmount < 0) {
			newAmount = 0;
		}
		amountSelected.put(r, newAmount);
	}
	
	public ArrayList<ResourceCard> completeProposal() {
		ArrayList<ResourceCard> resources = new ArrayList<ResourceCard>();
		for(Resource r : amountSelected.keySet()) {
			for(int i = 0; i < amountSelected.get(r); i++) {
				resources.add(new ResourceCard(r));
			}
		}
		return resources;
	}
	
}
