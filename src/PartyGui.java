import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class PartyGui {

	private Party party;
	private ArrayList<PlayerGui> playerGuis;
	
	public PartyGui(Party party) {
		this.party = party;
		
		playerGuis = new ArrayList<PlayerGui>();
		for(int i = 0; i < party.size(); i++) {
			playerGuis.add(new PlayerGui(party.get(i), i));
		}
	}
	
	public Player mouseClicked(Point p) {
		for(PlayerGui playerGui : playerGuis) {
			if(playerGui.tradeButtonClicked(p)) {
				System.out.println("clicked trade button");
				return playerGui.getPlayer();
			}
		}
		return null;
	}
	
	public void draw(Graphics2D g, Player currentPlayer) {
		for(int i = 0; i < playerGuis.size(); i++) {
			playerGuis.get(i).draw(g, currentPlayer == party.getPlayers().get(i));
		}
	}
	
	public void disableAllTrades() {
		for(PlayerGui playerGui : playerGuis) {
			playerGui.setButtonAllowed(false);
		}
	}
	
	public void setAllOtherTradeButtons(Player p) {
		for(PlayerGui playerGui : playerGuis) {
			if(playerGui.getPlayer() == p) {
				playerGui.setButtonAllowed(false);
			} else {
				playerGui.setButtonAllowed(true);
			}
		}
	}
	
	public void setOnlyThisTradeButton(Player p) {
		for(PlayerGui playerGui : playerGuis) {
			if(playerGui.getPlayer() == p) {
				playerGui.setButtonAllowed(true);
			} else {
				playerGui.setButtonAllowed(false);
			}
		}
	}
}
