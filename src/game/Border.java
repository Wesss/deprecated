package game;

import java.awt.Graphics;

import overhead_interfaces.GameObj;

/**
 * TODO
 * 
 * @author Wesley Cox
 * @last_edited Summer 2015
 */
public class Border implements GameObj {

	/****************************** Class level methods ***********************************/

	private static final int TOP_MARGIN = 100;
	private static final int RIGHT_MARGIN = 100;
	private static final int BOT_MARGIN = 100;
	private static final int LEFT_MARGIN = 100;
	
	public static final int TOP_BORDER = TOP_MARGIN;
	public static final int RIGHT_BORDER = DodgerGame.PANEL_X - RIGHT_MARGIN;
	public static final int BOT_BORDER = DodgerGame.PANEL_Y - BOT_MARGIN;
	public static final int LEFT_BORDER = LEFT_MARGIN;
	
	/*public static void initiallize() {
		border = new Border();
	}*/
	
	/*public static Border getBorder() {
		if (border == null) {
			throw new RuntimeException("attempted to call getBorder() before initialization");
		}
		return border;
	}*/
	
	@Override
	public void draw(Graphics g) {
		g.drawLine(LEFT_BORDER, TOP_BORDER, LEFT_BORDER, BOT_BORDER);
		g.drawLine(LEFT_BORDER, TOP_BORDER, RIGHT_BORDER, TOP_BORDER);
		g.drawLine(RIGHT_BORDER, BOT_BORDER, RIGHT_BORDER, TOP_BORDER);
		g.drawLine(LEFT_BORDER, BOT_BORDER, RIGHT_BORDER, BOT_BORDER);
	}

	@Override
	public void update() {}
}
