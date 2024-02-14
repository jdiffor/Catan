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
		party.validateTradeButtons(null);
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	public boolean mouseMoved(Point p) {
		return this.board.mouseMoved(p);
	}
	
	
	public boolean mouseClicked(Point p) {
		System.out.println(p);
		BoardClickEvent boardClick = this.board.mouseClicked(p);
		Intersection intersection = boardClick.getIntersection();
		Pathway pathway = boardClick.getPathway();
		HexTile tile = boardClick.getHexTile();
		
		ActionButton button = this.buttonContainer.mouseClicked(p);
		this.turnManager.getCurrentPlayer().mouseClicked(p, buttonContainer);
		
		Player toTradeWith = this.party.mouseClicked(p);
		
		if(cancelling(button)) {
			wrapUp();
			return true;
		}
		
		if(boardClick != null) {
			System.err.println(stateManager.getActionState());
			switch(stateManager.getActionState()) {
			case RollingForTurn:
				if(button != null) {
					handleButtonClick(button.getAction());
				}
				break;
			case OponentsTurn:
				return false;
			case PreRoll:
				if(button != null) {
					handleButtonClick(button.getAction());
					
					// If we roll a 7 we SHOULD NOT wrapUp()
					if(stateManager.getActionState() != ActionState.MovingRobber) {
						wrapUp();
					} 
					
					return true;
				}
				return false;
			case YourTurn:
				if(button != null) {
					handleButtonClick(button.getAction());
					return true;
				}
				if(toTradeWith != null) {
					party.initiateTrade(turnManager.getCurrentPlayer(), toTradeWith);
				}
				return false;
			case BuildingRoad:
				if(pathway != null && pathway.validForRoad(turnManager.getCurrentPlayer())) {
					if(turnManager.getCurrentPlayer().canBuildRoad()) {
						turnManager.getCurrentPlayer().buildRoad(pathway);
						wrapUp();
						return true;
					}
				}
				return pathway != null;
				
			case MovingRobber:
				if(tile != null && board.canMoveRobberHere(tile)) {
					board.moveRobberHere(tile);
					wrapUp();
					return true;
				}
				return false;
				
			case BuildingRegularSettlement:
				if(intersection != null && intersection.validForSettlement(turnManager.getCurrentPlayer(), false)) {
					if(turnManager.getCurrentPlayer().canBuildSettlement()) {
						turnManager.getCurrentPlayer().buildSettlement(intersection);
						wrapUp();
						
						return true;
					}
				}
				return false;
				
			case BuildingInitialSettlement:
				if(intersection != null && intersection.validForSettlement(turnManager.getCurrentPlayer(), true)) {
					turnManager.getCurrentPlayer().buildSettlement(intersection);
					wrapUp();
					return true;
				}
				return false;
				
			case BuildingCity:
				if(intersection != null && intersection.validForCity(turnManager.getCurrentPlayer())) {
					if(turnManager.getCurrentPlayer().canBuildCity()) {
						turnManager.getCurrentPlayer().buildCity(intersection);
						wrapUp();
						return true;
					}
				}
				return false;
				
			case ExchangingCards:
				
				return true;
			
			default:
				return false;
			}
		}
		
		return false;
	}
	
	private void handleButtonClick(Action action) {
		if(action != Action.ExchangeCards) {
			turnManager.getCurrentPlayer().unSelectHand();
		}
		
		switch(action) {
			case Cancel:
				// Currently this case is never reached because we return if cancelling from
				// mouseClicked before calling handleButtonClick
				this.stateManager.setActionState(ActionState.YourTurn);
				this.buttonContainer.hideCancelButton();
				this.turnManager.getCurrentPlayer().unSelectHand();
			case BuildCity:
				this.stateManager.setActionState(ActionState.BuildingCity);
				this.buttonContainer.showCancelButton();
				break;
			case BuildRoad:
				this.stateManager.setActionState(ActionState.BuildingRoad);
				this.buttonContainer.showCancelButton();
				break;
			case BuildSettlement:
				this.stateManager.setActionState(ActionState.BuildingRegularSettlement);
				this.buttonContainer.showCancelButton();
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
						this.stateManager.setActionState(ActionState.PreRoll);
					} else {
						this.stateManager.setActionState(ActionState.OponentsTurn);
					}
					
				} else if(this.stateManager.getActionState() == ActionState.PreRoll) {
					rollDice();
					party.validateTradeButtons(turnManager.getCurrentPlayer());
				}
				break;
			case ExchangeCards:
				this.buttonContainer.showCancelButton();
				break;
			case PlayDevCard:
				this.buttonContainer.showCancelButton();
				break;
			case DoneWithTurn:
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
		this.board.draw(g);
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
				stateManager.setActionState(ActionState.PreRoll);
				buttonContainer.validateButtons(turnManager.getCurrentPlayer(), board, stateManager, turnManager);
				party.validateTradeButtons(null);
			}
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
		buttonContainer.hideCancelButton();
		buttonContainer.validateButtons(turnManager.getCurrentPlayer(), board, stateManager, turnManager);
	}
}
