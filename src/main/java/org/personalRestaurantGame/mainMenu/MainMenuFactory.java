package org.personalRestaurantGame.mainMenu;

import org.framework.mainLoop.MainLoopCustomGroupsInterface;
import org.gameUtil.Button;
import org.gameUtil.ButtonList;
import org.gameUtil.CountdownEvent;
import org.personalRestaurantGame.RestaurantGame;

import static org.personalRestaurantGame.RestaurantGame.State.*;
import static org.personalRestaurantGame.mainMenu.MainMenu.*;

public class MainMenuFactory {

    public static MainMenu getMainMenu(RestaurantGame game, MainLoopCustomGroupsInterface mainLoop) {

        Button newGameButton = new Button(
                new CountdownEvent(60, () -> game.swapState(NEW_GAME)),
                X, Y_TOP, BUTTON_WIDTH, BUTTON_HEIGHT,
                "New Game");
        Button quitButton = new Button(
                new CountdownEvent(60, () -> game.swapState(NEW_GAME)),
                X, Y_TOP + BUTTON_HEIGHT + Y_MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT,
                "Quit");

        ButtonList buttons = new ButtonList(newGameButton, quitButton);
        mainLoop.createGroup(0, 0).add(buttons);
        return new MainMenu(buttons);
    }
}
