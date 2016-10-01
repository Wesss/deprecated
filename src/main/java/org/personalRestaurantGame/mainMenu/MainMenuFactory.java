package org.personalRestaurantGame.mainMenu;

import org.framework.mainLoop.MainLoopCustomGroupsInterface;
import org.framework.mainLoop.MainLoopGroup;
import org.personalRestaurantGame.RestaurantGame;

import static org.personalRestaurantGame.mainMenu.MainMenuModel.*;

public class MainMenuFactory {

    public static MainMenu getMainMenu(RestaurantGame game, MainLoopCustomGroupsInterface mainLoop) {
        MainLoopGroup foregroundGroup = mainLoop.createGroup(DEFAULT_PRIORITY, FOREGROUND_LAYER);
        MainLoopGroup maskGroup = mainLoop.createGroup(DEFAULT_PRIORITY, MASK_LAYER);

        // TODO code review MainMenuFactory

        MainMenuController controller = new MainMenuController();
        MainMenuModel model = new MainMenuModel(MainMenuModel.getButtons(controller, game, maskGroup, foregroundGroup));
        controller.setModelReference(model);
        MainMenuEventListener listener = new MainMenuEventListener(controller);
        return new MainMenu(model, controller, listener);
    }
}
