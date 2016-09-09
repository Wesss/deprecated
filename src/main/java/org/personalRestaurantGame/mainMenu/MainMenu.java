package org.personalRestaurantGame.mainMenu;

import org.framework.canvas.GameCanvasGraphics;
import org.framework.interfaces.GameObj;

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

    // TODO wire into event listener
    // TODO wire in mouse selection

    public void select() {
        buttons.get(menuSelectorPosition).fireEvent();
    }

    public void moveUp() {
        menuSelectorPosition++;
        if (menuSelectorPosition == buttons.size()) {
            menuSelectorPosition = 0;
        }
    }

    public void moveDown() {
        menuSelectorPosition--;
        if (menuSelectorPosition == -1) {
            menuSelectorPosition = buttons.size() - 1;
        }
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
