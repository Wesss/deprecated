package org.personalRestaurantGame.mainMenu;

import org.gameUtil.EventAcceptor;

import java.awt.event.KeyEvent;

import static org.gameUtil.Mouse.MOUSE_LEFT;

public class MainMenuEventAcceptor implements EventAcceptor {

    private MainMenu menu;

    public MainMenuEventAcceptor(MainMenu menu) {
        this.menu = menu;
    }

    @Override
    public void keyPressed(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_RIGHT:
                menu.moveSelectorUp();
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_LEFT:
                menu.moveSelectorDown();
                break;
            case KeyEvent.VK_ENTER:
                menu.selectWithKeyboard();
                break;
        }
    }

    @Override
    public void keyReleased(int keyCode) {

    }

    @Override
    public void mousePressed(int x, int y, int button) {
        if (button == MOUSE_LEFT) {
            menu.selectWithMouse(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y, int button) {

    }

    @Override
    public void mouseMoved(int x, int y) {
        menu.updateMousePosition(x, y);
    }
}
