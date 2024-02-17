import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class Player {

	private Color pieceColor;
	private Hand hand;
	private DevelopmentCardStash devCards;
	private PieceRepository pieces;
	private String name;
	protected int score;
	protected int freeSettlements;
	protected Intersection lastInitialSettlement;
	protected ArrayList<HarborType> tradeDeals;
	
	public Player(Color color, String name) {
		this.pieceColor = color;
		this.hand = new Hand();
		this.devCards = new DevelopmentCardStash();
		this.pieces = new PieceRepository();
		this.freeSettlements = 2;
		this.name = name;
		this.tradeDeals = new ArrayList<HarborType>();
	}
	
	public String getName() {
		return this.name;
	}
	
	public Color getPieceColor() {
		return this.pieceColor;
	}
	
	public void mouseClicked(Point p, ActionButtonContainer buttons, TurnManager tm) {
		this.hand.mouseClicked(p);
		buttons.validateExchangeCardsButton(this);
		
		this.devCards.mouseClicked(p);
		buttons.validatePlayDevelopmentCardButton(this, tm);
	}
	
	public void draw(Graphics2D g) {
		this.hand.draw(g);
		this.devCards.draw(g);
	}
	
	public void unSelectHand() {
		this.hand.clearSelection();
	}
	
	public void unSelectDevCards() {
		this.devCards.clearSelection();
	}
	
	public boolean hasPlayableDevCardSelected() {
		return devCards.hasPlayableDevCardSelected();
	}
	
	public DevelopmentCardType playSelectedDevCard() {
		return this.devCards.playSelectedCard();
	}
	
	public void addResource(Resource r) {
		this.hand.addCardOfResourceType(r);
	}
	
	public int removeAllCardsOfType(Resource r) {
		return this.hand.removeAllCardsOfType(r);
	}
	
	public boolean canBuildSettlement() {
		return this.pieces.hasSettlement() && (hasInitialSettlementLeft() || this.hand.canBuildSettlement());
	}
	
	public boolean canBuildCity() {
		return this.pieces.hasCity() && this.hand.canBuildCity();
	}
	
	public boolean canBuildFreeRoad() {
		return this.pieces.hasRoad();
	}
	
	public boolean canBuildRoad() {
		return this.pieces.hasRoad() && (hasInitialSettlementLeft() || this.hand.canBuildRoad());
	}
	
	public boolean canBuildDevCard(DevelopmentCardDeck deck) {
		return deck.hasCardsLeft() && this.hand.canBuildDevCard();
	}
	
	public void buildRoad(Pathway pathway, boolean free) {
		System.out.println("Building road");
		
		if(!hasInitialSettlementLeft() && !free) {
			this.hand.buildRoad();
		}
		this.pieces.useRoad();
		pathway.buildRoad(this);
		
		//Use up free settlement after building road for settlement
		if(hasInitialSettlementLeft()) {
			freeSettlements--;
		}
	}
	
	public void buildSettlement(Intersection intersection) {
		if(!hasInitialSettlementLeft()) {
			this.hand.buildSettlement();
		} else if(freeSettlements == 1) {
			for(HexTile tile : intersection.getHexTiles()) {
				this.hand.addCardOfResourceType(tile.getResource());
			}
		}
		
		this.pieces.useSettlement();
		intersection.buildSettlement(this);
		
		if(hasInitialSettlementLeft()) {
			this.lastInitialSettlement = intersection;
		}
		
		if(intersection.touchesPathwayWithHarbor()) {
			this.tradeDeals.add(intersection.getAdjacentHarbor());
		}
	}
	
	public void buildCity(Intersection intersection) {
		this.hand.buildCity();
		this.pieces.useCity();
		intersection.buildCity(this);
	}
	
	public void buildDevelopmentCard(DevelopmentCardDeck deck) {
		this.hand.buildDevCard();
		DevelopmentCard d = deck.drawCard();
		System.out.println(d.getType());
		this.devCards.addCard(d);
	}
	
	public Hand getHand() {
		return this.hand;
	}
	
	public DevelopmentCardStash getDevCardStash() {
		return this.devCards;
	}
	
	public PieceRepository getPieces() {
		return this.pieces;
	}
	
	public boolean canExchangeSelectedCards() {
		return this.hand.canExchangeSelectedCards(this.tradeDeals);
	}
	
	public boolean exchangeSelectedCards(Resource r) {
		if(this.hand.getSelectedType() != r) {
			this.hand.exchangeSelectedCards(r);
			return true;
		}
		return false;
	}
	
	public boolean hasInitialSettlementLeft() {
		return this.freeSettlements > 0;
	}
	
	public boolean isLastInitialSettlementLocation(Intersection intersection) {
		System.out.println(lastInitialSettlement);
		return intersection == lastInitialSettlement;
	}
	
	public int calculateScore(Board board) {
		int devCardPoints = this.devCards.getPoints(this);
		int boardPoints = board.getPoints(this);
		
		this.score = devCardPoints + boardPoints;
		return this.score;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public static void rob(Player takeFrom, Player addTo) {
		ResourceCard stolen = takeFrom.getHand().removeRandomCard();
		if(stolen != null) {
			addTo.getHand().addCardOfResourceType(stolen.getResource());
		}
	}
	
}
