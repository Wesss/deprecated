package org.unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

public class MainLoopBasicInterTest extends MainLoopTest {

    @Test
    public void containsNothing() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        assertFalse(mainLoop.contains(mockObj));

        mainLoopModel.assertValid();
        verifyZeroInteractions(mockObj);
    }

    @Test
    public void addsObj() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        mainLoop.add(mockObj);
        assertTrue(mainLoop.contains(mockObj));

        mainLoopModel.assertValid();
        verifyZeroInteractions(mockObj);
    }
}
