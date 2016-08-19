package org.unit;

import org.framework.canvas.GameCanvas;
import org.framework.interfaces.Game;
import org.framework.interfaces.GameEventListener;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class GameCanvasTest {

    // TODO allow frame to be passed in to canvas to mock it
    // TODO test actual/virtual transformations

    private static final int CANVAS_LENGTH = 1000;
    private static final int CANVAS_HEIGHT = 1000;

    @SuppressWarnings("unchecked")
    private GameEventListener<Game> mockListener = mock(GameEventListener.class);
    private GameCanvas canvas;

    @Before
    public void setup() {
        reset(mockListener);

        canvas = new GameCanvas(CANVAS_LENGTH, CANVAS_HEIGHT);
        canvas.setReferences(mockListener);
    }

    @Test
    public void testSetup() {
    }
}
