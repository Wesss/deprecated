package org.personalRestaurantGame.mainMenu;

import org.framework.mainLoop.MainLoopCustomGroupsInterface;
import org.gameUtil.Button;
import org.gameUtil.ButtonList;
import org.gameUtil.CountdownEvent;
import org.personalRestaurantGame.RestaurantGame;

import static org.personalRestaurantGame.RestaurantGame.State.*;
import static org.personalRestaurantGame.mainMenu.MainMenuModel.*;

public class MainMenuFactory {

    public static MainMenu getMainMenu(RestaurantGame game, MainLoopCustomGroupsInterface mainLoop) {

        Button newGameButton = new Button(
                new CountdownEvent(TRANSITION_OUT_CYCLES, () -> game.swapState(NEW_GAME)),
                X, Y_TOP, BUTTON_WIDTH, BUTTON_HEIGHT,
                "New Game");
        Button quitButton = new Button(
                new CountdownEvent(0, game::exit),
                X, Y_TOP + BUTTON_HEIGHT + Y_MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT,
                "Quit");

        ButtonList buttons = new ButtonList(newGameButton, quitButton);
        mainLoop.createGroup(DEFAULT_PRIORITY, FOREGROUND_LAYER).add(buttons);
        mainLoop.createGroup(DEFAULT_PRIORITY, MASK_LAYER); // TODO add in fade out mask

        MainMenuModel model = new MainMenuModel(buttons);
        MainMenuController controller = new MainMenuController(model);
        MainMenuEventListener listener = new MainMenuEventListener(controller);
        return new MainMenu(model, controller, listener);
    }
}
