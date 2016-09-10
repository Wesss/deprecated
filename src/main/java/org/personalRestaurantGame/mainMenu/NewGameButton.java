package org.personalRestaurantGame.mainMenu;

import org.personalRestaurantGame.RestaurantGame;
import util.Button;

import static org.personalRestaurantGame.RestaurantGame.State.NEW_GAME;

public class NewGameButton extends Button {

    private RestaurantGame game;

    protected NewGameButton(RestaurantGame game) {
        super(0, 0, 0, 0); // TODO
        this.game = game;
    }

    @Override
    public void fireEvent() {
        game.swapState(NEW_GAME);
    }
}
