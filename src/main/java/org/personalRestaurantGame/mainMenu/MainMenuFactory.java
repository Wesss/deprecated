package org.personalRestaurantGame.mainMenu;

import org.framework.mainLoop.MainLoopCustomGroupsInterface;
import org.gameUtil.Button;
import org.gameUtil.ButtonList;
import org.gameUtil.CountdownEvent;
import org.personalRestaurantGame.RestaurantGame;

import static org.personalRestaurantGame.RestaurantGame.State.*;

public class MainMenuFactory {

    public static MainMenu getMainMenu(RestaurantGame game, MainLoopCustomGroupsInterface mainLoop) {

        Button newGameButton = new Button(
                new CountdownEvent(MainMenuModel.TRANSITION_OUT_CYCLES, () -> game.swapState(NEW_GAME)),
                MainMenuModel.X, MainMenuModel.Y_TOP, MainMenuModel.BUTTON_WIDTH, MainMenuModel.BUTTON_HEIGHT,
                "New Game");
        Button quitButton = new Button(
                new CountdownEvent(0, game::exit),
                MainMenuModel.X, MainMenuModel.Y_TOP + MainMenuModel.BUTTON_HEIGHT + MainMenuModel.Y_MARGIN, MainMenuModel.BUTTON_WIDTH, MainMenuModel.BUTTON_HEIGHT,
                "Quit");

        ButtonList buttons = new ButtonList(newGameButton, quitButton);
        mainLoop.createGroup(0, 0).add(buttons);

        MainMenuModel model = new MainMenuModel(buttons);
        MainMenuController controller = new MainMenuController(model);
        MainMenuEventListener listener = new MainMenuEventListener(controller);
        return new MainMenu(model, controller, listener);
    }
}
