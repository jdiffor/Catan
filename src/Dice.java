import java.awt.Graphics2D;

public class Dice {

	private int d1;
	private int d2;
	private DiceGui diceGui;
	
	public Dice() {
		d1 = 1;
		d2 = 1;
		this.diceGui = new DiceGui(this);
	}
	
	public void draw(Graphics2D g) {
		diceGui.draw(g);
	}
	
	public int roll() {
		d1 = (int) (Math.random() *6) + 1;
		d2 = (int) (Math.random() *6) + 1; 
		
		return d1 + d2;
	}
	
	public int getD1() {
		return this.d1;
	}
	
	public int getD2() {
		return this.d2;
	}
	
}