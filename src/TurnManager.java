import java.util.ArrayList;
import java.util.Comparator;

public class TurnManager {

	private Party party;
	private Player currentPlayer;
	private boolean devCardPlayed;
	
	public TurnManager(Party party) {
		this.party = party;
		this.currentPlayer = party.getUser();
	}
	
	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}
	
	public Player nextPlayersTurn() {
		currentPlayer = party.getNextPlayer(currentPlayer);
		
		devCardPlayed = false;
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
		
		int highest = 0;
		for(int i = 1; i < party.size(); i++) {
			int thisRoll = dice.roll();
			if(thisRoll > highest) {
				highest = thisRoll;
			}
			rolls.add(thisRoll);
		}

		this.currentPlayer = party.getPlayers().get(rolls.indexOf(highest));		
		dice = userDice;
	}
}
