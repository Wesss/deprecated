package org.wesss.game_framework.mainLoop;

import org.wesss.game_framework.canvas.GameCanvasGraphics;
import org.wesss.game_framework.canvas.GameCanvasModel;
import org.wesss.game_framework.domain.GameObj;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;

public abstract class MainLoopTest {

    public static final int NO_UPS = 0;
    public static final int SLOW_UPS = 2;
    public static final int STANDARD_UPS = 60;
    public static final int STRESS_TEST_UPS = 300;

    protected MainLoopModel mainLoopModel;
    protected MainLoopController mainLoopController;

    protected GameCanvasGraphics mockGraphics = mock(GameCanvasGraphics.class);
    protected GameCanvasModel mockCanvas = mock(GameCanvasModel.class);
    protected GameObj mockObj = mock(GameObj.class);
    protected GameObj mockObj2 = mock(GameObj.class);

    //////////////////////////////////////////////////
    // Setup
    //////////////////////////////////////////////////

    @Before
    public void setup() {
        reset(mockCanvas, mockGraphics, mockObj);

        MainLoop mainLoop = MainLoopFactory.getMainLoop(STANDARD_UPS);
        mainLoopModel = mainLoop.getModel();
        mainLoopController = mainLoop.getController();

        mainLoop.setReferences(mockCanvas);
    }

    @Test
    public void testSetup() {
        // just see if setup works correctly
    }

    @Test
    public void testInit() {
        mainLoopModel.assertValid();
    }
}
