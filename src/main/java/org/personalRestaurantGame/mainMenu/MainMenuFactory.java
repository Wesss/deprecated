package org.personalRestaurantGame.mainMenu;

import org.framework.mainLoop.MainLoopController;
import org.personalRestaurantGame.RestaurantGame;
import util.Button;

import java.util.ArrayList;
import java.util.List;

public class MainMenuFactory {

    public static MainMenuEventAcceptor initMainMenu(RestaurantGame game, MainLoopController loop) {
        List<Button> buttons = new ArrayList<>();

        // TODO init buttons
        buttons.add(new NewGameButton(game));

        MainMenu menu = new MainMenu(buttons);
        loop.addPostClear(menu);
        return new MainMenuEventAcceptor(menu);
    }
}
