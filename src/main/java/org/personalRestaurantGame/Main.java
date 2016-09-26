package org.personalRestaurantGame;

import org.framework.GameFramework;

public class Main {
    public static final int FPS = 60;

    public static void main(String[] args) {
        GameFramework.startGame(new RestaurantGameFactory(), FPS);
    }
}
