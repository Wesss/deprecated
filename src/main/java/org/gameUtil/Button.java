package org.gameUtil;

import org.framework.canvas.GameCanvasGraphics;
import org.framework.domain.GameObj;

import java.awt.*;

public class Button implements GameObj {

    /**
     * (x, y) is the center coordinate of the button
     * isCurrentSelection is true when the player has their selector over it
     */
    private CountdownCallback event;
    private int x, y, width, height;
    private boolean isCurrentSelection;
    private String label;
    // TODO have buttons use images

    public Button(CountdownCallback event, int x, int y, int width, int height, String label) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Button width/height must be positive");
        }
        this.event = event;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.label = label;
        isCurrentSelection = false;
    }

    ////////////////////
    // Setters and Getters
    ////////////////////

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isCurrentSelection() {
        return isCurrentSelection;
    }

    public void setCurrentSelection(boolean isSelection) {
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
        return (x - (width / 2) <= mouseX && mouseX <= x + (width / 2))
                && (y - (height / 2) <= mouseY && mouseY <= y + (height / 2));
    }

    /**
     * Fires the functionality attached to this button
     */
    public void fireEvent() {
        event.resume();
    }

    @Override
    public void update() {
        event.update();
    }

    @Override
    public void paint(GameCanvasGraphics g) {
        paintButtonWithoutImage(g, x, y, width, height, isCurrentSelection, label);
    }

    /////////////////////
    // Static Utils
    /////////////////////

    public static void paintButtonWithoutImage(GameCanvasGraphics g,
                                               int x, int y, int width, int height,
                                               boolean isCurrentSelection,
                                               String label) {
        if (isCurrentSelection) {
            g.setColor(Color.GRAY);
        } else {
            g.setColor(Color.BLACK);
        }
        g.drawRect(x - (width / 2), y - (height / 2), width, height);
        g.drawString(label, x, y);
    }
}
