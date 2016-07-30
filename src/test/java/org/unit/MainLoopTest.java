package org.unit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.awt.Graphics;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.framework.*;
import org.framework.interfaces.GameObj;
import org.junit.Before;
import org.junit.Test;

public abstract class MainLoopTest {

    public static final int NO_UPS = 0;
    public static final int SLOW_UPS = 2;
    public static final int STANDARD_UPS = 60;
    public static final int STRESSTEST_UPS = 300;

    protected MainLoop mainloop = null;

    protected Method mainloopValidate = null;
    protected Method nextFrame = null;

    protected GamePanelGraphics mockGraphics = mock(GamePanelGraphics.class);
    protected GamePanel mockPanel = mock(GamePanel.class);
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

            Method ref = MainLoop.class.getDeclaredMethod("setReferences", GamePanel.class);
            ref.setAccessible(true);
            ref.invoke(mainloop, mockPanel);

            mainloopValidate = MainLoop.class.getDeclaredMethod("assertValid");
            mainloopValidate.setAccessible(true);

            nextFrame = MainLoop.class.getDeclaredMethod("nextFrame", GamePanelGraphics.class);
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
