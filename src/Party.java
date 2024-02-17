import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class Party {

	private ArrayList<Player> players;
	private Player user;
	private PartyGui partyGui;
	private SpecialPoints specialPoints;
	
	public Party(int numPlayers, SetupManager sm) {
		this.players = new ArrayList<Player>();
		
		this.user = new Player(sm.getNextColor(), "User");
		this.players.add(user);
		
		for(int i = 1; i < numPlayers; i++) {
			players.add(new PlayerAI(sm.getNextColor(), "AI #" + i));
		}
		
		this.partyGui = new PartyGui(this);	
		this.specialPoints = new SpecialPoints(numPlayers);
	}
	
	public Player mouseClicked(Point p) {
		return partyGui.mouseClicked(p);
	}
	
	public void validateTradeButtons(Player currentPlayer) {
		// TODO: hide if player has no cards
		if(currentPlayer == null) {
			partyGui.disableAllTrades();
		} if(currentPlayer == user) {
			partyGui.setAllOtherTradeButtons(user);
		} else {
			partyGui.setOnlyThisTradeButton(currentPlayer);
		}
	}
	
	public int setPossiblePlayersToStealFrom(ArrayList<Player> playersOnRobbedTile) {
		partyGui.disableAllStealing();
		int countToRob = 0;
		for(Player player : playersOnRobbedTile) {
			if(player != user && player.getHand().size() > 0) {
				partyGui.setStealButton(player);
				countToRob++;
			}
		}
		
		return countToRob;
	}
	
	public void doneStealing() {
		partyGui.disableAllStealing();
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
	
	public SpecialPoints getSpecialPoints() {
		return this.specialPoints;
	}
	
	public void draw(Graphics2D g, Player currentPlayer) {
		partyGui.draw(g, currentPlayer);
	}
	
	public void initiateTrade(Player offering, Player beingOffered) {
		// TODO: implement
		System.out.println(offering.getName() + " offers " + beingOffered.getName() + " a trade");
	}
	
	public void activateMonopoly(Player monopolizer, Resource resource) {
		int cardsTaken = 0;
		for(Player p : players) {
			if(p != monopolizer) {
				cardsTaken += p.removeAllCardsOfType(resource);
			}
		}
		
		for(int i = 0; i < cardsTaken; i++) {
			monopolizer.addResource(resource);
		}
	}
	
	// Return true if game over, false otherwise
	public boolean updateScores(Board board) {
		int highScore = 0;
		for(Player player : players) {
			int score = player.updateScore(board, specialPoints);			
			
			highScore = score > highScore ? score : score;
		}
		
		return highScore >= 10;
	}
	
}
