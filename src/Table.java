import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class Table {

	private Party party;
	private Player user;
	private Board board;
	private Dice dice;
	
	private StateManager stateManager;
	private TurnManager turnManager;
	private ActionButtonContainer buttonContainer;
	private CardExchangeGui cardExchange;
	
	public Table(int numPlayers) {
		if(numPlayers < 2 || numPlayers > 4) {
			System.err.println("Invalid number of players");
		}
		
		SetupManager setupManager = new SetupManager();
		stateManager = new StateManager();
		board = new Board(setupManager, stateManager);
		dice = new Dice();
		
		party = new Party(numPlayers, setupManager);
		turnManager = new TurnManager(party);
		user = party.getUser();
		
		buttonContainer = new ActionButtonContainer();
		buttonContainer.validateButtons(turnManager.getCurrentPlayer(), board, stateManager, turnManager);
		
		cardExchange = new CardExchangeGui();
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	public boolean mouseMoved(Point p) {
		return this.board.mouseMoved(p, turnManager.getCurrentPlayer());
	}
	
	
	public void mouseClicked(Point p) {
		System.out.println(p);
		BoardClickEvent boardClick = this.board.mouseClicked(p);
		Intersection intersection = boardClick.getIntersection();
		Pathway pathway = boardClick.getPathway();
		HexTile tile = boardClick.getHexTile();
		
		// Clicking one of the main buttons from the left of screen
		ActionButton button = this.buttonContainer.mouseClicked(p);
		
		// Selecting cards
		if(stateManager.getActionState() == ActionState.YourTurn) {
			this.turnManager.getCurrentPlayer().mouseClicked(p, buttonContainer, turnManager);
		}
		
		// Clicking on a player
		Player playerToTradeWithOrStealFrom = this.party.mouseClicked(p);
		
		// Clicking accept or cancel on exchanging cards
		Resource resourceFromExchange = this.cardExchange.mouseClicked(p);
		
		if(cancelling(button)) {
			wrapUp();
			return;
		}
		
		if(boardClick != null) {
			System.err.println(stateManager.getActionState());
			switch(stateManager.getActionState()) {
			case RollingForTurn:
				if(button != null) {
					handleButtonClick(button.getAction());
					//this.buttonContainer.setAllInactive();
				}
				break;
			case OponentsTurn:
				break;
			case PreRoll:
				if(button != null) {
					handleButtonClick(button.getAction());
					
					// If we roll a 7 we SHOULD NOT wrapUp()
					if(stateManager.getActionState() != ActionState.MovingRobber) {
						wrapUp();
					} else {
						this.party.validateTradeButtons(null);
					}
				}
				break;
			case YourTurn:
				if(button != null) {
					handleButtonClick(button.getAction());
					break;
				}
				
				// Not stealing if YourTurn, must be trading
				if(playerToTradeWithOrStealFrom != null) {
					party.initiateTrade(turnManager.getCurrentPlayer(), playerToTradeWithOrStealFrom);
				}
				break;
				
			case BuildingRoad:
				if(pathway != null && pathway.validForRoad(turnManager.getCurrentPlayer())) {
					if(turnManager.getCurrentPlayer().canBuildRoad()) {
						turnManager.getCurrentPlayer().buildRoad(pathway, false);
						wrapUp();
					}
				}
				break;
			case BuildingInitialRoad:
				if(pathway != null && pathway.validForRoad(turnManager.getCurrentPlayer())) {
					if(turnManager.getCurrentPlayer().canBuildRoad()) {
						turnManager.getCurrentPlayer().buildRoad(pathway, true);
						buttonContainer.setButtonsForDoneWithInitialBuild();
						stateManager.clearActionState();
						board.clearHover();
					}
				}
				break;
			case MovingRobber:
				if(tile != null && board.canMoveRobberHere(tile)) {
					ArrayList<Player> playersOnRobbedTile = board.moveRobberHere(tile);
					int countToRob = party.setPossiblePlayersToStealFrom(playersOnRobbedTile);
					if(countToRob > 0) {
						buttonContainer.setAllInactive();
						stateManager.setActionState(ActionState.Stealing);
						board.clearHover();
					} else {
						wrapUp();
					}
				}
				break;
				
			case Stealing:
				if(playerToTradeWithOrStealFrom != null) {
					Player.rob(playerToTradeWithOrStealFrom, turnManager.getCurrentPlayer());
					party.doneStealing();
					wrapUp();
				}				
				break;
				
			case BuildingRegularSettlement:
				if(intersection != null && intersection.validForSettlement(turnManager.getCurrentPlayer())) {
					if(turnManager.getCurrentPlayer().canBuildSettlement()) {
						turnManager.getCurrentPlayer().buildSettlement(intersection);
						wrapUp();
					}
				}
				break;
				
			case BuildingInitialSettlement:
				if(intersection != null && intersection.validForSettlement(turnManager.getCurrentPlayer())) {
					turnManager.getCurrentPlayer().buildSettlement(intersection);
					//wrapUp();
					board.clearHover();
					buttonContainer.setButtonsForInitialRoad();
					stateManager.clearActionState();
				}
				break;
				
			case BuildingCity:
				if(intersection != null && intersection.validForCity(turnManager.getCurrentPlayer())) {
					if(turnManager.getCurrentPlayer().canBuildCity()) {
						turnManager.getCurrentPlayer().buildCity(intersection);
						wrapUp();
					}
				}
				break;
				
			case ExchangingCards:
				if(resourceFromExchange != null) {
					if(turnManager.getCurrentPlayer().exchangeSelectedCards(resourceFromExchange)) {
						wrapUp();
					}
				}
				break;
			
			case YearOfPlentyCardOne:
				if(resourceFromExchange != null) {
					turnManager.getCurrentPlayer().addResource(resourceFromExchange);
					stateManager.setActionState(ActionState.YearOfPlentyCardTwo);
				}
				break;
				
			case YearOfPlentyCardTwo:
				if(resourceFromExchange != null) {
					turnManager.getCurrentPlayer().addResource(resourceFromExchange);
					wrapUp();
				}
				break;
			case RoadBuildingFirstRoad:
				if(pathway != null && pathway.validForRoad(turnManager.getCurrentPlayer()) && turnManager.getCurrentPlayer().canBuildFreeRoad()) {
					turnManager.getCurrentPlayer().buildRoad(pathway, true);
					if(turnManager.getCurrentPlayer().canBuildFreeRoad()) {
						stateManager.setActionState(ActionState.RoadBuildingSecondRoad);
					} else {
						wrapUp();
					}
				}
				break;
				
			case RoadBuildingSecondRoad:
				if(pathway != null && pathway.validForRoad(turnManager.getCurrentPlayer()) && turnManager.getCurrentPlayer().canBuildFreeRoad()) {
					turnManager.getCurrentPlayer().buildRoad(pathway, true);
					wrapUp();
				}
				break;
				
			case Monopoly:
				if(resourceFromExchange != null) {
					this.party.activateMonopoly(turnManager.getCurrentPlayer(), resourceFromExchange);
					wrapUp();
				}
				
			default:
				break;
			}
		}
		
		party.updateScores(board);
	}
	
	private void handleButtonClick(Action action) {
		if(action != Action.ExchangeCards) {
			turnManager.getCurrentPlayer().unSelectHand();
		}
		
		switch(action) {
			case Cancel:
				// Currently this case is never reached because we return if cancelling from
				// mouseClicked before calling handleButtonClick
				this.stateManager.clearActionState();
				this.buttonContainer.hideCancelButton();
				this.turnManager.getCurrentPlayer().unSelectHand();
			case BuildCity:
				this.stateManager.setActionState(ActionState.BuildingCity);
				this.buttonContainer.showCancelButton();
				break;
			case BuildRoad:
				if(this.turnManager.inInitialBuildingPhase()) {
					this.buttonContainer.setAllInactive();
					this.stateManager.setActionState(ActionState.BuildingInitialRoad);
				} else {
					this.buttonContainer.showCancelButton();
					this.stateManager.setActionState(ActionState.BuildingRoad);
				}
				
				break;
			case BuildSettlement:
				if(this.turnManager.inInitialBuildingPhase()) {
					this.stateManager.setActionState(ActionState.BuildingInitialSettlement);
					this.buttonContainer.setAllInactive();
				} else {
					this.stateManager.setActionState(ActionState.BuildingRegularSettlement);
					this.buttonContainer.showCancelButton();
				}
				break;
			case BuildDevCard:
				if(turnManager.getCurrentPlayer().canBuildDevCard(board.getDevCardDeck())) {
					turnManager.getCurrentPlayer().buildDevelopmentCard(board.getDevCardDeck());
					wrapUp();
				}
				break;
			case RollDice:
				if(this.stateManager.getActionState() == ActionState.RollingForTurn) {
					this.turnManager.rollDiceForTurn(this.dice);
					
					if(this.turnManager.getCurrentPlayer() == user) {
						this.stateManager.clearActionState();
						this.buttonContainer.setButtonsForInitialSettlement();
					} else {
						this.stateManager.setActionState(ActionState.OponentsTurn);
						this.buttonContainer.setAllInactive();
					}
					
				} else if(this.stateManager.getActionState() == ActionState.PreRoll) {
					rollDice();
					party.validateTradeButtons(turnManager.getCurrentPlayer());
				}
				break;
			case ExchangeCards:
				this.stateManager.setActionState(ActionState.ExchangingCards);
				this.buttonContainer.showCancelButton();
				break;
			case PlayDevCard:
				DevelopmentCardType playedCard = this.turnManager.getCurrentPlayer().playSelectedDevCard();
				turnManager.playDevCard();
				this.buttonContainer.setAllInactive();
				takeDevCardAction(playedCard);
				//play dev card
				break;
			case DoneWithTurn:
				this.turnManager.getCurrentPlayer().unSelectDevCards();
				this.stateManager.setActionState(ActionState.OponentsTurn);
				this.turnManager.nextPlayersTurn();
				
				buttonContainer.validateButtons(turnManager.getCurrentPlayer(), board, stateManager, turnManager);
				party.validateTradeButtons(null);
				
				break;
			default:
				break;
		}
	}
	
	public void draw(Graphics2D g) {
		if(this.stateManager.getActionState() == ActionState.ExchangingCards ||
				this.stateManager.getActionState() == ActionState.YearOfPlentyCardOne ||
				this.stateManager.getActionState() == ActionState.YearOfPlentyCardTwo ||
				this.stateManager.getActionState() == ActionState.Monopoly) {
			this.cardExchange.draw(g);
		} else {
			this.board.draw(g);
		}
		
		this.user.draw(g);
		this.buttonContainer.draw(g);
		this.dice.draw(g);
		this.party.draw(g, turnManager.getCurrentPlayer());
	}
	
	public void takeAITurnIfApplicable() {
		if(stateManager.getActionState() == ActionState.OponentsTurn) {
			Player p = turnManager.getCurrentPlayer();
			if(p instanceof PlayerAI) {
				if(((PlayerAI) p).takeTurn(board, dice)) {
					System.out.println("Turn over");
					turnManager.nextPlayersTurn();
				}
			} else {
				if(turnManager.inInitialBuildingPhase()) {
					stateManager.clearActionState();
					buttonContainer.setButtonsForInitialSettlement();
				} else {
					stateManager.setActionState(ActionState.PreRoll);
					buttonContainer.validateButtons(turnManager.getCurrentPlayer(), board, stateManager, turnManager);
					party.validateTradeButtons(null);
				}
			}
			
			party.updateScores(board);
		}
	}
	
	private void rollDice() {
		int diceRoll = this.dice.roll();
		if(diceRoll == 7) {
			this.stateManager.setActionState(ActionState.MovingRobber);
			this.buttonContainer.setAllInactive();
		} else {
			this.board.distributeResources(diceRoll);
		}
	}
	
	private void takeDevCardAction(DevelopmentCardType type) {
		switch(type) {
		case Knight:
			this.buttonContainer.setAllInactive();
			this.stateManager.setActionState(ActionState.MovingRobber);
			break;
		case YearOfPlenty:
			this.buttonContainer.setAllInactive();
			this.stateManager.setActionState(ActionState.YearOfPlentyCardOne);
			break;
		case RoadBuilding:
			if(this.turnManager.getCurrentPlayer().canBuildFreeRoad()) {
				this.buttonContainer.setAllInactive();
				this.stateManager.setActionState(ActionState.RoadBuildingFirstRoad);
			} else {
				wrapUp();
			}
			break;
		case Monopoly:
			this.buttonContainer.setAllInactive();
			this.stateManager.setActionState(ActionState.Monopoly);
			break;
		default:
			break;
		}
	}
	
	private boolean cancelling(ActionButton button) {
		if(button != null && button.getAction() == Action.Cancel) {
			return true;
		}
		
		return false;
	}
	
	private void wrapUp() {
		board.clearHover();
		stateManager.clearActionState();
		turnManager.getCurrentPlayer().unSelectHand();
		turnManager.getCurrentPlayer().unSelectDevCards();
		buttonContainer.hideCancelButton();
		buttonContainer.validateButtons(turnManager.getCurrentPlayer(), board, stateManager, turnManager);
	}
}
