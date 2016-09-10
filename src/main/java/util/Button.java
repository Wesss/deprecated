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
        return (x <= mouseX && mouseX <= x + width)
                && (y <= mouseY && mouseY <= y + height);
    }

    /**
     * Fires the event attached to this button
     */
    public abstract void fireEvent();
}
