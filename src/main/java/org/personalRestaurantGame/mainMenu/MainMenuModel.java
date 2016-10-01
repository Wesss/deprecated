package org.personalRestaurantGame.mainMenu;

import org.framework.mainLoop.MainLoopGroup;
import org.gameUtil.Button;
import org.gameUtil.ButtonList;
import org.gameUtil.CountdownCallback;
import org.personalRestaurantGame.RestaurantGame;
import org.personalRestaurantGame.domain.GameStateStore;

import static org.personalRestaurantGame.RestaurantGame.State.NEW_GAME;

public class MainMenuModel {

    public static final int X = 500;
    public static final int Y_TOP = 100;
    public static final int Y_MARGIN = 50;
    public static final int BUTTON_WIDTH = 200;
    public static final int BUTTON_HEIGHT = 100;

    public static final int TRANSITION_OUT_CYCLES = 60;

    public static final int DEFAULT_PRIORITY = 0;
    public static final int BACKGROUND_LAYER = 0;
    public static final int FOREGROUND_LAYER = 1;
    public static final int MASK_LAYER = 2;

    private GameStateStore store;
    private ButtonList buttonList;

    public MainMenuModel(ButtonList buttons) {
        this.buttonList = buttons;
    }

    public GameStateStore getStore() {
        return store;
    }

    public void setStore(GameStateStore store) {
        this.store = store;
    }

    public ButtonList getButtonList() {
        return buttonList;
    }

    public void setButtonList(ButtonList buttonList) {
        this.buttonList = buttonList;
    }

    public static ButtonList getButtons(MainMenuController controller,
                                        RestaurantGame game,
                                        MainLoopGroup maskGroup,
                                        MainLoopGroup foregroundGroup) {
        Button newGameButton = new Button(
                new CountdownCallback(1, () -> {
                    maskGroup.add(new FadeOutMask(TRANSITION_OUT_CYCLES));
                    foregroundGroup.add(new CountdownCallback(TRANSITION_OUT_CYCLES, () -> {
                        game.swapState(NEW_GAME);
                    }));
                }),
                X, Y_TOP, BUTTON_WIDTH, BUTTON_HEIGHT,
                "New Game");
        Button quitButton = new Button(
                new CountdownCallback(1, game::exit),
                X, Y_TOP + BUTTON_HEIGHT + Y_MARGIN, BUTTON_WIDTH, BUTTON_HEIGHT,
                "Quit");

        ButtonList buttons = new ButtonList(newGameButton, quitButton);
        foregroundGroup.add(buttons);
        return buttons;
    }
}
