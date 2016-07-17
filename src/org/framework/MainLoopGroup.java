package org.framework;

import org.framework.interfaces.GameObj;

/**
 * This class represents a grouping of GameObjs currently stored within the MainLoop
 *
 * @author Wesley
 */
public class MainLoopGroup {
	
	// TODO determine which classes/methods need to be public/private/protected
	// TODO figure out mainloop callback to this Group

	/**
	 * 
	 * @param obj
	 * @throws IllegalArgumentException if obj is part of the mainloop already?
	 */
	public void add(GameObj obj) {
		// TODO
	}
	
	// TODO
	public void remove(GameObj obj) {
		// TODO
	}
	
	// TODO
	public boolean contains(GameObj obj) {
		// TODO
		return false;
	}
	
	/**
	 * Clears all objs in this group from the mainLoop and from this group on the next frame
	 */
	public void markClear() {
		// TODO
	}
	
	/**
	 * TODO
	 */
	public void addPostClear() {
		// TODO
	}
}
