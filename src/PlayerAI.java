import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class PlayerAI extends Player {

	private ArrayList<AIAction> thingsToDo;
	
	public PlayerAI(Color color, String name) {
		super(color, name);
		thingsToDo = new ArrayList<AIAction>();
		resetInitialSettlementThingsToDo();
	}
	
	private void resetInitialSettlementThingsToDo() {
		thingsToDo.clear();
		thingsToDo.add(AIAction.BuildInitialSettlement);
		thingsToDo.add(AIAction.BuildInitialRoad);
	}
	
	private void resetGeneralThingsToDo() {
		thingsToDo.clear();
		thingsToDo.add(AIAction.RollDice);
		thingsToDo.add(AIAction.TradeInCards);
		thingsToDo.add(AIAction.BuildCity);
		thingsToDo.add(AIAction.BuildSettlement);
		thingsToDo.add(AIAction.BuildRoad);
		thingsToDo.add(AIAction.BuildDevCard);
		thingsToDo.add(AIAction.TradeInCards);
		thingsToDo.add(AIAction.BuildCity);
		thingsToDo.add(AIAction.BuildSettlement);
		thingsToDo.add(AIAction.BuildRoad);
		thingsToDo.add(AIAction.BuildDevCard);
	}
	
	public boolean considerTradeOffer(ArrayList<ResourceCard> offeredCards, ArrayList<ResourceCard> desiredCards) {
		System.out.println("Offering " + offeredCards.size());
		System.out.println("Wanting " + desiredCards.size());
		return offeredCards.size() > desiredCards.size();
	}
	
	public void discardCardsAI() {
		if(hasTooManyCards()) {
			int discardAmount = this.getHand().size() / 2;
			for(int i = 0; i < discardAmount; i++) {
				this.getHand().removeRandomCard();
			}
		}
	}
	
	/*
	 * Return true if turn is done, false otherwise
	 */
	public boolean takeTurn(Board board, Dice dice, StateManager sm) {		
		// Do first thing
		AIAction todo = thingsToDo.remove(0);
		while(!takeAction(todo, board, dice, sm) && !thingsToDo.isEmpty()) {
			todo = thingsToDo.remove(0);
		}
		
		if(thingsToDo.isEmpty()) {
			if(this.freeSettlements > 0) {
				resetInitialSettlementThingsToDo();
			} else {
				resetGeneralThingsToDo();
			}
			return true;
		}
		
		return false;
	}
	
	private boolean takeAction(AIAction todo, Board board, Dice dice, StateManager sm) {
		
		switch(todo) {
		case BuildInitialSettlement:
			return buildSettlementAI(board);
		case BuildInitialRoad:
			buildInitialRoadAI(this.lastInitialSettlement);
			return true;
		case RollDice:
			rollDiceAI(dice, board, sm);
			return true;
		case BuildCity:
			return buildCityAI(board);
		case BuildSettlement:
			return buildSettlementAI(board);
		case BuildRoad:
			return buildRoadAI(board);
		case BuildDevCard:
			return buildDevCardAI(board);
		case MoveRobber:
			moveRobberAI(board);
			return true;
		case TradeInCards:
			return tradeInCardsAI();
		case TradeWithPlayer:
			return false;
		case PlayDevCard:
			return false;
		}
		
		return false;
	}
	
	private boolean buildRoadAI(Board board) {
 		if(board.canBuildRoadSomewhere(this) && this.canBuildRoad()) {
 			System.out.println(this.getName() + ": building road");
 			
 			Pathway best = null;
 			int bestScore = 0;
 			
 			ArrayList<Pathway> alreadyTested = new ArrayList<Pathway>();
 			
 			for(HexTile tile : board.getBoardTiles()) {
 				for(Intersection i : tile.getIntersections()) {
 					for(Pathway p : i.getPathways()) {
 						if(!alreadyTested.contains(p) && p.validForRoad(this)) {
 							int thisScore = 0;
 							if(p.getIntersectionNotTouchingPlayer(this) == null || p.getIntersectionNotTouchingPlayer(this).hasStructure()) {
 								thisScore = (int) (Math.random() * 2) + 1;
 							} else if(!p.getIntersectionNotTouchingPlayer(this).oneAwayFromStructure()) {
 								for(HexTile t : p.getIntersectionNotTouchingPlayer(this).getHexTiles()) {
 									thisScore += Dice.combosForNumber(t.getNumber());
 								}
 							} else {
 								thisScore = (int) (Math.random() * 4) + 1; //arbitrary
 							}
 							
 							if(thisScore > bestScore) {
 								bestScore = thisScore;
 								best = p;
 								System.out.println(p + " score: " + thisScore);
 							}
 						}
 					}
 				}
 			}
 			
 			if(best == null) {
 				return false;
 			}
 			
 			this.buildRoad(best, false);
 			return true;
 			
 		} else {
 			return false;
 		}
 	}
	
	private boolean buildCityAI(Board board) {
		if(board.canBuildCitySomewhere(this) && this.canBuildCity()) {
			System.out.println(this.getName() + ": building city");
			Intersection best = null;
			int bestScore = 0;
			
			for(HexTile tile : board.getBoardTiles()) {
				for(Intersection i : tile.getIntersections()) {
					
					if(i.hasStructure() && i.getStructure() instanceof Settlement && i.getStructure().getOwner() == this) {
						int thisScore = 0;
						for(HexTile t : i.getHexTiles()) {
							thisScore += Dice.combosForNumber(t.getNumber());
						}
						
						if(thisScore > bestScore) {
							bestScore = thisScore;
							best = i;
						}
					}
				}
			}
			this.buildCity(best);
			return true;
		} else {
			return false;
		}
	}
	
	private boolean buildDevCardAI(Board board) {
		if(this.unplayedDevCardCount() < 3 && this.canBuildDevCard(board.getDevCardDeck())) {
			this.buildDevelopmentCard(board.getDevCardDeck());
		}
		return false;
	}
	
	private boolean tradeInCardsAI() {
		ArrayList<ResourceCard> handCopy = new ArrayList<ResourceCard>(this.getHand().getCards());
		ArrayList<ResourceCard> city = new ArrayList<ResourceCard>();
		
		ArrayList<ResourceCard> needed = needsForCity(handCopy);
		if(needed.size() == 0) {
			return false;
		} else {
			ArrayList<ResourceCard> cardsToExchange = canExchangeForAmount(needed.size(), handCopy);
			if(cardsToExchange != null) {
				this.getHand().exchange(cardsToExchange, needed);
				return true;
			}
		}
		
		handCopy = new ArrayList<ResourceCard>(this.getHand().getCards());
		needed = needsForSettlement(handCopy);
		if(needed.size() == 0) {
			return false;
		} else {
			ArrayList<ResourceCard> cardsToExchange = canExchangeForAmount(needed.size(), handCopy);
			if(cardsToExchange != null) {
				this.getHand().exchange(cardsToExchange, needed);
				return true;
			}
		}
		
		handCopy = new ArrayList<ResourceCard>(this.getHand().getCards());
		needed = needsForRoad(handCopy);
		if(needed.size() == 0) {
			return false;
		} else {
			ArrayList<ResourceCard> cardsToExchange = canExchangeForAmount(needed.size(), handCopy);
			if(cardsToExchange != null) {
				this.getHand().exchange(cardsToExchange, needed);
				return true;
			}
		}
		
		handCopy = new ArrayList<ResourceCard>(this.getHand().getCards());
		needed = needsForDevCard(handCopy);
		if(needed.size() == 0) {
			return false;
		} else {
			ArrayList<ResourceCard> cardsToExchange = canExchangeForAmount(needed.size(), handCopy);
			if(cardsToExchange != null) {
				this.getHand().exchange(cardsToExchange, needed);
				return true;
			}
		}
		
		return false;
	}
	
	private ArrayList<ResourceCard> canExchangeForAmount(int numNeeded, ArrayList<ResourceCard> handCopy) {
		int baseTrade = tradeDeals.contains(HarborType.ThreeForOne) ? 3 : 4;
		HashMap<Resource, Integer> counts = new HashMap<Resource, Integer>();
		for(ResourceCard c : handCopy) {
			counts.put(c.getResource(), counts.getOrDefault(c.getResource(), 0) + 1);
		}
		
		ArrayList<ResourceCard> exchanging = new ArrayList<ResourceCard>();
		for(Resource r : counts.keySet()) {
			int tradeAmount = this.tradeDeals.contains(Harbor.resourceToHarbor.get(r)) ? 2 : baseTrade;
			int numAvailable = counts.get(r);
			
			// While we have more available to exchange
			while(numAvailable > tradeAmount) {
				
				// Keep track of what we would exchange and how close we are to our goal
				numAvailable -= tradeAmount;
				numNeeded--;
				
				for(int i = 0; i < tradeAmount; i++) {
					exchanging.add(new ResourceCard(r));
				}
				
				if(numNeeded == 0) {
					return exchanging;
				}
			}
		}
		return null;
	}
	
	private ArrayList<ResourceCard> needsForCity(ArrayList<ResourceCard> handCopy) {
		ArrayList<ResourceCard> needed = new ArrayList<ResourceCard>();
		
		if(!handCopy.remove(new ResourceCard(Resource.Ore))) {
			needed.add(new ResourceCard(Resource.Ore));
		}
		if(!handCopy.remove(new ResourceCard(Resource.Ore))) {
			needed.add(new ResourceCard(Resource.Ore));
		}
		if(!handCopy.remove(new ResourceCard(Resource.Ore))) {
			needed.add(new ResourceCard(Resource.Ore));
		}
		if(!handCopy.remove(new ResourceCard(Resource.Wheat))) {
			needed.add(new ResourceCard(Resource.Wheat));
		}
		if(!handCopy.remove(new ResourceCard(Resource.Wheat))) {
			needed.add(new ResourceCard(Resource.Wheat));
		}
		return needed;
	}
	
	private ArrayList<ResourceCard> needsForSettlement(ArrayList<ResourceCard> handCopy) {
		ArrayList<ResourceCard> needed = new ArrayList<ResourceCard>();
		
		if(!handCopy.remove(new ResourceCard(Resource.Sheep))) {
			needed.add(new ResourceCard(Resource.Sheep));
		}
		if(!handCopy.remove(new ResourceCard(Resource.Wood))) {
			needed.add(new ResourceCard(Resource.Wood));
		}
		if(!handCopy.remove(new ResourceCard(Resource.Bricks))) {
			needed.add(new ResourceCard(Resource.Bricks));
		}
		if(!handCopy.remove(new ResourceCard(Resource.Wheat))) {
			needed.add(new ResourceCard(Resource.Wheat));
		}
		return needed;
	}
	
	private ArrayList<ResourceCard> needsForRoad(ArrayList<ResourceCard> handCopy) {
		ArrayList<ResourceCard> needed = new ArrayList<ResourceCard>();
		
		if(!handCopy.remove(new ResourceCard(Resource.Wood))) {
			needed.add(new ResourceCard(Resource.Wood));
		}
		if(!handCopy.remove(new ResourceCard(Resource.Bricks))) {
			needed.add(new ResourceCard(Resource.Bricks));
		}
		return needed;
	}
	
	private ArrayList<ResourceCard> needsForDevCard(ArrayList<ResourceCard> handCopy) {
		ArrayList<ResourceCard> needed = new ArrayList<ResourceCard>();
		
		if(!handCopy.remove(new ResourceCard(Resource.Ore))) {
			needed.add(new ResourceCard(Resource.Ore));
		}
		if(!handCopy.remove(new ResourceCard(Resource.Wheat))) {
			needed.add(new ResourceCard(Resource.Wheat));
		}
		if(!handCopy.remove(new ResourceCard(Resource.Sheep))) {
			needed.add(new ResourceCard(Resource.Sheep));
		}
		return needed;
	}
	
 	private boolean buildSettlementAI(Board board) {
 		if(!this.canBuildSettlement()) {
 			return false;
 		}
 		
 		Intersection best = null;
 		int bestScore = 0;
 		for(HexTile tile : board.getBoardTiles()) {
 			for(Intersection i : tile.getIntersections()) {
 				
 				int thisScore = 0;
 				for(HexTile t : i.getHexTiles()) {
 					thisScore += Dice.combosForNumber(t.getNumber());
 	 			}
 	 			if(thisScore > bestScore && i.validForSettlement(this)) {
 	 				bestScore = thisScore;
 	 				best = i;
 	 			}
 				
 			}
 		}
 		
 		if(best == null) {
 			return false;
 		}
 		
 		System.out.println(this.getName() + ": building settlement");
 		
 		this.buildSettlement(best);
 		this.lastInitialSettlement = best;
 		
 		return true;
 	}
 	
 	private void buildInitialRoadAI(Intersection intersection) {
 		System.out.println(this.getName() + ": building road");
 		ArrayList<Pathway> pathways = intersection.getPathways();
 		
 		Pathway best = null;
 		int bestScore = 0;
 		for(Pathway pathway : pathways) {
 			
 			int thisScore = 0;
 			for(HexTile tile : pathway.getOtherIntersection(intersection).getHexTiles()) {
 				thisScore += Dice.combosForNumber(tile.getNumber());
 			}
 			
 			if(thisScore > bestScore && pathway.validForRoad(this)) {
				bestScore = thisScore;
				best = pathway;
			}
 		}
 		
 		this.buildRoad(best, true);
 	}
	
	private void rollDiceAI(Dice dice, Board board, StateManager sm) {
		int diceRoll = dice.roll();
		if(diceRoll == 7) {
			sm.setActionState(ActionState.Discarding);
			this.thingsToDo.add(0, AIAction.MoveRobber);
		} else {
			board.distributeResources(diceRoll);
		}
	}
	
	private void moveRobberAI(Board board) {
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
