package org.manual;

import org.framework.GameFramework;
import org.framework.canvas.GameCanvasController;
import org.framework.canvas.GameCanvasGraphics;
import org.framework.domain.Game;
import org.framework.domain.GameEventListener;
import org.framework.domain.GameFactory;
import org.framework.domain.GameObj;
import org.framework.mainLoop.MainLoopController;

import java.awt.event.KeyEvent;

/**
 * Test for EventListeners
 * (Key press/release, Mouse press/release, mouse position)
 */
public class EventFireTest implements Game{

    private static final int FPS = 60;

    public static void main(String args[]) throws InstantiationException {
        GameFramework.startGame(new EventFireTestFactory(), FPS);
    }

    private static class EventFireTestFactory implements GameFactory {
        private EventFireTest singleton;

        @Override
        public Game createGame(MainLoopController mainLoop, GameCanvasController canvas) {
            singleton = new EventFireTest(mainLoop, canvas);
            return singleton;
        }

        @Override
        public GameEventListener dispatchGameEventListener() {
            return new EventFireTestListener(singleton);
        }
    }

    private GameString[] log;
    private GameString mouseLoc;

    public EventFireTest(MainLoopController mainLoop, GameCanvasController canvas) {
        log = new GameString[20];

        for (int i = 0; i < log.length; i++) {
            log[i] = new GameString(20, 430 - (i * 20));
            mainLoop.add(log[i]);
        }

        mouseLoc = new GameString(20, 460);
        mainLoop.add(mouseLoc);
    }

    private void newEvent(String s) {
        for (int i = log.length - 1; i > 0; i--) {
            log[i].setString(log[i - 1].getString());
        }
        log[0].setString(s);
    }

    private void setMouseLoc(int x, int y) {
        mouseLoc.setString("Mouse Position: (" + x + ", " + y + ")");
    }

    private class GameString implements GameObj {

        private String string;
        private int mx;
        private int my;

        /**
         * @param x the myx coordinate of the top left of the string
         * @param y the y coordinate of the top left of the string
         */
        public GameString(int x, int y) {
            string = "";
            mx = x;
            my = y;
        }

        public String getString() {
            return string;
        }

        public void setString(String s) {
            string = s;
        }

        @Override
        public void paint(GameCanvasGraphics g) {
            g.drawString(string, mx, my);
        }

        @Override
        public void update() {}
    }

    private static class EventFireTestListener implements GameEventListener {

        private EventFireTest eventTest;

        public EventFireTestListener(EventFireTest eventTest) {
            this.eventTest = eventTest;
        }

        @Override
        public void mousePressed(int x, int y, int button) {
            eventTest.newEvent("pressed button "+ button);
        }

        @Override
        public void mouseReleased(int x, int y, int button) {
            eventTest.newEvent("released button "+ button);
        }

        @Override
        public void mouseMoved(int x, int y) {
            eventTest.setMouseLoc(x, y);
        }

        @Override
        public void keyPressed(int key) {
            eventTest.newEvent("pressed  key "+ KeyEvent.getKeyText(key));
        }

        @Override
        public void keyReleased(int key) {
            eventTest.newEvent("released key "+ KeyEvent.getKeyText(key));
        }
    }

}
