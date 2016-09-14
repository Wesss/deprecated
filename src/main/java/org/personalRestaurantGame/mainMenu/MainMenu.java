package org.personalRestaurantGame.mainMenu;

import org.framework.canvas.GameCanvasGraphics;
import org.personalRestaurantGame.model.GamePipeline;
import org.personalRestaurantGame.model.GameStateStore;
import org.gameUtil.ButtonList;
import org.gameUtil.EventAcceptor;

public class MainMenu implements GamePipeline {

    // TODO music
    private GameStateStore store; // TODO wire in getting of players name
    private ButtonList buttonList;

    protected MainMenu(ButtonList buttonList) {
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
