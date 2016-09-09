package org.personalRestaurantGame.mainMenu;

import org.personalRestaurantGame.RestaurantGame;

import static org.personalRestaurantGame.RestaurantGame.State.NEW_GAME;

public class NewGameButton extends Button {

    private RestaurantGame game;

    protected NewGameButton(RestaurantGame game) {
        this.game = game;
    }

    @Override
    public void fireEvent() {
        game.swapState(NEW_GAME);
    }
}
