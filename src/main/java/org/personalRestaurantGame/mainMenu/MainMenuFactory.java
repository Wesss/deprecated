package org.personalRestaurantGame.mainMenu;

import org.personalRestaurantGame.RestaurantGame;

import java.util.ArrayList;
import java.util.List;

public class MainMenuFactory {

    public static MainMenu getMainMenu(RestaurantGame game) {
        List<Button> buttons = new ArrayList<>();

        // TODO init buttons
        buttons.add(new NewGameButton(game));

        return new MainMenu(buttons);
    }
}
