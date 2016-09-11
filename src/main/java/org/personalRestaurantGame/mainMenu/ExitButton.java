package org.personalRestaurantGame.mainMenu;

import org.framework.canvas.GameCanvasGraphics;
import org.personalRestaurantGame.RestaurantGame;
import util.Button;

import static org.personalRestaurantGame.RestaurantGame.State.NEW_GAME;

public class ExitButton extends Button {

    private static final int X = 500;
    private static final int Y = 250;
    private static final int WIDTH = 200;
    private static final int HEIGHT = 100;

    private RestaurantGame game;

    protected ExitButton(RestaurantGame game) {
        super(X, Y, WIDTH, HEIGHT);
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
        super.paint(g);
        g.drawString("Exit", X, Y);
    }
}
