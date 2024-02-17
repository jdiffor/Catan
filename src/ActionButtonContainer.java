import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class ActionButtonContainer {

	public static final int BUTTON_GAP = 10;
	public static final int START_X = 160;
	public static final int START_Y = 380;
	
	private ActionButton buildRoadButton;
	private ActionButton buildSettlementButton;
	private ActionButton buildCityButton;
	private ActionButton buildDevCardButton;
	private ActionButton playDevCardButton;
	private ActionButton exchangeCardsButton;
	private ActionButton rollDiceButton;
	private ActionButton doneWithTurnButton;
	
	private ArrayList<ActionButton> buttons = new ArrayList<ActionButton>();
	private ActionButton cancelButton;
	
	public ActionButtonContainer() {
		buttons = createActionButtons();
		cancelButton = createCancelButton();
		buttons.add(cancelButton);
	}
	
	public ActionButton mouseClicked(Point p) {
		for(ActionButton button : buttons) {
			if(button.clicked(p)) {
				System.out.println(button);
				return button;
			}
		}
		return null;
	}
	
	public void draw(Graphics2D g) {
		for(ActionButton button : buttons) {
			button.draw(g);
		}
	}
	
	public void setAllInactive() {
		for(ActionButton button : buttons) {
			button.setActive(false);
		}
	}
	
	public void setButtonsForInitialSettlement() {
		setAllInactive();
		buildSettlementButton.setActive(true);
	}
	
	public void setButtonsForInitialRoad() {
		setAllInactive();
		buildRoadButton.setActive(true);
	}
	
	public void setButtonsForDoneWithInitialBuild() {
		setAllInactive();
		doneWithTurnButton.setActive(true);
	}
	
	public void validateButtons(Player player, Board board, StateManager sm, TurnManager tm) {
		setAllInactive();
		if(sm.getActionState() == ActionState.PreRoll || sm.getActionState() == ActionState.RollingForTurn) {
			rollDiceButton.setActive(true);
			// TODO: Activate play dev card if knight
			return;
		}
		
		if(sm.getActionState() == ActionState.YourTurn) {
			validateExchangeCardsButton(player);
			validateBuildRoadButton(player, board);
			validateBuildSettlementButton(player, board);
			validateBuildCityButton(player, board);
			validateBuildDevCardButton(player, board.getDevCardDeck());
			validatePlayDevelopmentCardButton(player, tm);
			
			doneWithTurnButton.setActive(true); // Always active unless we haven't rolled yet
		}
		
		
		cancelButton.setActive(true);
	}
	
	public void validateExchangeCardsButton(Player player) {
		this.exchangeCardsButton.setActive(player.canExchangeSelectedCards());
	}
	
	public void validatePlayDevelopmentCardButton(Player player, TurnManager tm) {
		boolean cardSelected = player.hasPlayableDevCardSelected();
		boolean nonePlayedYet = !tm.hasDevCardBeenPlayedThisTurn();
		this.playDevCardButton.setActive(cardSelected && nonePlayedYet);
	}
	
	private void validateBuildRoadButton(Player player, Board board) {
		boolean hasCards = player.getHand().canBuildRoad();
		boolean hasPieces = player.getPieces().hasRoad();
		boolean hasLocation = board.canBuildRoadSomewhere(player);
		this.buildRoadButton.setActive(hasCards && hasPieces && hasLocation);
	}
	
	private void validateBuildSettlementButton(Player player, Board board) {
		boolean hasCards = player.getHand().canBuildSettlement();
		boolean hasPieces = player.getPieces().hasSettlement();
		boolean hasLocation = board.canBuildSettlementSomewhere(player);
		this.buildSettlementButton.setActive(hasCards && hasPieces && hasLocation);
	}
	
	private void validateBuildCityButton(Player player, Board board) {
		boolean hasCards = player.getHand().canBuildCity();
		boolean hasPieces = player.getPieces().hasCity();
		boolean hasLocation = board.canBuildCitySomewhere(player);
		this.buildCityButton.setActive(hasCards && hasPieces && hasLocation);
	}
	
	private void validateBuildDevCardButton(Player player, DevelopmentCardDeck deck) {
		boolean hasCards = player.getHand().canBuildDevCard();
		boolean deckHasCardsLeft = deck.hasCardsLeft();
		this.buildDevCardButton.setActive(hasCards && deckHasCardsLeft);
	}
	
	public void showCancelButton() {
		for(ActionButton button : buttons) {
			button.setHidden(true);
		}
		cancelButton.setHidden(false);
	}
	
	public void hideCancelButton() {
		for(ActionButton button : buttons) {
			button.setHidden(false);
		}
		cancelButton.setHidden(true);
	}
	
	private ActionButton createCancelButton() {
		ActionButton b = new ActionButton("Cancel", Action.Cancel, new Point(START_X, START_Y));
		b.setHidden(true);
		return b;
	}
	
	private ArrayList<ActionButton> createActionButtons() {
		ArrayList<ActionButton> b = new ArrayList<ActionButton>();
		buildRoadButton = new ActionButton("Build Road", Action.BuildRoad, new Point(START_X, START_Y));
		buildSettlementButton = new ActionButton("Build Settlement", Action.BuildSettlement, new Point(START_X + ActionButton.BUTTON_WIDTH + BUTTON_GAP, START_Y));
		buildCityButton = new ActionButton("Build City", Action.BuildCity, new Point(START_X, START_Y + ActionButton.BUTTON_HEIGHT + BUTTON_GAP));
		buildDevCardButton = new ActionButton("Build Development Card", Action.BuildDevCard, new Point(START_X + (ActionButton.BUTTON_WIDTH + BUTTON_GAP), START_Y + (ActionButton.BUTTON_HEIGHT + BUTTON_GAP)));
		exchangeCardsButton = new ActionButton("Exchange Cards", Action.ExchangeCards, new Point(START_X, START_Y + (ActionButton.BUTTON_HEIGHT + BUTTON_GAP)*2));
		playDevCardButton = new ActionButton("Play Development Card", Action.PlayDevCard, new Point(START_X + (ActionButton.BUTTON_WIDTH + BUTTON_GAP), START_Y + (ActionButton.BUTTON_HEIGHT + BUTTON_GAP)*2));
		rollDiceButton = new ActionButton("Roll Dice", Action.RollDice, new Point(START_X, START_Y + (ActionButton.BUTTON_HEIGHT + BUTTON_GAP)*3));
		doneWithTurnButton = new ActionButton("Done With Turn", Action.DoneWithTurn, new Point(START_X + (ActionButton.BUTTON_WIDTH + BUTTON_GAP), START_Y + (ActionButton.BUTTON_HEIGHT + BUTTON_GAP)*3));
		
		b.add(buildRoadButton);
		b.add(buildSettlementButton);
		b.add(buildCityButton);
		b.add(buildDevCardButton);
		b.add(exchangeCardsButton);
		b.add(playDevCardButton);
		b.add(rollDiceButton);
		b.add(doneWithTurnButton);
		
		return b;
	}
	
}
