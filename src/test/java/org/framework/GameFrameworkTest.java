package org.framework;

import javafx.util.Pair;
import org.framework.canvas.GameCanvasController;
import org.framework.interfaces.Game;
import org.framework.interfaces.GameEventListener;
import org.framework.mainLoop.MainLoop;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class GameFrameworkTest {

    @SuppressWarnings("unchecked")
    private GameEventListener<Game> mockGameEventListener = mock(GameEventListener.class);

    @Before
    public void setup() {
        reset(mockGameEventListener);
    }

    @Test
    public void createGame() {
        Pair<MainLoop, GameCanvasController> gameControllers = null;
        try {
            gameControllers = GameFramework.startGame(EmptyGame.class, mockGameEventListener, 60);
        } catch (InstantiationException e) {
            fail(e.getMessage());
        }

        assertThat(gameControllers, notNullValue());
        verify(mockGameEventListener).acceptGame(any(EmptyGame.class));
    }

    public static class EmptyGame implements Game {}

    ////////////////////////////////////////////////

    @Test
    public void createGameWithPreferredConstructor() {
        Pair<MainLoop, GameCanvasController> gameControllers = null;
        try {
            gameControllers = GameFramework.startGame(EmptyGame2.class, mockGameEventListener, 60);
        } catch (InstantiationException e) {
            fail(e.getMessage());
        }

        assertThat(gameControllers, notNullValue());
        verify(mockGameEventListener).acceptGame(any(EmptyGame2.class));
    }

    public static class EmptyGame2 implements Game {

        public EmptyGame2() {
            fail("Default constructor called when preferred constructor is present");
        }

        public EmptyGame2(MainLoop loop, GameCanvasController canvas) {
        }
    }

    ////////////////////////////////////////////////

    @Test
    public void createGameWithWrongConstructorFails() {
        try {
            GameFramework.startGame(EmptyGame3.class, mockGameEventListener, 60);
            fail("should throw");
        } catch (InstantiationException e) {

        }
    }

    public static class EmptyGame3 implements Game {

        public EmptyGame3(MainLoop loop) {
            fail("Bad constructor called");
        }
    }
}
