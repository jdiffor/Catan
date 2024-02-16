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
	
	public void mouseClicked(Point p, ActionButtonContainer buttons) {
		this.hand.mouseClicked(p);
		buttons.validateExchangeCardsButton(this);
	}
	
	public void draw(Graphics2D g) {
		this.hand.draw(g);
	}
	
	public void unSelectHand() {
		this.hand.clearSelection();
	}
	
	public boolean hasUnplayedDevCard() {
		return devCards.hasUnplayedDevCard();
	}
	
	public void addResource(Resource r) {
		this.hand.addCardOfResourceType(r);
	}
	
	public boolean canBuildSettlement() {
		return this.pieces.hasSettlement() && (hasInitialSettlementLeft() || this.hand.canBuildSettlement());
	}
	
	public boolean canBuildCity() {
		return this.pieces.hasCity() && this.hand.canBuildCity();
	}
	
	public boolean canBuildRoad() {
		return this.pieces.hasRoad() && (hasInitialSettlementLeft() || this.hand.canBuildRoad());
	}
	
	public boolean canBuildDevCard(DevelopmentCardDeck deck) {
		return deck.hasCardsLeft() && this.hand.canBuildDevCard();
	}
	
	public void buildRoad(Pathway pathway) {
		System.out.println("Building road");
		
		if(!hasInitialSettlementLeft()) {
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
		this.devCards.addCard(deck.drawCard());
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
	
	public static void rob(Player takeFrom, Player addTo) {
		ResourceCard stolen = takeFrom.getHand().removeRandomCard();
		if(stolen != null) {
			addTo.getHand().addCardOfResourceType(stolen.getResource());
		}
	}
	
	public boolean hasInitialSettlementLeft() {
		return this.freeSettlements > 0;
	}
	
	public boolean isLastInitialSettlementLocation(Intersection intersection) {
		System.out.println(lastInitialSettlement);
		return intersection == lastInitialSettlement;
	}
	
}
