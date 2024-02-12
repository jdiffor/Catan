import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class Utils {

	public static <T> boolean isCorner(int i, int j, T[][] arr) {
		return (i == 0 && j == 0) || (i == 0 && j == arr[0].length - 1) || (i == arr.length - 1 && j == 0) || (i == arr.length - 1 && j == arr[0].length - 1);
	}
	
	public static <T> boolean isInArrayRange(int i, int j, T[][] arr) {
		return i >= 0 && j >= 0 && i < arr.length && j < arr[0].length;
	}
	
	public static <T> void printArray2D(T[][] arr) {
		if(arr == null) {
			System.out.println("Array is null");
			return;
		}
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[0].length; j++) {
				System.out.print(arr[i][j] + ", ");
			}
			System.out.println();
		}
	}
	
	public static <T> boolean arrayContains(T[] arr, T item) {
		for(int i = 0; i < arr.length; i++) {
			if(arr[i].equals(item)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean intArrayContains(int[] arr, int item) {
		for(int i = 0; i < arr.length; i++) {
			if(arr[i] == item) {
				return true;
			}
		}
		return false;
	}
	
	public static <T> void addIfNotContains(ArrayList<T> arr, T item) {
		if(!arr.contains(item)) {
			arr.add(item);
		}
	}
	
	public static <T> void addAmountOfItemToList(T item, int amount, ArrayList<T> list) {
		for(int i = 0; i < amount; i++) {
			list.add(item);
		}
	}
	
	public static void drawCenteredString(Graphics2D g, String text, Point p, Font font) {
		FontMetrics metrics = g.getFontMetrics(font);
		int x = (int) (p.getX() - metrics.stringWidth(text) / 2);
		int y = (int) (p.getY() - metrics.getHeight() / 2 + metrics.getAscent());
		g.setFont(font);
		g.drawString(text, x, y);
	}
	
	public static Color averageColor(Color c1, Color c2) {
		return new Color((c1.getRed() + c2.getRed()) / 2,
				(c1.getGreen() + c2.getGreen()) / 2,
				(c1.getBlue() + c2.getBlue()) / 2);
	}
}
