package org.personalRestaurantGame;

import org.framework.GameFramework;

public class Main {
    public static final int FPS = 60;

    public static void main(String[] args) throws InstantiationException {
        try {
            GameFramework.startGame(RestaurantGame.class, new MainEventListener(), FPS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
