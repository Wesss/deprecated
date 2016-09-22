package org.personalRestaurantGame.game;

import org.gameUtil.EventAcceptor;

public class LevelEventAcceptor implements EventAcceptor {

    private Level level;

    public LevelEventAcceptor(Level level) {
        this.level = level;
    }

    @Override
    public void keyPressed(int keyCode) {
    }

    @Override
    public void keyReleased(int keyCode) {

    }

    @Override
    public void mousePressed(int x, int y, int button) {
    }

    @Override
    public void mouseReleased(int x, int y, int button) {

    }

    @Override
    public void mouseMoved(int x, int y) {

    }
}
