package org.framework;

import org.framework.interfaces.GameObj;

public class MainLoopAdvancedInterface {
	
	// the MainLoop being modified by this interface
	private MainLoop loop;
	
	protected MainLoopAdvancedInterface(MainLoop loop) {
		this.loop = loop;
	}
	
	// TODO
	public void insertAction(MainLoopAction action, int actionGroup) {
		loop.addAction(action, actionGroup);
	}

	// TODO
	public boolean containsAction(MainLoopAction action) {
		return loop.containsAction(action);
	}
	
	// TODO
	public boolean containsAction(MainLoopAction action, int actionGroup) {
		return loop.containsAction(action, actionGroup);
	}
	
	// TODO
	public void deleteAction(MainLoopAction action) {
		loop.removeAction(action);
	}
	
	// TODO
	public void deleteAllActions() {
		loop.clearActions();
	}
	
	// TODO
	public void deleteAllActionsGroup(int actionGroup) {
		loop.clearActionGroup(actionGroup);
	}
	
	// TODO
	public MainLoopAction createAddAction(GameObj obj, int priority, int layer) {
		return new MainLoopAddAction(obj, priority, layer);
	}
	
	// TODO
	public MainLoopAction createRemoveAction(GameObj obj) {
		return new MainLoopRemoveAction(obj);
	}

	// TODO
	public MainLoopAction createClearAction(GameObj obj) {
		return new MainLoopClearAction();
	}
	
	// TODO
	public static interface MainLoopAction {
		// TODO change to enum?
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
			return obj;
		}
		
		protected int getPriority() {
			return priority;
		}
		
		protected int getLayer() {
			return layer;
		}
	}
	
	protected class MainLoopRemoveAction implements MainLoopAction {
		private GameObj obj;
		
		protected MainLoopRemoveAction(GameObj obj) {
			this.obj = obj;
		}
		
		protected GameObj getObj() {
			return obj;
		}
	}
	
	protected class MainLoopClearAction implements MainLoopAction {
		
		protected MainLoopClearAction() {
			// nothing!
		}
	}
}