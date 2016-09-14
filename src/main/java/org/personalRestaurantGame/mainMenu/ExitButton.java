package org.personalRestaurantGame.mainMenu;

import org.framework.canvas.GameCanvasGraphics;
import org.personalRestaurantGame.RestaurantGame;
import util.Button;

import static org.personalRestaurantGame.RestaurantGame.State.NEW_GAME;

public class ExitButton extends Button {

    private RestaurantGame game;

    protected ExitButton(RestaurantGame game, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.game = game;
    }

    @Override
    public void fireEvent() {
        game.exit();
    }

    @Override
    public void update() {

    }

    @Override
    public void paint(GameCanvasGraphics g) {
        Button.paintButtonWithoutImage(g, getX(), getY(), getWidth(), getHeight(), isCurrentSelection(), "Exit");
    }
}
