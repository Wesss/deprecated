package org.unit;

import org.framework.canvas.GameCanvas;
import org.framework.canvas.GameCanvasGraphics;
import org.framework.interfaces.GameObj;
import org.framework.mainLoop.MainLoop;
import org.framework.mainLoop.MainLoopFactory;
import org.framework.mainLoop.MainLoopFactoryFactory;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public abstract class MainLoopTest {

    public static final int NO_UPS = 0;
    public static final int SLOW_UPS = 2;
    public static final int STANDARD_UPS = 60;
    public static final int STRESSTEST_UPS = 300;

    protected MainLoop mainloop = null;
    protected Method mainloopValidate = null;
    protected Method nextFrame = null;

    protected GameCanvasGraphics mockGraphics = mock(GameCanvasGraphics.class);
    protected GameCanvas mockPanel = mock(GameCanvas.class);
    protected GameObj mockObj = mock(GameObj.class);
    protected GameObj mockObj2 = mock(GameObj.class);

    //////////////////////////////////////////////////
    // Setup
    //////////////////////////////////////////////////

    @Before
    public void setup() {
        reset(mockPanel, mockGraphics, mockObj);

        mainloop = null;
        try {
            Method getFactory = MainLoopFactoryFactory.class.getDeclaredMethod("getMainLoopFactory");
            getFactory.setAccessible(true);
            MainLoopFactory factory = (MainLoopFactory) getFactory.invoke(null);

            Method init = MainLoopFactory.class.getDeclaredMethod("constructMainLoop", int.class);
            init.setAccessible(true);
            init.invoke(factory, STANDARD_UPS);
            mainloop = factory.getMainLoop();

            Method ref = MainLoop.class.getDeclaredMethod("setReferences", GameCanvas.class);
            ref.setAccessible(true);
            ref.invoke(mainloop, mockPanel);

            mainloopValidate = MainLoop.class.getDeclaredMethod("assertValid");
            mainloopValidate.setAccessible(true);

            nextFrame = MainLoop.class.getDeclaredMethod("nextFrame", GameCanvasGraphics.class);
            nextFrame.setAccessible(true);
        } catch (SecurityException | IllegalArgumentException | NoSuchMethodException
                | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    public void testSetup() {
        // just see if setup works correctly
    }

    @Test
    public void testInit() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        mainloopValidate.invoke(mainloop);
    }
}
