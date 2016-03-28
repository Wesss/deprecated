package game;

import java.awt.Dimension;
import overhead.Overhead;

/**
 * Kicks off the DodgerGame game
 * 
 * @author Wesley Cox
 * @last_edited 3/27/16
 */
public class Main {
	
	public static void main(String args[]) {
		Dimension dim = new Dimension(DodgerGame.PANEL_X, DodgerGame.PANEL_Y);
		Overhead.startGame(DodgerGame.class, DodgerGame.FPS, dim);
		while(true) {}
	}
}
