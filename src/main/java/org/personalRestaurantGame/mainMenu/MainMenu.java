package org.personalRestaurantGame.mainMenu;

import org.framework.canvas.GameCanvasGraphics;
import org.framework.interfaces.GameObj;
import util.Button;

import java.util.List;

public class MainMenu implements GameObj {

    // TODO music

    private final List<Button> buttons;
    private int menuSelectorPosition;

    protected MainMenu(List<Button> buttons) {
        this.buttons = buttons;
        menuSelectorPosition = 0;
    }

    ////////////////////
    // Player Events
    ////////////////////

    // TODO wire in mouse selection

    public void select() {
        buttons.get(menuSelectorPosition).fireEvent(); // TODO abstract into menu list
    }

    public void moveSelectorUp() {
        menuSelectorPosition++;
        if (menuSelectorPosition == buttons.size()) {
            menuSelectorPosition = 0;
        }
    }

    public void moveSelectorDown() {
        menuSelectorPosition--;
        if (menuSelectorPosition == -1) {
            menuSelectorPosition = buttons.size() - 1;
        }
    }

    public void updateMousePosition(int x, int y) {
        // TODO
    }

    public void selectWithMouse() {

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
