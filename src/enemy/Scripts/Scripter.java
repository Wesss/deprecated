package enemy.scripts;

import game.DodgerGame;

import java.awt.Graphics;
import java.util.Queue;

import overhead.MainLoop;
import overhead_interfaces.GameObj;

/**
 * Runs Overhead involving script execution
 * 
 * @author Wesley Cox
 * @last_edited 3/24/16
 */
public class Scripter implements GameObj {
	
	//////////////////////////////////////////////////
	// Definition
	//////////////////////////////////////////////////
	
	private static int LEVEL_END_BUFFER_FRAMES = 240; //4 seconds
	
	/**
	 * TODO
	 */
	
	private DodgerGame game;
	private Queue<Script> levelQueue;
	private boolean inLevel;
	private Script curScript;
	private int curScriptFrame;
	
	/**
	 * TODO
	 * @param levelQueue the levels to be run
	 */
	public Scripter(DodgerGame game, Queue<Script> levelQueue) {
		this.game = game;
		this.levelQueue = levelQueue;
		startNext();
		MainLoop.add(this);
	}
	
	//////////////////////////////////////////////////
	// Level handling
	//////////////////////////////////////////////////
	
	/**
	 * If more levels are still queued up, startNext starts the next level
	 * Otherwise, Lets DodgerGame know that no more levels are queued
	 * TODO
	 */
	private void startNext() {
		if (levelQueue.isEmpty()) {
			inLevel = false;
			game.menuMode(); //TODO create different function call
		} else {
			inLevel = true;
			curScript = levelQueue.remove();
			Scripts.activate(curScript, this);
			curScriptFrame = 0;
		}
	}
	
	/**
	 * Ends the current Script. Sets up the next Script to be run if it exists, otherwise notifies DodgerGame for out of levels
	 */
	public void end() {
		inLevel = false;
		curScriptFrame = 0;
	}
	
	//////////////////////////////////////////////////
	// Update
	//////////////////////////////////////////////////

	@Override
	public void update() {
		curScriptFrame++;
		if (inLevel) {
			curScript.update(curScriptFrame);
		} else {
			if (curScriptFrame == LEVEL_END_BUFFER_FRAMES) {
				startNext();
			}
		}
			
	}

	@Override
	public void draw(Graphics g) {}
}
