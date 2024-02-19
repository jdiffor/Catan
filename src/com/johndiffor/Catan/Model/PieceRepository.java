package com.johndiffor.Catan.Model;

public class PieceRepository {

	private int numRoads = 15;
	private int numSettlements = 5;
	private int numCities = 4;
	
	public PieceRepository() {
		
	}
	
	public boolean hasSettlement() {
		return numSettlements > 0;
	}
	
	public boolean hasRoad() {
		return numRoads > 0;
	}
	
	public boolean hasCity() {
		return numCities > 0;
	}
	
	public void useRoad() {
		numRoads--;
	}
	
	public void useSettlement() {
		numSettlements--;
	}
	
	public void useCity() {
		numCities--;
		numSettlements++;
	}
	
}
