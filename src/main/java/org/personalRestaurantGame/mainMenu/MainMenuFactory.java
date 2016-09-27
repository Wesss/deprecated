package org.personalRestaurantGame.mainMenu;

import org.framework.mainLoop.MainLoopCustomGroupsInterface;
import org.framework.mainLoop.MainLoopGroup;
import org.gameUtil.Button;
import org.gameUtil.ButtonList;
import org.gameUtil.CountdownEvent;
import org.personalRestaurantGame.RestaurantGame;

import static org.personalRestaurantGame.RestaurantGame.State.*;
import static org.personalRestaurantGame.mainMenu.MainMenuModel.*;

public class MainMenuFactory {

    public static MainMenu getMainMenu(RestaurantGame game, MainLoopCustomGroupsInterface mainLoop) {
        MainLoopGroup foregroundGroup = mainLoop.createGroup(DEFAULT_PRIORITY, FOREGROUND_LAYER);
        MainLoopGroup maskGroup = mainLoop.createGroup(DEFAULT_PRIORITY, MASK_LAYER);

        // TODO code review MainMenuFactory
        Button newGameButton = new Button(
                new CountdownEvent(1, () -> {
                    maskGroup.add(new FadeOutMask(TRANSITION_OUT_CYCLES));
                    foregroundGroup.add(new CountdownEvent(TRANSITION_OUT_CYCLES, () -> {
                        game.swapState(NEW_GAME);
                    }));
                }),
                X, Y_TOP, BUTTON_WIDTH, BUTTON_HEIGHT,
                "New Game");
        Button quitButton = new Button(
                new CountdownEvent(1, game::exit),
                X, Y_TOP + BUTTON_HEIGHT + Y_MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT,
                "Quit");

        ButtonList buttons = new ButtonList(newGameButton, quitButton);
        foregroundGroup.add(buttons);

        MainMenuModel model = new MainMenuModel(buttons);
        MainMenuController controller = new MainMenuController(model);
        MainMenuEventListener listener = new MainMenuEventListener(controller);
        return new MainMenu(model, controller, listener);
    }
}
