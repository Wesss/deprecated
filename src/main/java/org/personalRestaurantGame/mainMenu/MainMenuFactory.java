package org.personalRestaurantGame.mainMenu;

import org.framework.mainLoop.MainLoopController;
import org.personalRestaurantGame.RestaurantGame;
import org.personalRestaurantGame.model.GamePipeline;
import util.ButtonList;

public class MainMenuFactory {

    public static MainMenu getMainMenu(RestaurantGame game) {
        ButtonList buttons = new ButtonList(new NewGameButton(game), new ExitButton(game));

        return new MainMenu(buttons);
    }

    public static void destroy(GamePipeline currentGamePipeline) {
        // TODO rewire this properly
    }
}
