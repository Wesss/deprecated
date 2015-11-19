package enemy;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Queue;

import overhead_interfaces.GameObj;
import overhead.MainLoop;
import game.DodgerGame;

/**
 * TODO
 * 
 * @author Wesley Cox
 * @last_edited 11/18/15
 */
public abstract class Script implements GameObj{
	
	/**
	 * TODO turn this into an object that takes ScriptBehaviors?
	 */
	
	//////////////////////////////////////////////////
	// Static level
	//////////////////////////////////////////////////
	
	/**
	 * TODO
	 * @return
	 */
	public static Queue<Script> createScriptQueue() {
		Queue<Script> result = new LinkedList<Script>();
		result.add(new Script01());
		result.add(new Script02());
		return result;
	}
	
	/**
	 * Initializes and runs the given script
	 * @param script the script to be run
	 */
	public static void run(Script script) {
		script.stepCount = 0;
		script.game = DodgerGame.getDodgerGame();
		MainLoop.add(script);
	}
	
	/**
	 * Terminates the given script
	 * @param script the script to remove
	 */
	public static void end(Script script) {
		MainLoop.remove(script);
	}
	
	//////////////////////////////////////////////////
	// Definition
	//////////////////////////////////////////////////
	
	public int stepCount; //represents the current mainloop cycle since this Script's run frame
	private DodgerGame game;
	
	/**
	 * TODO
	 */
	public abstract void step();
	
	/**
	 * returns the amount of MainLoop cycles needed for this script to reach completion
	 */
	public abstract int endStep();
	
	public void update() {
		step();
		stepCount++;
		if (stepCount >= endStep())
			game.nextLevel();
	}
	
	//No painting needed
	public void draw(Graphics g) {}
	
	//////////////////////////////////////////////////
	// private functions
	//////////////////////////////////////////////////
	
	/**
	 * @param low the lower step bound
	 * @param high the higher step bound
	 * @return true when current stepCount falls in between given low and high values
	 */
	protected boolean between(int low, int high) {
		return (low <= stepCount && stepCount <= high);
	}
}
