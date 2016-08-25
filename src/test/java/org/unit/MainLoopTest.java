package org.unit;

import org.framework.canvas.GameCanvasModel;
import org.framework.canvas.GameCanvasGraphics;
import org.framework.interfaces.GameObj;
import org.framework.mainLoop.MainLoop;
import org.framework.mainLoop.MainLoopFactory;
import org.framework.mainLoop.MainLoopFactoryFactory;
import org.framework.mainLoop.MainLoopModel;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public abstract class MainLoopTest {

    public static final int NO_UPS = 0;
    public static final int SLOW_UPS = 2;
    public static final int STANDARD_UPS = 60;
    public static final int STRESSTEST_UPS = 300;

    protected MainLoopModel mainLoopModel;
    protected MainLoop mainLoop;

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

        MainLoopFactory factory = MainLoopFactoryFactory.getMainLoopFactory();
        factory.constructMainLoop(STANDARD_UPS);
        mainLoopModel = factory.getMainLoopModel();
        mainLoop = factory.getMainLoop();

        mainLoopModel.setReferences(mockCanvas);
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
