package com.johndiffor.Catan.Model;

import java.util.ArrayList;

public class Intersection {

	private Pathway[] pathways;
	private Structure structure;
	private ArrayList<HexTile> hexTiles;
	
	public Intersection() {
		this.pathways = new Pathway[3];
		this.hexTiles = new ArrayList<HexTile>();
	}
	
	public void addPathway(Pathway p) {
		if(pathways[0] == null) {
			pathways[0] = p;
		}
		
		if(pathways[1] == null && !pathways[0].equals(p)) {
			pathways[1] = p;
		}
		
		if(pathways[2] == null && !pathways[0].equals(p) && !pathways[1].equals(p)) {
			pathways[2] = p;
		}
	}
	
	public void addHexTile(HexTile tile) {
		this.hexTiles.add(tile);
	}
	
	public ArrayList<HexTile> getHexTiles() {
		return this.hexTiles;
	}
	
	public boolean hasStructure() {
		return this.structure != null;
	}
	
	public Structure getStructure() {
		return this.structure;
	}
	
	public ArrayList<Pathway> getPathways() {
		ArrayList<Pathway> pathwayList = new ArrayList<Pathway>();
		if(pathways[0] != null) {
			pathwayList.add(pathways[0]);
		}
		
		if(pathways[1] != null) {
			pathwayList.add(pathways[1]);
		}
		
		if(pathways[2] != null) {
			pathwayList.add(pathways[2]);
		}
		return pathwayList;
	}
	
	public void buildSettlement(Player player) {
		this.structure = new Settlement(player);
	}
	
	public void buildCity(Player player) {
		this.structure = new City(player);
	}
	
	public boolean validForSettlement(Player player) {
		boolean initialSetup = player.hasInitialSettlementLeft();		
		boolean touchesOwnedRoad = false;
		for(int i = 0; i < pathways.length; i++) {
			if(pathways[i] != null) {
				if(pathways[i].hasIntersectionWithStructure()) {
					return false;
				}
				if(pathways[i].getRoad() != null && pathways[i].getRoad().getOwner() == player) {
					touchesOwnedRoad = true;
				}
			}
		}
		
		if(!initialSetup) {
			return touchesOwnedRoad;
		}
		
		return true;
	}
	
	public boolean oneAwayFromStructure() {
		for(int i = 0; i < pathways.length; i++) {
			if(pathways[i] != null) {
				if(pathways[i].hasIntersectionWithStructure()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean validForCity(Player player) {
		return structure != null && structure instanceof Settlement && structure.ownedBy(player);
	}
	
	public boolean playerCanBuildRoadFrom(Player player) {
		return (structure != null && structure.ownedBy(player)) || touchesOwnedRoad(player);
	}
	
	public boolean touchesPathwayWithHarbor() {
		for(int i = 0; i < pathways.length; i++) {
			if(pathways[i] != null && pathways[i].hasHarbor()) {
				return true;
			}
		}
		return false;
	}
	
	public HarborType getAdjacentHarbor() {
		for(int i = 0; i < pathways.length; i++) {
			if(pathways[i] != null && pathways[i].hasHarbor()) {
				return pathways[i].getHarbor().getType();
			}
		}
		return null;
	}
	
	public boolean touchesOwnedRoad(Player player) {
		for(int i = 0; i < pathways.length; i++) {
			if(pathways[i] != null && pathways[i].ownedBy(player)) {
				return true;
			}
		}
		return false;
	}
	
	
}
