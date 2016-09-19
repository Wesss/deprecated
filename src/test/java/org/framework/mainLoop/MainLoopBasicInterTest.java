package org.framework.mainLoop;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verifyZeroInteractions;

public class MainLoopBasicInterTest extends MainLoopTest {

    @Test
    public void containsNothing() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        assertFalse(mainLoopController.contains(mockObj));

        mainLoopModel.assertValid();
        verifyZeroInteractions(mockObj);
    }

    @Test
    public void addsObj() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        mainLoopController.add(mockObj);
        assertTrue(mainLoopController.contains(mockObj));

        mainLoopModel.assertValid();
        verifyZeroInteractions(mockObj);
    }
}
