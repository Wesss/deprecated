package org.personalRestaurantGame.mainMenu;

import org.gameUtil.ButtonList;
import org.personalRestaurantGame.domain.GameStateStore;

public class MainMenuModel {

    public static final int X = 500;
    public static final int Y_TOP = 100;
    public static final int Y_MARGIN = 50;
    public static final int BUTTON_WIDTH = 200;
    public static final int BUTTON_HEIGHT = 100;
    public static final int TRANSITION_OUT_CYCLES = 60;

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
}
