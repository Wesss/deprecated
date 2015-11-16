package game;

import java.awt.Graphics;

import overhead.Game;
import overhead_interfaces.GameObj;

/**
 * TODO
 * 
 * @author Wesley Cox
 * @last_edited Summer 2015
 */
public class Menu implements GameObj {
	
	//***************************** Static Level **********************************//
	
	private static final int MENU_START_X = Game.PANEL_X / 3 - 100;
	private static final int MENU_START_Y = Game.PANEL_Y / 3;
	private static final int CONTROLS_START_X = Game.PANEL_X / 3 + 100;
	private static final int CONTROLS_START_Y = MENU_START_Y;
	private static final int NEW_LINE_MARGIN = 20;
	
	//***************************** Instance Level *********************************//
	
	private DodgerGame game;
	public Menu(DodgerGame game) {
		this.game = game;
	}
	
	//***************************** Manipulation Methods ******************************//

	private static int curSelection = 0;
	
	public void up() {
		curSelection = positiveMod(curSelection - 1, 2);
	}
	
	public void down() {
		curSelection = positiveMod(curSelection + 1, 2);
	}
	
	public void select() {
		switch (curSelection) {
		case 0 :
			game.gameMode();
			break;
		case 1 :
			System.exit(0);
		}
	}
	
	private static int positiveMod(int num, int mod) {
		return ((num % mod) + mod) % mod;
	}
	
    //No update is needed
	@Override
	public void update() {}

	//******************************** Paint Methods *********************************//
	
	@Override
	public void draw(Graphics g) {
		drawControls(g);
		g.drawString("----Menu----", MENU_START_X, MENU_START_Y);
		g.drawString("New Game", MENU_START_X, MENU_START_Y + NEW_LINE_MARGIN);
		g.drawString("Exit", MENU_START_X, MENU_START_Y + 2 * NEW_LINE_MARGIN);
		g.fillOval(MENU_START_X - 13, MENU_START_Y + (1 + curSelection) * NEW_LINE_MARGIN - 8, 7, 7);
	}
	
	public void drawControls(Graphics g) {
		g.drawString("----Controls----", CONTROLS_START_X, CONTROLS_START_Y);
		g.drawString("Arrow Keys to move", CONTROLS_START_X, CONTROLS_START_Y + NEW_LINE_MARGIN);
		g.drawString("Shift to slow down", CONTROLS_START_X, CONTROLS_START_Y + 2 * NEW_LINE_MARGIN);
	}

}
