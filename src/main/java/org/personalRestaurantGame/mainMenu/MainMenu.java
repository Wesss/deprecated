package org.personalRestaurantGame.mainMenu;

import org.framework.canvas.GameCanvasGraphics;
import org.framework.mainLoop.MainLoopCustomGroupsInterface;
import org.personalRestaurantGame.model.GamePipeline;
import org.personalRestaurantGame.model.GameStateStore;
import org.gameUtil.ButtonList;
import org.gameUtil.EventAcceptor;

public class MainMenu implements GamePipeline {

    public static final int X = 500;
    public static final int Y_TOP = 100;
    public static final int Y_MARGIN = 50;
    public static final int BUTTON_WIDTH = 200;
    public static final int BUTTON_HEIGHT = 100;

    // TODO music
    private GameStateStore store; // TODO wire in getting of players name
    private ButtonList buttonList;

    public MainMenu(ButtonList buttonList) {
        this.buttonList = buttonList;
    }

    @Override
    public void acceptGameStateStore(GameStateStore store) {
        this.store = store;
    }

    @Override
    public GameStateStore returnGameStateStore() {
        return store;
    }

    @Override
    public EventAcceptor dispatchEventAcceptor() {
        return new MainMenuEventAcceptor(this);
    }

    ////////////////////
    // Player Events
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
    // Internal Events
    ////////////////////

    public void update() {
        // TODO animation
    }

    public void paint(GameCanvasGraphics g) {
        buttonList.paint(g);
    }
}
