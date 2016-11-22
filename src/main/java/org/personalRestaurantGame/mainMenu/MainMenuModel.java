package org.personalRestaurantGame.mainMenu;

import org.framework.mainLoop.MainLoopGroup;
import org.gameUtil.ButtonList;
import org.gameUtil.GameCallback;
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
    private RestaurantGame game;
    private MainLoopGroup foregroundGroup;
    private MainLoopGroup maskGroup;
    private ButtonList buttonList;

    public MainMenuModel() {

    }

    public void setReferences(RestaurantGame game, MainLoopGroup foregroundGroup, MainLoopGroup maskGroup, ButtonList buttons) {
        this.game = game;
        this.foregroundGroup = foregroundGroup;
        this.maskGroup = maskGroup;
        this.buttonList = buttons;
    }

    public GameStateStore getStore() {
        return store;
    }

    public void setStore(GameStateStore store) {
        this.store = store;
    }

    ////////////////////
    // User events
    ////////////////////

    public void selectWithKeyboard() {
        buttonList.selectWithKeyboard();
    }

    public void moveSelectorUp() {
        buttonList.moveSelectorUp();
    }

    public void moveSelectorDown() {
        buttonList.moveSelectorDown();
    }

    public void updateMousePosition(int x, int y) {
        buttonList.updateMousePosition(x, y);
    }

    public void selectWithMouse(int x, int y) {
        buttonList.selectWithMouse(x, y);
    }

    ////////////////////
    // Button callbacks
    ////////////////////

    public void newGameButton() {
        maskGroup.add(new FadeOutMask(TRANSITION_OUT_CYCLES));
        GameCallback newGameSwap = new GameCallback(TRANSITION_OUT_CYCLES, this::newGameButton2);
        newGameSwap.resume();
        foregroundGroup.add(newGameSwap);
    }

    // TODO better name
    public void newGameButton2() {
        game.swapState(NEW_GAME);
    }

    public void quitButton() {
        game.exit();
    }
}
