package org.wesss.personal_restaurant_game;

import org.wesss.game_framework.GameFramework;

public class Main {
    public static void main(String[] args) {
        GameFramework.startGame(new RestaurantGameFactory(), RestaurantGame.FPS);
    }
}
