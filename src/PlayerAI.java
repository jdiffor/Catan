import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

public class PlayerAI extends Player {

	private boolean takenTurn = false;
	private ArrayList<AIAction> thingsToDo;
	
	public PlayerAI(Color color, String name) {
		super(color, name);
		thingsToDo = new ArrayList<AIAction>();
		resetThingsToDo();
	}
	
	private void resetThingsToDo() {
		thingsToDo.clear();
		thingsToDo.add(AIAction.RollDice);
		
		/*
		thingsToDo.add(AIAction.BuildCity);
		thingsToDo.add(AIAction.BuildSettlement);
		thingsToDo.add(AIAction.BuildRoad);
		thingsToDo.add(AIAction.BuildDevCard);
		thingsToDo.add(AIAction.TradeInCards);
		*/
	}
	
	/*
	 * Return true if turn is done, false otherwise
	 */
	public boolean takeTurn(Board board, Dice dice) {
		// Do first thing
		AIAction todo = thingsToDo.remove(0);
		while(!takeAction(todo, board, dice)) {
			todo = thingsToDo.remove(0);
		}
		
		if(thingsToDo.isEmpty()) {
			resetThingsToDo();
			return true;
		}
		
		return false;
	}
	
	private boolean takeAction(AIAction todo, Board board, Dice dice) {
		
		switch(todo) {
		case RollDice:
			rollDice(dice, board);
			return true;
		}
		
		
		return false;
	}
	
	private void rollDice(Dice dice, Board board) {
		int diceRoll = dice.roll();
		if(diceRoll == 7) {
			moveRobber(board);
		} else {
			board.distributeResources(diceRoll);
		}
	}
	
	private void moveRobber(Board board) {
		HexTile best = board.getBoardTiles().get(0);
		
		// TODO: Shouldn't need this once initial settlements exist
		if(best.hasRobber()) {
			best = board.getBoardTiles().get(1);
		}
		
		// Set to arbitrary out of reach values
		int distFromSeven = 100;
		int numStructures = -100;
		
		for(HexTile t : board.getBoardTiles()) {
			if(board.canMoveRobberHere(t) && t.getNonPlayerOwnersCount(this) > 0) {
				int thisDistFromSeven = Math.abs(7 - t.getNumber());
				int thisNumStructures = t.getNonPlayerOwnersCount(this);
				
				if(thisNumStructures - thisDistFromSeven > numStructures - distFromSeven) {
					best = t;
					distFromSeven = thisDistFromSeven;
					numStructures = thisNumStructures;
				}
			}
		}
		board.moveRobberHere(best);
		
		ArrayList<Player> possiblePlayersToRob = best.getNonPlayerOwners(this);
		Collections.shuffle(possiblePlayersToRob);
		
		// TODO: Shouldn't need this once initial settlements exist
		if(possiblePlayersToRob.size() == 0) {
			return;
		}
		
		Player.rob(possiblePlayersToRob.get(0), this);
	}
}
