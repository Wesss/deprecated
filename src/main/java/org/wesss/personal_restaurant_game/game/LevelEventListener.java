package org.wesss.personal_restaurant_game.game;

import org.wesss.game_framework.domain.GameEventListener;

public class LevelEventListener implements GameEventListener {

    private Level level;

    public LevelEventListener(Level level) {
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
