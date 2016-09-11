package util;

import com.sun.istack.internal.Nullable;
import org.framework.canvas.GameCanvasGraphics;
import org.framework.interfaces.GameObj;

import java.util.ArrayList;
import java.util.List;

import static org.util.math.Math.modPos;

public class ButtonList implements GameObj {

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
            setButtonThatHasSelector(buttons.get(0));
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
            setButtonThatHasSelector(buttons.get(0));
            return;
        }

        setButtonThatHasSelector(buttons.get(
                modPos(buttons.indexOf(buttonThatHasSelector) + 1, buttons.size())));
    }

    public void moveSelectorDown() {
        if (!isKeyboardActiveSelector) {
            isKeyboardActiveSelector = true;
            isMouseActiveSelector = false;
            setButtonThatHasSelector(buttons.get(0));
            return;
        }

        setButtonThatHasSelector(buttons.get(
                modPos(buttons.indexOf(buttonThatHasSelector) - 1, buttons.size())));
    }

    ////////////////////
    // Mouse Events
    ////////////////////

    public void updateMousePosition(int x, int y) {
        if (!isMouseActiveSelector) {
            isMouseActiveSelector = true;
            isKeyboardActiveSelector = false;
        }

        Button mousedOverButton = null;
        for (Button button : buttons) {
            if (button.isMousePositionOverButton(x, y)) {
                mousedOverButton = button;
                break;
            }
        }
        setButtonThatHasSelector(mousedOverButton);
    }

    public void selectWithMouse(int x, int y) {
        if (!isMouseActiveSelector) {
            isMouseActiveSelector = true;
            isKeyboardActiveSelector = false;
            updateMousePosition(x, y);
            return;
        }

        if (buttonThatHasSelector != null) {
            buttonThatHasSelector.fireEvent();
        }
    }

    ////////////////////
    // Game Events
    ////////////////////

    @Override
    public void update() {
        for (Button button : buttons) {
            button.update();
        }
    }

    @Override
    public void paint(GameCanvasGraphics g) {
        for (Button button : buttons) {
            button.paint(g);
        }
    }

    ////////////////////
    // Private
    ////////////////////

    /**
     * @param button
     * @requires button is contained by Buttons
     */
    private void setButtonThatHasSelector(@Nullable Button button) {
        if (buttonThatHasSelector != null) {
            buttonThatHasSelector.setAsCurrentSelection(false);
        }
        buttonThatHasSelector = button;
        if (buttonThatHasSelector != null) {
            buttonThatHasSelector.setAsCurrentSelection(true);
        }
    }
}
