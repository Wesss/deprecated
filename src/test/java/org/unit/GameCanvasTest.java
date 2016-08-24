package org.unit;

import org.framework.canvas.GameCanvas;
import org.framework.canvas.GameCanvasFactory;
import org.framework.interfaces.Game;
import org.framework.interfaces.GameEventListener;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class GameCanvasTest {

    // TODO allow frame to be passed in to canvas to mock it
    // TODO test key/mouse multi press without release

    private static final int CANVAS_LENGTH = GameCanvas.DEFAULT_VIRTUAL_X;
    private static final int CANVAS_HEIGHT = GameCanvas.DEFAULT_VIRTUAL_Y;

    @SuppressWarnings("unchecked")
    private GameEventListener<Game> mockListener = mock(GameEventListener.class);
    private Frame mockFrame = mock(Frame.class);
    private GameCanvas canvas;

    @Before
    public void setup() {
        reset(mockListener);

        canvas = GameCanvasFactory.createCanvas(mockFrame, CANVAS_LENGTH, CANVAS_HEIGHT);
        canvas.setReferences(mockListener);
    }

    @Test
    public void testSetup() {
    }

    @Test
    public void virtualToActualXNoChange() {
        assertThat(canvas.virtualToActualX(500), is(500));
    }

    @Test
    public void virtualToActualYNoChange() {
        assertThat(canvas.virtualToActualY(500), is(500));
    }

    @Test
    public void actualToVirtualXNoChange() {
        assertThat(canvas.actualToVirtualX(500), is(500));
    }

    @Test
    public void actualToVirtualYNoChange() {
        assertThat(canvas.actualToVirtualY(500), is(500));
    }

    @Test
    public void virtualToActualXTriple() {
        canvas.setVirtualX(333);
        assertThat(canvas.virtualToActualX(100), is(300));
    }

    @Test
    public void virtualToActualYTriple() {
        canvas.setVirtualY(333);
        assertThat(canvas.virtualToActualY(100), is(300));
    }

    @Test
    public void actualToVirtualXTriple() {
        canvas.setVirtualX(3000);
        assertThat(canvas.actualToVirtualX(500), is(1500));
    }

    @Test
    public void actualToVirtualYTriple() {
        canvas.setVirtualY(3000);
        assertThat(canvas.actualToVirtualY(500), is(1500));
    }

    @Test
    public void virtualToActualXOneThird() {
        canvas.setVirtualX(3000);
        assertThat(canvas.virtualToActualX(500), either(is(167)).or(is(166)));
    }

    @Test
    public void virtualToActualYOneThird() {
        canvas.setVirtualY(3000);
        assertThat(canvas.virtualToActualY(500), either(is(167)).or(is(166)));
    }

    @Test
    public void actualToVirtualXOneThird() {
        canvas.setVirtualX(333);
        assertThat(canvas.actualToVirtualX(500), either(is(167)).or(is(166)));
    }

    @Test
    public void actualToVirtualYOneThird() {
        canvas.setVirtualY(333);
        assertThat(canvas.actualToVirtualY(500), either(is(167)).or(is(166)));
    }
}
