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
	
	public Dice(Dice otherDice) {
		this.d1 = otherDice.d1;
		this.d2 = otherDice.d2;
		this.diceGui = otherDice.diceGui;
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
	
	public static int combosForNumber(int number) {
		if(number < 2 || number > 12) {
			return 0;
		}
		 return 6 - Math.abs(number - 7);
	}
	
}
