package org.framework;

import org.framework.interfaces.GameObj;

public class MainLoopAdvancedInterface {
	
	public void addAction(MainLoopAction action, int actionGroup) {
		// TODO
	}

	public boolean containsAction(MainLoopAction action) {
		// TODO
		return false;
	}
	
	public boolean containsAction(MainLoopAction action, int actionGroup) {
		// TODO
		return false;
	}
	
	public void removeAction(MainLoopAction action) {
		// TODO
	}
	
	public void clearActions() {
		// TODO
	}
	
	public void clearActionGroup(int actionGroup) {
		// TODO
	}
	
	public static interface MainLoopAction {
		// TODO
	}
	
	protected class MainLoopAddAction implements MainLoopAction {
		private GameObj obj;
		private int priority;
		private int layer;
		
		protected MainLoopAddAction() {
			//TODO
		}
		
		protected GameObj getObj() {
			//TODO
			return null;
		}
		
		protected int getPriority() {
			//TODO
			return -1;
		}
		
		protected int getLayer() {
			//TODO
			return -1;
		}
	}
	
	protected class MainLoopRemoveAction implements MainLoopAction {
		private GameObj obj;
		
		protected MainLoopRemoveAction() {
			//TODO
		}
		
		protected GameObj getObj() {
			//TODO
			return null;
		}
	}
	
	protected class MainLoopClearAction implements MainLoopAction {
		
		protected MainLoopClearAction() {
			// nothing!
		}
	}
}