package enemy;

import java.util.Random;

import overhead.Game;
import random_number_generators.ModRandom;

/**
 * TODO
 * 
 * @author Wesley Cox
 * @last_edited Summer 2015
 */
public class Script01 extends Script{
	
	private static final int SECT_COUNT = 10;
	private static final int SECT_LENGTH_Y = Game.PANEL_Y / SECT_COUNT;
	//private static final int SECT_LENGTH_X = Game.PANEL_X / SECT_COUNT; not needed
	
	private CircleSpawner spawn1;
	private ModRandom sectRand;
	private Random rand;
	
	public Script01() {
		spawn1 = new CircleSpawner();
		spawn1.setRadius(8);
		spawn1.setSpeed(20);
		
		sectRand = new ModRandom(SECT_COUNT);
		rand = new Random();
	}
	
	@Override
	public int endStep() {
		return 1250;
	}
	
	@Override
	public void step() {
		if (stepCount == 1) {
			spawn1.setDir(0);
			spawn1.setX(-10);
		}
		if (between(50, 900) && stepCount % 5 == 0) {
			spawn1.setY(sectRand.next() * SECT_LENGTH_Y + rand.nextInt(SECT_LENGTH_Y));
			spawn1.spawn();
		}
		if (stepCount == 500) {
			spawn1.setDir(180);
			spawn1.setX(Game.PANEL_X + 10);
		}
	}
}
