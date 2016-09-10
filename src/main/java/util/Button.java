package util;

public abstract class Button {

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

    public boolean isCurrentSelection() {
        return isCurrentSelection;
    }

    public void setAsCurrentSelection(boolean isSelection) {
        isCurrentSelection = isSelection;
    }

    ////////////////////
    // Button Functionality
    ////////////////////

    /**
     * If the given mouse position is over this button, it will be set as the current selection
     * @param mouseX
     * @param mouseY
     */
    public void checkMousePositionOverButton(int mouseX, int mouseY) {
        isCurrentSelection = (x <= mouseX && mouseX <= x + width)
                && (y <= mouseY && mouseY <= y + height);
    }

    /**
     * Fires the event attached to this button
     */
    public abstract void fireEvent();
}
