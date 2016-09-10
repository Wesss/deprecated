package org.personalRestaurantGame.mainMenu;

import org.framework.canvas.GameCanvasGraphics;
import org.framework.interfaces.GameObj;
import util.Button;

import java.util.List;

import static org.util.math.Math.modPos;

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

    public void selectWithKeyboard() {
        buttons.get(menuSelectorPosition).fireEvent(); // TODO abstract into menu list
    }

    public void moveSelectorUp() {
        menuSelectorPosition = modPos(menuSelectorPosition + 1, buttons.size());
    }

    public void moveSelectorDown() {
        menuSelectorPosition = modPos(menuSelectorPosition - 1, buttons.size());
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
