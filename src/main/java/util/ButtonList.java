package util;

import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.List;

import static org.util.math.Math.modPos;

public class ButtonList {

    /**
     * buttonThatHasSelector.isCurrentSelection == true
     * (any other button).isCurrentSelection == false
     * at most one of isMouseActiveSelector and isKeyboardActiveSelector can be true
     * buttons' mouse over selection areas are not overlapping
     */
    private List<Button> buttons;
    private @Nullable Button buttonThatHasSelector;
    private boolean isMouseActiveSelector;
    private boolean isKeyboardActiveSelector;

    public ButtonList(Button... buttons) {
        this.buttons = new ArrayList<>();
        for (Button button : buttons) {
            this.buttons.add(button); // TODO create list builder util
        }
        buttonThatHasSelector = null;
        isMouseActiveSelector = false;
        isKeyboardActiveSelector = false;
    }

    ////////////////////
    // Keyboard Events
    ////////////////////

    public void selectWithKeyboard() {
        if (!isKeyboardActiveSelector) {
            isKeyboardActiveSelector = true;
            isMouseActiveSelector = false;
            return;
        }

        if (buttonThatHasSelector != null) {
            buttonThatHasSelector.fireEvent();
        }
    }

    public void moveSelectorUp() {
        if (!isKeyboardActiveSelector) {
            isKeyboardActiveSelector = true;
            isMouseActiveSelector = false;
            return;
        }

        buttonThatHasSelector.setAsCurrentSelection(false);
        buttonThatHasSelector = buttons.get(
                modPos(buttons.indexOf(buttonThatHasSelector) + 1, buttons.size()));
        buttonThatHasSelector.setAsCurrentSelection(true);
    }

    public void moveSelectorDown() {
        if (!isKeyboardActiveSelector) {
            isKeyboardActiveSelector = true;
            isMouseActiveSelector = false;
            return;
        }

        buttonThatHasSelector.setAsCurrentSelection(false);
        buttonThatHasSelector = buttons.get(
                modPos(buttons.indexOf(buttonThatHasSelector) - 1, buttons.size()));
        buttonThatHasSelector.setAsCurrentSelection(true);
    }

    ////////////////////
    // Mouse Events
    ////////////////////

    public void updateMousePosition(int x, int y) {
        if (!isMouseActiveSelector) {
            isMouseActiveSelector = true;
            isKeyboardActiveSelector = false;
            return;
        }

        for (Button button : buttons) {
            if (button.isMousePositionOverButton(x, y)) {
                button.setAsCurrentSelection(true);
                buttonThatHasSelector = button;
            } else {
                button.setAsCurrentSelection(false);
            }
        }
    }

    public void selectWithMouse() {
        if (!isMouseActiveSelector) {
            isMouseActiveSelector = true;
            isKeyboardActiveSelector = false;
            return;
        }

        if (buttonThatHasSelector != null) {
            buttonThatHasSelector.fireEvent();
        }
    }
}
