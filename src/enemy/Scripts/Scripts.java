package enemy.scripts;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility Class for scripts package
 * 
 * @author Wesley
 * @last_edited 3/24/16
 */
public class Scripts {
	
	//TODO figure out a better way to do this
	private static Map<Script, Scripter> scriptToScripter = new HashMap<>();
	
	/**
	 * TODO
	 * @param script
	 * @param scripter
	 */
	public static void activate(Script script, Scripter scripter) {
		scriptToScripter.put(script, scripter);
	}
	
	
	/**
	 * TODO
	 * @param script
	 */
	public static void deactivate(Script script) {
		Scripter scripter = scriptToScripter.get(script);
		scriptToScripter.remove(script);
		scripter.end();
	}
	
	//returns True iff min <= frame <= max
	public static boolean between(int min, int frame, int max) {
		return (min <= frame && max >= frame);
	}
}
