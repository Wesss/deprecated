package org.personalRestaurantGame;

import org.framework.mainLoop.MainLoopCustomGroupsInterface;
import org.gameUtil.ButtonList;
import org.personalRestaurantGame.mainMenu.ExitButton;
import org.personalRestaurantGame.mainMenu.MainMenu;
import org.personalRestaurantGame.mainMenu.NewGameButton;
import org.personalRestaurantGame.model.GamePipeline;

import static org.personalRestaurantGame.mainMenu.MainMenu.*;

public class GamePipelineFactory {

    public static MainMenu getMainMenu(RestaurantGame game, MainLoopCustomGroupsInterface mainLoop) {
        ButtonList buttons = new ButtonList(
                new NewGameButton(game, X, Y_TOP, BUTTON_WIDTH, BUTTON_HEIGHT),
                new ExitButton(game, X, Y_TOP + BUTTON_HEIGHT + Y_MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT));
        mainLoop.createGroup(0, 0).add(buttons);
        return new MainMenu(buttons);
    }

    public static GamePipeline getLevel1() {
        // TODO
        return GamePipeline.EMPTY_GAME_PIPELINE;
    }
}
