package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import overhead.Overhead;
import overhead.MainLoop;
import overhead_interfaces.Game;
import overhead_interfaces.GameObj;
import test.util.DodgeGameTest;

/**
 * Test for Player Control, Border interaction
 * 
 * @author Wesley Cox
 * @last_edited 3/27/16
 */
public class PlayerTest {
	
	public static void main(String args[]) {
		Dimension dim = new Dimension(DodgeGameTest.PANEL_X, DodgeGameTest.PANEL_Y);
		Overhead.startGame(DodgeGameTest.class, DodgeGameTest.FPS, dim);
	}
}
