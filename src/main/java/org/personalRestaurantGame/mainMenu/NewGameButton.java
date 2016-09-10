package org.personalRestaurantGame.mainMenu;

import org.framework.canvas.GameCanvasGraphics;
import org.personalRestaurantGame.RestaurantGame;
import util.Button;

import static org.personalRestaurantGame.RestaurantGame.State.NEW_GAME;

public class NewGameButton extends Button {

    private static final int X = 500;
    private static final int Y = 100;
    private static final int WIDTH = 200;
    private static final int HEIGHT = 100;

    private RestaurantGame game;

    protected NewGameButton(RestaurantGame game) {
        super(X, Y, WIDTH, HEIGHT);
        this.game = game;
    }

    @Override
    public void fireEvent() {
        game.swapState(NEW_GAME);
    }

    @Override
    public void update() {

    }

    @Override
    public void paint(GameCanvasGraphics g) {
        super.paint(g);
    }
}
