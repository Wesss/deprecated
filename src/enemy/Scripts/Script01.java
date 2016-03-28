package enemy.scripts;

import enemy.CircleSpawner;
import game.DodgerGame;
import overhead.MainLoop;
import random_number_generators.ModRandom;

import java.util.Random;

/**
 * TODO
 * 
 * @author Wesley Cox
 * @last_edited 3/24/16
 */
public class Script01 implements Script{
	
	private static final int SECT_COUNT = 10;
	private static final int SECT_LENGTH_Y = DodgerGame.PANEL_Y / SECT_COUNT;
	//private static final int SECT_LENGTH_X = Game.PANEL_X / SECT_COUNT; not needed
	
	private CircleSpawner spawn1;
	private ModRandom sectRand;
	private Random rand;
	
	public Script01(MainLoop mainLoop) {
		spawn1 = new CircleSpawner(mainLoop);
		spawn1.setRadius(8);
		spawn1.setSpeed(20);
		
		sectRand = new ModRandom(SECT_COUNT);
		rand = new Random();
	}

	@Override
	public void update(int frame) {
		if (frame == 1) {
			spawn1.setDir(0);
			spawn1.setX(-10);
		}
		if (Scripts.between(50, frame, 900) && frame % 5 == 0) {
			spawn1.setY(sectRand.next() * SECT_LENGTH_Y + rand.nextInt(SECT_LENGTH_Y));
			spawn1.spawn();
		}
		if (frame == 500) {
			spawn1.setDir(180);
			spawn1.setX(DodgerGame.PANEL_X + 10);
		}
		if (frame == 1250) {
			Scripts.deactivate(this);
		}
	}
}
