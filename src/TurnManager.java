import java.util.ArrayList;

public class TurnManager {

	private ArrayList<Player> players;
	private Player currentPlayer;
	private boolean devCardPlayed;
	
	public TurnManager(ArrayList<Player> players) {
		this.players = players;
		currentPlayer = rollForFirst();
	}
	
	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}
	
	public Player nextPlayersTurn() {
		int currentPlayerIndex = players.indexOf(currentPlayer) + 1;
		currentPlayerIndex = currentPlayerIndex >= players.size() ? 0 : currentPlayerIndex;
		currentPlayer = players.get(currentPlayerIndex);
		
		devCardPlayed = false;
		return currentPlayer;
	}
	
	public boolean hasDevCardBeenPlayedThisTurn() {
		return devCardPlayed;
	}
	
	public void playDevCard() {
		this.devCardPlayed = true;
	}
	
	private Player rollForFirst() {
		return players.get(0);
	}
}
