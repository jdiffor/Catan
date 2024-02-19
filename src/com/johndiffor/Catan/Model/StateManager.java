package com.johndiffor.Catan.Model;

public class StateManager {

	private ActionState actionState;
	
	public StateManager() {
		this.actionState = ActionState.RollingForTurn;
	}
	
	public void setActionState(ActionState s) {
		this.actionState = s;
	}
	
	public void clearActionState() {
		this.actionState = ActionState.YourTurn;
	}
	
	public ActionState getActionState() {
		return this.actionState;
	}
	
}
