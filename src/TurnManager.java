import java.util.ArrayList;
import java.util.Comparator;

public class TurnManager {

	public static int TURN_NUMBER = 0;
	
	private Party party;
	private Player currentPlayer;
	private boolean devCardPlayed;
	private ArrayList<Player> initialBuildingOrder;
	
	public TurnManager(Party party) {
		this.party = party;
		this.currentPlayer = party.getUser();
		initialBuildingOrder = new ArrayList<Player>();
	}
	
	public boolean inInitialBuildingPhase() {
		return this.initialBuildingOrder == null || this.initialBuildingOrder.size() > 0;
	}
	
	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}
	
	public Player nextPlayersTurn() {
		TURN_NUMBER++;
		
		if(initialBuildingOrder.size() > 0) {
			currentPlayer = initialBuildingOrder.remove(0);
		} else {
			currentPlayer = party.getNextPlayer(currentPlayer);
		}
		
		devCardPlayed = false;
		
		System.out.println("Now " + currentPlayer.getName() + "'s turn");
		return currentPlayer;
	}
	
	public boolean hasDevCardBeenPlayedThisTurn() {
		return devCardPlayed;
	}
	
	public void playDevCard() {
		this.devCardPlayed = true;
	}
	
	public void rollDiceForTurn(Dice dice) {
		ArrayList<Integer> rolls = new ArrayList<Integer>();
		
		// User rolls dice and die roll is saved to paint on screen
		rolls.add(dice.roll());
		Dice userDice = new Dice(dice);
		
		boolean tiedForHighest = false;
		int highest = rolls.get(0);
		
		for(int i = 1; i < party.size(); i++) {
			int thisRoll = dice.roll();
			
			if(thisRoll > highest) {
				highest = thisRoll;
				tiedForHighest = false;
			} else if(thisRoll == highest) {
				tiedForHighest = true;
			}
			
			rolls.add(thisRoll);
		}
		
		// Re-roll until no tie for first
		while(tiedForHighest) {
			int oldHighest = highest;
			highest = 1;
			for(int i = 0; i < rolls.size(); i++) {
				if(rolls.get(i) == oldHighest) {
					
					int thisRoll = dice.roll();
					if(thisRoll > highest) {
						highest = thisRoll;
						tiedForHighest = false;
					} else if(thisRoll == highest) {
						tiedForHighest = true;
					}
					rolls.set(i, thisRoll);
					
				} else {
					rolls.set(i, 0);
				}
			}
		}

		//this.currentPlayer = party.getPlayers().get(rolls.indexOf(highest));
		this.currentPlayer = party.getUser();
		System.out.println(currentPlayer.getName() + " goes first");
		createInitialBuildingOrder();
		
		// Used to draw player's first roll on screen
		dice = userDice;
	}
	
	private void createInitialBuildingOrder() {
		Player p = this.currentPlayer;
		while(initialBuildingOrder.size() == 0 || p != initialBuildingOrder.get(0)) {
			initialBuildingOrder.add(p);
			p = party.getNextPlayer(p);
		}
		
		for(int i = initialBuildingOrder.size() - 1; i >= 0; i--) {
			initialBuildingOrder.add(initialBuildingOrder.get(i));
		}
		
		// Current player already set to first player in order
		// First player in order is the first one to get a real turn
		initialBuildingOrder.add(initialBuildingOrder.remove(0));
	}
}
