package org.personalRestaurantGame.mainMenu;

import org.framework.mainLoop.MainLoopCustomGroupsInterface;
import org.framework.mainLoop.MainLoopGroup;
import org.gameUtil.Button;
import org.gameUtil.ButtonList;
import org.gameUtil.CountdownCallback;
import org.personalRestaurantGame.RestaurantGame;

import static org.personalRestaurantGame.RestaurantGame.State.NEW_GAME;
import static org.personalRestaurantGame.mainMenu.MainMenuModel.*;

public class MainMenuFactory {

    public static MainMenu getMainMenu(RestaurantGame game, MainLoopCustomGroupsInterface mainLoop) {
        MainLoopGroup foregroundGroup = mainLoop.createGroup(DEFAULT_PRIORITY, FOREGROUND_LAYER);
        MainLoopGroup maskGroup = mainLoop.createGroup(DEFAULT_PRIORITY, MASK_LAYER);

        MainMenuModel model = new MainMenuModel();
        Button newGameButton = new Button(
                new CountdownCallback(0, model::newGameButton),
                X, Y_TOP, BUTTON_WIDTH, BUTTON_HEIGHT,
                "New Game");
        Button quitButton = new Button(
                new CountdownCallback(0, model::quitButton),
                X, Y_TOP + BUTTON_HEIGHT + Y_MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT,
                "Quit");

        ButtonList buttons = new ButtonList(newGameButton, quitButton);
        foregroundGroup.add(buttons);

        model.setReferences(game, foregroundGroup, maskGroup, buttons);
        MainMenuEventListener listener = new MainMenuEventListener(model);
        return new MainMenu(model, listener);
    }
}
