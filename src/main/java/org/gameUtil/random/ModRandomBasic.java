package org.gameUtil.random;

import java.util.Random;

/**
 * This object returns biased spread of random integers using java's default random class
 */
public class ModRandomBasic {
    private int marbleTypeCount;

    //represents chance to be chosen for each marble type, increases when not picked
    private int[] marbles;

    private Random random;

    //Creates a new ModRandom object to return a stream of random integers from 0 to count,
    //in a controlled spread.
    public ModRandomBasic(int count) {
        if (count < 1)
            throw new IllegalArgumentException("Illegal Parameters on ModRandom");

        marbleTypeCount = count;
        random = new Random();

        marbles = new int[marbleTypeCount];
        for (int i = 0; i < marbleTypeCount; i++) {
            marbles[i] = 1;
        }
    }

    //returns a random integer 
    public int next() {
        int[] maxOfType = new int[marbleTypeCount];
        int[] minOfType = new int[marbleTypeCount];

        //setup "marble bag"
        int marbleCount = 0;
        for (int i = 0; i < marbleTypeCount; i++) {
            minOfType[i] = marbleCount;
            marbleCount = marbleCount + marbles[i];
            maxOfType[i] = marbleCount - 1;
        }

        //pick a marble
        int chosenMarble = random.nextInt(marbleCount);

        int chosenValue = -1; //does not exist
        for (int i = 0; chosenValue < 0; i++) {
            if (chosenMarble >= minOfType[i] && chosenMarble <= maxOfType[i]) {
                chosenValue = i;
            }
        }

        //modify chances
        if (marbles[chosenValue] > 1) {
            marbles[chosenValue] = marbles[chosenValue] - 1;
        } else {
            for (int i = 0; i < marbleTypeCount; i++) {
                if (i != chosenValue) {
                    marbles[i] = marbles[i]++;
                }
            }
        }
        //return number that the chosen marble represents
        return chosenValue;
    }
}
