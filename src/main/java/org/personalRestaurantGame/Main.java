package org.personalRestaurantGame;

import org.framework.GameFramework;

public class Main {
    public static void main(String[] args) {
        GameFramework.startGame(new RestaurantGameFactory(), RestaurantGame.FPS);
    }
}
