package org.personalRestaurantGame.mainMenu;

import org.framework.mainLoop.MainLoopController;
import org.personalRestaurantGame.RestaurantGame;
import util.ButtonList;

public class MainMenuFactory {

    public static MainMenuEventAcceptor initMainMenu(RestaurantGame game, MainLoopController loop) {
        ButtonList buttons = new ButtonList(new NewGameButton(game), new ExitButton(game));

        MainMenu menu = new MainMenu(buttons);
        loop.addPostClear(menu);
        return new MainMenuEventAcceptor(menu);
    }
}
