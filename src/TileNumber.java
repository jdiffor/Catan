
public class TileNumber {

	private int number;
	
	public TileNumber(int num) {
		assert(num >= 2 && num <= 12 && num != 7);
		this.number = num;
	}
	
	public int getNumber() {
		return this.number;
	}
	
	public String getNumberString() {
		return this.number + "";
	}
	
}
