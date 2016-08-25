package org.framework.canvas;

import org.framework.interfaces.Game;
import org.framework.interfaces.GameEventListener;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class GameCanvasModelTest {

    // TODO test key/mouse multi press without release

    private static final int CANVAS_LENGTH = GameCanvasModel.DEFAULT_VIRTUAL_X;
    private static final int CANVAS_HEIGHT = GameCanvasModel.DEFAULT_VIRTUAL_Y;

    private Frame mockFrame = mock(Frame.class);
    @SuppressWarnings("unchecked")
    private GameEventListener<Game> mockListener = mock(GameEventListener.class);
    private GameCanvasModel canvasModel;

    @Before
    public void setup() {
        reset(mockListener);

        canvasModel = GameCanvasFactory.createCanvas(mockFrame, CANVAS_LENGTH, CANVAS_HEIGHT).getValue();
        canvasModel.setReferences(mockListener);
    }

    @Test
    public void testSetup() {
    }

    @Test
    public void virtualToActualXNoChange() {
        assertThat(canvasModel.virtualToActualX(500), is(500));
    }

    @Test
    public void virtualToActualYNoChange() {
        assertThat(canvasModel.virtualToActualY(500), is(500));
    }

    @Test
    public void actualToVirtualXNoChange() {
        assertThat(canvasModel.actualToVirtualX(500), is(500));
    }

    @Test
    public void actualToVirtualYNoChange() {
        assertThat(canvasModel.actualToVirtualY(500), is(500));
    }

    @Test
    public void virtualToActualXTriple() {
        canvasModel.setVirtualX(333);
        assertThat(canvasModel.virtualToActualX(100), is(300));
    }

    @Test
    public void virtualToActualYTriple() {
        canvasModel.setVirtualY(333);
        assertThat(canvasModel.virtualToActualY(100), is(300));
    }

    @Test
    public void actualToVirtualXTriple() {
        canvasModel.setVirtualX(3000);
        assertThat(canvasModel.actualToVirtualX(500), is(1500));
    }

    @Test
    public void actualToVirtualYTriple() {
        canvasModel.setVirtualY(3000);
        assertThat(canvasModel.actualToVirtualY(500), is(1500));
    }

    @Test
    public void virtualToActualXOneThird() {
        canvasModel.setVirtualX(3000);
        assertThat(canvasModel.virtualToActualX(500), either(is(167)).or(is(166)));
    }

    @Test
    public void virtualToActualYOneThird() {
        canvasModel.setVirtualY(3000);
        assertThat(canvasModel.virtualToActualY(500), either(is(167)).or(is(166)));
    }

    @Test
    public void actualToVirtualXOneThird() {
        canvasModel.setVirtualX(333);
        assertThat(canvasModel.actualToVirtualX(500), either(is(167)).or(is(166)));
    }

    @Test
    public void actualToVirtualYOneThird() {
        canvasModel.setVirtualY(333);
        assertThat(canvasModel.actualToVirtualY(500), either(is(167)).or(is(166)));
    }
}
