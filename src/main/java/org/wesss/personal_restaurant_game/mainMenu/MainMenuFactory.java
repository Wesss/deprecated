package org.wesss.personal_restaurant_game.mainMenu;

import org.wesss.game_framework.mainLoop.MainLoopCustomGroupsInterface;
import org.wesss.game_framework.mainLoop.MainLoopGroup;
import org.wesss.game_utils.Button;
import org.wesss.game_utils.ButtonList;
import org.wesss.game_utils.GameCallback;
import org.wesss.personal_restaurant_game.RestaurantGame;

public class MainMenuFactory {

    public static MainMenu getMainMenu(RestaurantGame game, MainLoopCustomGroupsInterface mainLoop) {
        MainLoopGroup foregroundGroup = mainLoop.createGroup(DEFAULT_PRIORITY, FOREGROUND_LAYER);
        MainLoopGroup maskGroup = mainLoop.createGroup(DEFAULT_PRIORITY, MASK_LAYER);

        MainMenuModel model = new MainMenuModel();
        Button newGameButton = new Button(
                new GameCallback(0, model::newGameButton),
                X, Y_TOP, BUTTON_WIDTH, BUTTON_HEIGHT,
                "New Game");
        Button quitButton = new Button(
                new GameCallback(0, model::quitButton),
                X, Y_TOP + BUTTON_HEIGHT + Y_MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT,
                "Quit");

        ButtonList buttons = new ButtonList(newGameButton, quitButton);
        foregroundGroup.add(buttons);

        model.setReferences(game, foregroundGroup, maskGroup, buttons);
        MainMenuEventListener listener = new MainMenuEventListener(model);
        return new MainMenu(model, listener);
    }
}
