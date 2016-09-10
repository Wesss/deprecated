package org.personalRestaurantGame.mainMenu;

import org.framework.canvas.GameCanvasGraphics;
import org.framework.interfaces.GameObj;
import util.ButtonList;

public class MainMenu implements GameObj {

    // TODO music
    private ButtonList buttonList;

    protected MainMenu(ButtonList buttonList) {
        this.buttonList = buttonList;
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

    public void selectWithMouse() {
        buttonList.selectWithMouse();
    }

    ////////////////////
    // Internal Events
    ////////////////////

    public void update() {
        // TODO animation
    }

    public void paint(GameCanvasGraphics g) {
        g.drawString("TODO MAIN_MENU", 100, 100);
    }
}
