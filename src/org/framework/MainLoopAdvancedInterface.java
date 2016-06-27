package org.framework;

import org.framework.MainLoopAdvancedInterface.MainLoopAction;
import org.framework.interfaces.GameObj;

public class MainLoopAdvancedInterface {
	
	// the MainLoop being modified by this interface
	private MainLoop loop;
	
	protected MainLoopAdvancedInterface(MainLoop loop) {
		this.loop = loop;
	}
	
	public void addAction(MainLoopAction action, int actionGroup) {
		loop.addAction(action, actionGroup);
	}

	public boolean containsAction(MainLoopAction action) {
		return loop.containsAction(action);
	}
	
	public boolean containsAction(MainLoopAction action, int actionGroup) {
		return loop.containsAction(action, actionGroup);
	}
	
	public void removeAction(MainLoopAction action) {
		loop.removeAction(action);
	}
	
	public void clearActions() {
		loop.clearActions();
	}
	
	public void clearActionGroup(int actionGroup) {
		loop.clearActionGroup(actionGroup);
	}
	
	public static interface MainLoopAction {
		// TODO change to enum
	}
	
	protected class MainLoopAddAction implements MainLoopAction {
		private GameObj obj;
		private int priority;
		private int layer;
		
		protected MainLoopAddAction(GameObj obj, int priority, int layer) {
			this.obj = obj;
			this.priority = priority;
			this.layer = layer;
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
		
		protected MainLoopRemoveAction(GameObj obj) {
			this.obj = obj;
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