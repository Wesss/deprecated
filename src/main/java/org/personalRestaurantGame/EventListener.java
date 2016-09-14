package org.personalRestaurantGame;

import org.framework.interfaces.GameEventListener;

public class EventListener implements GameEventListener<RestaurantGame> {

    private RestaurantGame game;

    public void acceptGame(RestaurantGame game) {
        this.game = game;
    }

    @Override
    public void keyPressed(int keyCode) {
        game.keyPressed(keyCode);
    }

    @Override
    public void keyReleased(int keyCode) {
        game.keyReleased(keyCode);
    }

    @Override
    public void mousePressed(int x, int y, int button) {
        game.mousePressed(x, y, button);
    }

    @Override
    public void mouseReleased(int x, int y, int button) {
        game.mouseReleased(x, y, button);
    }

    @Override
    public void mouseMoved(int x, int y) {
        game.mouseMoved(x, y);
    }
}
