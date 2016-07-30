package org.system;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import org.framework.GameFramework;
import org.framework.GamePanelGraphics;
import org.framework.MainLoop;
import org.framework.interfaces.Game;
import org.framework.interfaces.GameEventListener;
import org.framework.interfaces.GameObj;

/**
 * Test for EventListeners
 * (Key press/release, Mouse press/release, mouse position)
 * 
 * @author Wesley Cox
 */
public class EventFireTest implements Game{

    private static final int FPS = 60;

    public static void main(String args[]) {
        GameFramework.startGame(EventFireTest.class, new EventFireTestListener(), FPS);
    }

    private GameString[] log;
    private GameString mouseLoc;

    public EventFireTest(MainLoop mainLoop) {
        log = new GameString[20];

        for (int i = 0; i < log.length; i++) {
            log[i] = new GameString(20, 430 - (i * 20));
            mainLoop.add(log[i]);
        }

        mouseLoc = new GameString(20, 460);
        mainLoop.add(mouseLoc);
    }

    public void newEvent(String s) {
        for (int i = log.length - 1; i > 0; i--) {
            log[i].setString(log[i - 1].getString());
        }
        log[0].setString(s);
    }

    public void setMouseLoc(int x, int y) {
        mouseLoc.setString("Mouse Position: (" + x + ", " + y + ")");
    }

    public static class EventFireTestListener implements GameEventListener {

        private EventFireTest eventTest;

        @Override
        public void acceptGame(Game game) {
            this.eventTest = (EventFireTest) game;
        }

        @Override
        public void mousePressed(int x, int y, int button) {
            eventTest.newEvent("pressed  button "+ button);
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

    public class GameString implements GameObj {

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
        public void draw(GamePanelGraphics g) {
            g.drawString(string, mx, my);
        }

        @Override
        public void update() {}
    }
}
