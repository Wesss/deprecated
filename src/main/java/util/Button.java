package util;

import org.framework.canvas.GameCanvasGraphics;
import org.framework.interfaces.GameObj;

import java.awt.*;

public abstract class Button implements GameObj {

    /**
     * (x, y) is the center coordinate of the button
     */
    private int x, y, width, height;
    private boolean isCurrentSelection;

    protected Button(int x, int y, int width, int height) {
        // TODO arg check
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        isCurrentSelection = false;
    }

    public void setAsCurrentSelection(boolean isSelection) {
        isCurrentSelection = isSelection;
    }

    ////////////////////
    // Button Functionality
    ////////////////////

    /**
     * @param mouseX
     * @param mouseY
     * @return true iff given mouse position is over this button
     */
    public boolean isMousePositionOverButton(int mouseX, int mouseY) {
        return (x - (width/2) <= mouseX && mouseX <= x + (width/2))
                && (y - (height/2) <= mouseY && mouseY <= y + (height/2));
    }

    /**
     * Fires the event attached to this button
     */
    public abstract void fireEvent();

    ////////////////////
    // Game Event
    ////////////////////

    @Override
    public void paint(GameCanvasGraphics g) {
        if (isCurrentSelection) {
            g.setColor(Color.GRAY);
        } else {
            g.setColor(Color.BLACK);
        }
        g.drawRect(x - (width/2), y - (height/2), width, height);
    }
}
