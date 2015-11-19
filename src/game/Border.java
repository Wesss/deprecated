package game;

import java.awt.Graphics;

import overhead_interfaces.GameObj;

/**
 * TODO
 * 
 * @author Wesley Cox
 * @last_edited 11/15/15
 */
public class Border implements GameObj {
	
	//////////////////////////////////////////////////
	// Static level
	//////////////////////////////////////////////////

	private static final int TOP_MARGIN = 100;
	private static final int RIGHT_MARGIN = 100;
	private static final int BOT_MARGIN = 100;
	private static final int LEFT_MARGIN = 100;
	
	public static final int TOP_BORDER = TOP_MARGIN;
	public static final int RIGHT_BORDER = DodgerGame.PANEL_X - RIGHT_MARGIN;
	public static final int BOT_BORDER = DodgerGame.PANEL_Y - BOT_MARGIN;
	public static final int LEFT_BORDER = LEFT_MARGIN;
	

	//no update needed
	@Override
	public void update() {}
	
	//////////////////////////////////////////////////
	// Painting
	//////////////////////////////////////////////////
	
	@Override
	public void draw(Graphics g) {
		g.drawLine(LEFT_BORDER, TOP_BORDER, LEFT_BORDER, BOT_BORDER);
		g.drawLine(LEFT_BORDER, TOP_BORDER, RIGHT_BORDER, TOP_BORDER);
		g.drawLine(RIGHT_BORDER, BOT_BORDER, RIGHT_BORDER, TOP_BORDER);
		g.drawLine(LEFT_BORDER, BOT_BORDER, RIGHT_BORDER, BOT_BORDER);
	}
}
