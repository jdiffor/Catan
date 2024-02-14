import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class Party {

	private ArrayList<Player> players;
	private Player user;
	private PartyGui partyGui;
	
	public Party(int numPlayers, SetupManager sm) {
		this.players = new ArrayList<Player>();
		
		this.user = new Player(sm.getNextColor(), "User");
		this.players.add(user);
		
		for(int i = 0; i < numPlayers - 1; i++) {
			players.add(new PlayerAI(sm.getNextColor(), "AI #" + i));
		}
		
		this.partyGui = new PartyGui(this);		
	}
	
	public Player mouseClicked(Point p) {
		return partyGui.mouseClicked(p);
	}
	
	public void validateTradeButtons(Player currentPlayer) {
		if(currentPlayer == null) {
			partyGui.disableAllTrades();
		} if(currentPlayer == user) {
			partyGui.setAllOtherTradeButtons(user);
		} else {
			partyGui.setOnlyThisTradeButton(currentPlayer);
		}
	}
	
	public int size() {
		return this.players.size();
	}
	
	public Player get(int index) {
		if(index >= 0 && index < this.players.size()) {
			return this.players.get(index);
		}
		return null;
	}
	
	public Player getUser() {
		return this.user;
	}
	
	public ArrayList<Player> getPlayers() {
		return this.players;
	}
	
	public Player getNextPlayer(Player currentPlayer) {
		int nextPlayerIndex = players.indexOf(currentPlayer) + 1;
		nextPlayerIndex = nextPlayerIndex >= players.size() ? 0 : nextPlayerIndex;
		Player nextPlayer = players.get(nextPlayerIndex);
		
		return nextPlayer;
	}
	
	public void draw(Graphics2D g, Player currentPlayer) {
		partyGui.draw(g, currentPlayer);
	}
	
	public void initiateTrade(Player offering, Player beingOffered) {
		// TODO: implement
		System.out.println(offering.getName() + " offers " + beingOffered.getName() + " a trade");
	}
	
}
