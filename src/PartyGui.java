import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class PartyGui {

	private static final int HEIGHT = 70;
	private static final int GAP = 10;
	
	private Party party;
	private ArrayList<PlayerGui> playerGuis;
	
	public PartyGui(Party party) {
		this.party = party;
		
		playerGuis = new ArrayList<PlayerGui>();
		for(int i = 0; i < party.size(); i++) {
			playerGuis.add(new PlayerGui(party.get(i), i, HEIGHT, GAP));
		}
		
		disableAllTrades();
		disableAllStealing();
	}
	
	public Player mouseClicked(Point p) {
		for(PlayerGui playerGui : playerGuis) {
			if(playerGui.buttonClicked(p)) {
				System.out.println("clicked player button");
				return playerGui.getPlayer();
			}
			
		}
		return null;
	}
	
	public void draw(Graphics2D g, Player currentPlayer) {
		for(int i = 0; i < playerGuis.size(); i++) {
			playerGuis.get(i).draw(g, currentPlayer == party.getPlayers().get(i));
		}
		
		party.getSpecialPoints().draw(g, HEIGHT, GAP);
	}
	
	public void disableAllTrades() {
		for(PlayerGui playerGui : playerGuis) {
			playerGui.setTradeButtonAllowed(false);
			playerGui.setTradeAllButtonAllowed(false);
		}
	}
	
	public void setAllOtherTradeButtons(Player p) {
		for(PlayerGui playerGui : playerGuis) {
			if(playerGui.getPlayer() == p) {
				playerGui.setTradeButtonAllowed(false);
				playerGui.setTradeAllButtonAllowed(true);
			} else {
				playerGui.setTradeButtonAllowed(true);
				playerGui.setTradeAllButtonAllowed(false);
			}
		}
	}
	
	public void setOnlyThisTradeButton(Player p) {
		for(PlayerGui playerGui : playerGuis) {
			if(playerGui.getPlayer() == p) {
				playerGui.setTradeButtonAllowed(true);
				playerGui.setTradeAllButtonAllowed(false);
			} else {
				playerGui.setTradeButtonAllowed(false);
				playerGui.setTradeAllButtonAllowed(false);
			}
		}
	}
	
	public void disableAllStealing() {
		for(PlayerGui playerGui : playerGuis) {
			playerGui.setStealButtonAllowed(false);
		}
	}
	
	public void setStealButton(Player p) {
		for(PlayerGui playerGui : playerGuis) {
			if(playerGui.getPlayer() == p) {
				playerGui.setStealButtonAllowed(true);
			}
		}
	}
}
