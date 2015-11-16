package enemy;

import java.util.Random;

import random_number_generators.ModRandom;
import overhead.Game;
import enemy.Enemy.EnemyType;

/**
 * TODO
 * 
 * @author Wesley Cox
 * @last_edited Summer 2015
 */
public class Script02 extends Script {

	private static final int SECT_COUNT = 10;
	private static final int SECT_LENGTH_Y = Game.PANEL_Y / SECT_COUNT;
	
	private CircleSpawner spawn1;
	private CircleSpawner spawn2;
	private ModRandom sectRand;
	private Random rand;
	
	public Script02() {
		spawn1 = new CircleSpawner();
		spawn1.setRadius(5);
		spawn1.setSpeed(20);
		spawn1.setDir(180);
		spawn1.setX(Game.PANEL_X + 5);
		
		spawn2 = new CircleSpawner();
		spawn2.setRadius(10);
		spawn2.setSpeed(7);
		spawn2.setDir(0);
		spawn2.setX(-10);
		
		sectRand = new ModRandom(SECT_COUNT);
		rand = new Random();
	}
	
	@Override
	public void step() {
		if (between(50, 1300) && stepCount % 4 == 0) {
			spawn1.setY(sectRand.next() * SECT_LENGTH_Y + rand.nextInt(SECT_LENGTH_Y));
			spawn1.spawn();
		}
		if (stepCount == 400 || stepCount == 700) {
			for (int i = 0; i < 5; i++) {
				spawn2.setY(2 * i * SECT_LENGTH_Y + 20);
				spawn2.spawn();
			}
		}
		if (stepCount == 550) {
			for (int i = 0; i < 5; i++) {
				spawn2.setY((2 * i + 1) * SECT_LENGTH_Y + 20);
				spawn2.spawn();
			}
		}
	}

	@Override
	public int endStep() {
		return 1600;
	}

}
