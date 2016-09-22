package org.framework;

import javafx.util.Pair;
import org.framework.canvas.GameCanvasController;
import org.framework.domain.Game;
import org.framework.domain.GameEventListener;
import org.framework.domain.GameFactory;
import org.framework.mainLoop.MainLoopController;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class GameFrameworkTest {

    GameFactory mockFactory = mock(GameFactory.class);

    @Before
    public void setup() {
        reset(mockFactory);
    }

    @Test
    public void successfulCreation() throws Exception {
        when(mockFactory.createGame(org.mockito.Matchers.any(MainLoopController.class),
                org.mockito.Matchers.any(GameCanvasController.class)))
                .thenReturn(Game.EMPTY_GAME);
        when(mockFactory.dispatchGameEventListener()).thenReturn(GameEventListener.EMPTY_GAME_LISTENER);

        Pair<MainLoopController, GameCanvasController> controllers = GameFramework.startGame(mockFactory, 60);
        assertThat(controllers.getKey(), notNullValue());
        assertThat(controllers.getValue(), notNullValue());

        verify(mockFactory).createGame(controllers.getKey(), controllers.getValue());
        verify(mockFactory).dispatchGameEventListener();
    }

    @Test
    public void failedCreation() {
        when(mockFactory.createGame(
                org.mockito.Matchers.any(MainLoopController.class),
                org.mockito.Matchers.any(GameCanvasController.class)))
                .thenThrow(new NullPointerException("mock factory error"));
        try {
            GameFramework.startGame(mockFactory, 60);
            fail("should throw");
        } catch (RuntimeException ignored) {
        }
    }


}
