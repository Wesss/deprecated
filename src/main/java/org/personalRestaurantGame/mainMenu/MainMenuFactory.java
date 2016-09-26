package org.personalRestaurantGame.mainMenu;

import org.framework.mainLoop.MainLoopCustomGroupsInterface;
import org.gameUtil.ButtonList;
import org.gameUtil.CountdownEvent;
import org.personalRestaurantGame.RestaurantGame;

import static org.personalRestaurantGame.mainMenu.MainMenu.*;

public class MainMenuFactory {

    public static MainMenu getMainMenu(RestaurantGame game, MainLoopCustomGroupsInterface mainLoop) {
        ButtonList buttons = new ButtonList(
                new NewGameButton(game, X, Y_TOP, BUTTON_WIDTH, BUTTON_HEIGHT),
                new ExitButton(new CountdownEvent(60, game::exit), X, Y_TOP + BUTTON_HEIGHT + Y_MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT));
        mainLoop.createGroup(0, 0).add(buttons);
        return new MainMenu(buttons);
    }


}
