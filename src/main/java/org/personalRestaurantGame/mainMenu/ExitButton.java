package org.personalRestaurantGame.mainMenu;

import org.framework.canvas.GameCanvasGraphics;
import org.gameUtil.CountdownEvent;
import org.personalRestaurantGame.RestaurantGame;
import org.gameUtil.Button;

public class ExitButton extends Button {

    CountdownEvent event;

    public ExitButton(CountdownEvent event, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.event = event;
        event.suspend();
    }

    @Override
    public void fireEvent() {
        event.resume();
    }

    @Override
    public void update() {
        event.tick();
    }

    @Override
    public void paint(GameCanvasGraphics g) {
        Button.paintButtonWithoutImage(g, getX(), getY(), getWidth(), getHeight(), isCurrentSelection(), "Exit");
        event.printStats(g, getX(), getY(), 1);
    }
}
