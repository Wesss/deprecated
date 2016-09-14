package org.personalRestaurantGame.mainMenu;

import org.personalRestaurantGame.RestaurantGame;
import org.personalRestaurantGame.model.GamePipeline;
import org.gameUtil.ButtonList;

public class MainMenuFactory {

    private static final int X = 500;
    private static final int Y_TOP = 100;
    private static final int Y_MARGIN = 50;
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 100;

    public static MainMenu getMainMenu(RestaurantGame game) {
        ButtonList buttons = new ButtonList(
                new NewGameButton(game, X, Y_TOP, BUTTON_WIDTH, BUTTON_HEIGHT),
                new ExitButton(game, X, Y_TOP + BUTTON_HEIGHT + Y_MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT));

        return new MainMenu(buttons);
    }

    public static void destroy(GamePipeline currentGamePipeline) {
        // TODO rewire this properly
    }
}
