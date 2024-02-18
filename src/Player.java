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
	protected int publicScore;
	protected int privateScore;
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
	
	public boolean canFulfillTrade(ArrayList<ResourceCard> desiredCards) {
		return this.hand.canFulfillTrade(desiredCards);
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
	
	public int unplayedDevCardCount() {
		return this.devCards.getUnplayedCards().size();
	}
	
	public PieceRepository getPieces() {
		return this.pieces;
	}
	
	public boolean hasTooManyCards() {
		return this.hand.size() > 7;
	}
	
	public boolean canExchangeSelectedCards() {
		return this.hand.canExchangeSelectedCards(this.tradeDeals);
	}
	
	public ArrayList<Resource> getSelectedCardTypes() {
		return this.hand.getSelectedCardTypes();
	}
	
	public ArrayList<ResourceCard> getSelectedCards() {
		return this.hand.getSelectedCards();
	}
	
	public boolean hasAnyCardsSelected() {
		return this.hand.hasAnyCardsSelected();
	}
	
	public boolean exchangeSelectedCards(Resource r) {
		if(this.hand.getSelectedType() != r) {
			this.hand.exchangeSelectedCards(r);
			return true;
		}
		return false;
	}
	
	public void discardSelectedCards() {
		this.hand.removeSelectedCards();
	}
	
	public boolean hasInitialSettlementLeft() {
		return this.freeSettlements > 0;
	}
	
	public boolean isLastInitialSettlementLocation(Intersection intersection) {
		System.out.println(lastInitialSettlement);
		return intersection == lastInitialSettlement;
	}
	
	// Returns actual score to determine if player has won
	public int updateScore(Board board, SpecialPoints specialPoints) {
		int devCardPointsPublic = this.devCards.getPointsPublic(this, specialPoints);
		int devCardPointsPrivate = this.devCards.getPointsPrivate(this, specialPoints);
		int boardPoints = board.getPoints(this, specialPoints);
		
		this.publicScore = devCardPointsPublic + boardPoints;
		this.privateScore = devCardPointsPrivate + boardPoints;
		
		return this.privateScore;
	}
	
	// Returns known score to display
	public int getKnownScore() {
		return this.publicScore;
	}
	
	// Returns actual score
	public int getRealScore() {
		return this.privateScore;
	}
	
	public int getUnplayedDevelopmentCardsCount() {
		return this.devCards.getUnplayedCards().size();
	}
	
	public static void rob(Player takeFrom, Player addTo) {
		ResourceCard stolen = takeFrom.getHand().removeRandomCard();
		if(stolen != null) {
			addTo.getHand().addCardOfResourceType(stolen.getResource());
		}
	}
	
}
