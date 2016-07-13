package org.framework;

import org.framework.interfaces.GameObj;
/**
 * TODO
 *
 * @author Wesley
 */
public class MainLoopAdvancedInterface {
	
	// TODO getActions, getObjs
	// TODO check name matching on mainloop callbacks
	// TODO better msgs on invalid action creation
	
	// the MainLoop being modified by this interface
	private MainLoop loop;
	
	protected MainLoopAdvancedInterface(MainLoop loop) {
		this.loop = loop;
	}
	
	//////////////////////////////////////////////////
	// Public Interface
	//////////////////////////////////////////////////
	
	/**
	 * TODO
	 * @param action
	 * @param actionGroup
	 * @throws IllegalArgumentException if actionGroup < 0, action == null
	 */
	public void insertAction(MainLoopAction action, int actionGroup) {
		loop.insertAction(action, actionGroup);
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
		loop.deleteAction(action);
	}
	
	// TODO
	public void deleteAllActions() {
		loop.deleteAllActions();
	}
	
	// TODO
	public boolean contains(GameObj obj) {
		return loop.containsAdv(obj);
	}
	
	//////////////////////////////////////////////////
	// Actions
	//////////////////////////////////////////////////

	/**
	 * TODO
	 * @param obj
	 * @param priority
	 * @param layer
	 * @return
	 * @throws IllegalArguementException if obj == null, priority < 0, layer < 0
	 */
	public MainLoopAction createAddAction(GameObj obj, int priority, int layer) {
		if (obj == null || priority < 0 || layer < 0)
			throw new IllegalArgumentException("Illegal Add Action creation");
		return new MainLoopAddAction(obj, priority, layer);
	}
	
	/**
	 * TODO
	 * @param obj
	 * @return
	 * @throws IllegalArgumentException if obj == null
	 */
	public MainLoopAction createRemoveAction(GameObj obj) {
		if (obj == null)
			throw new IllegalArgumentException("Illegal Remove Action creation");
		return new MainLoopRemoveAction(obj);
	}

	// TODO
	public MainLoopAction createClearAction() {
		return new MainLoopClearAction();
	}
	
	// TODO
	public static abstract class MainLoopAction {
		protected void acceptResolution(MainLoop loop) {
			loop.visitResolution(this);
		}
	}
	
	protected class MainLoopAddAction extends MainLoopAction {
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
	
	protected class MainLoopRemoveAction extends MainLoopAction {
		private GameObj obj;
		
		protected MainLoopRemoveAction(GameObj obj) {
			this.obj = obj;
		}
		
		protected GameObj getObj() {
			return obj;
		}
	}
	
	protected class MainLoopClearAction extends MainLoopAction {
		
		protected MainLoopClearAction() {
			// nothing!
		}
	}
}