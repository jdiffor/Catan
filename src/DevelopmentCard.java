
public class DevelopmentCard extends Card {

	private DevelopmentCardType type;
	private boolean played;
	
	public DevelopmentCard(DevelopmentCardType type) {
		this.type = type;
		this.played = false;
	}
	
	public DevelopmentCardType getType() {
		return this.type;
	}
	
	public boolean upPlayed() {
		return !this.played;
	}
	
}
