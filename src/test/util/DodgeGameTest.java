package test.util;

import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Queue;

import overhead.MainLoop;
import overhead_interfaces.Game;
import enemy.Circle;
import enemy.scripts.Script;
import enemy.scripts.Script01;
import enemy.scripts.Script02;
import enemy.scripts.Scripter;
import game.Border;
import game.DodgeGame;
import game.Player;

/**
 * This class represents the dodging game itself
 * 
 * @author Wesley Cox
 * @last_edited 3/27/16
 */
public class DodgeGameTest implements DodgeGame {
	
	//////////////////////////////////////////////////
	// Definition
	//////////////////////////////////////////////////
	
	public static final int PANEL_X = 500;
	public static final int PANEL_Y = 500;
	public static final int FPS = 60;
	
	private MainLoop mainLoop;
	private Player player;
	
	//////////////////////////////////////////////////
	// Initialization
	//////////////////////////////////////////////////
	
	public DodgeGameTest(MainLoop mainLoop) {
		this.mainLoop = mainLoop;
		player = new Player(this);
		
		mainLoop.add(new Circle(115, 115, 0, 0, 10, mainLoop));
		mainLoop.add(player, 1);
		mainLoop.add(new Border(), 2);
	}
	
	//////////////////////////////////////////////////
	// Game state "Events"
	//////////////////////////////////////////////////
	
	@Override
	public void menuExitGame() {
		System.out.println("menuExitGame recieved");
		System.exit(0);
	}
	
	@Override
	public void menuStartGame() {
		System.out.println("menuStartGame recieved");
		System.exit(0);
	}
	
	@Override
	public void playerOutOfLives() {
		System.out.println("playerOutOfLives recieved");
		System.exit(0);
	}
	
	@Override
	public void scripterOutOfLevels() {
		System.out.println("scripterOutOfLevens recieved");
		System.exit(0);
	}
	
	//////////////////////////////////////////////////
	// Key-press Events
	//////////////////////////////////////////////////
	
	@Override
	public void keyPressed(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_UP :
			player.setUp(true);
			break;
		case KeyEvent.VK_LEFT :
			player.setLeft(true);
			break;
		case KeyEvent.VK_RIGHT :
			player.setRight(true);
			break;
		case KeyEvent.VK_DOWN :
			player.setDown(true);
			break;
		case KeyEvent.VK_SHIFT :
			player.setSlow(true);
			break;
		}
	}

	@Override
	public void keyReleased(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_UP :
			player.setUp(false);
			break;
		case KeyEvent.VK_LEFT :
			player.setLeft(false);
			break;
		case KeyEvent.VK_RIGHT :
			player.setRight(false);
			break;
		case KeyEvent.VK_DOWN :
			player.setDown(false);
			break;
		case KeyEvent.VK_SHIFT :
			player.setSlow(false);
			break;
		}
	}
	
	//The mouse is not used in this game
	@Override
	public void mousePressed(int x, int y, int button) {}
	@Override
	public void mouseReleased(int x, int y, int button) {}
	@Override
	public void mouseMoved(int x, int y) {}
}
