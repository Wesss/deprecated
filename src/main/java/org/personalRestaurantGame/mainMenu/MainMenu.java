package org.personalRestaurantGame.mainMenu;

import org.framework.domain.GameEventListener;
import org.gameUtil.ButtonList;
import org.personalRestaurantGame.model.GamePipeline;
import org.personalRestaurantGame.model.GameStateStore;

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
    public GameEventListener dispatchEventAcceptor() {
        return new MainMenuEventListener(this);
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

    // Transition notes
    //disable buttons (cut off events) while transitioning
    //set flag to transitioning out
    //let update handle fading or moving out
    //let update call swap state
}
