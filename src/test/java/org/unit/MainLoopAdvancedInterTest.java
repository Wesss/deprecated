package org.unit;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.InvocationTargetException;

import org.framework.MainLoop.MainLoopAction;
import org.framework.MainLoopAdvancedInterface;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class MainLoopAdvancedInterTest extends MainLoopTest {

    private MainLoopAdvancedInterface advInter = null;

    @Override
    @Before
    public void setup() {
        super.setup();
        advInter = mainloop.advancedInterface();
    }

    @Test
    public void advInsertAction() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        advInter.insertAction(advInter.createAddAction(mockObj, 0, 0), 0);
        mainloopValidate.invoke(mainloop);
        verifyZeroInteractions(mockObj);
    }

    @Test
    public void advContainsNoAction() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        assertFalse(advInter.containsAction(advInter.createAddAction(mockObj, 0, 0)));
        assertFalse(advInter.containsAction(advInter.createAddAction(mockObj, 0, 0), 0));
        mainloopValidate.invoke(mainloop);
        verifyZeroInteractions(mockObj);
    }

    @Test
    public void advDeleteNoAction() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        advInter.deleteAction(advInter.createAddAction(mockObj, 0, 0));
        mainloopValidate.invoke(mainloop);
        verifyZeroInteractions(mockObj);
    }

    @Test
    public void advContainsNoObj() {
        assertFalse(advInter.contains(mockObj));
    }

    @Test
    public void advContainsAction() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        MainLoopAction action = advInter.createAddAction(mockObj, 0, 0);
        advInter.insertAction(action, 0);

        mainloopValidate.invoke(mainloop);
        assertTrue(advInter.containsAction(action));
        assertTrue(advInter.containsAction(action, 0));
        assertFalse(advInter.contains(mockObj));
        verifyZeroInteractions(mockObj);
    }

    @Test
    public void advDeleteAction() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        MainLoopAction action = advInter.createAddAction(mockObj, 0, 0);
        advInter.insertAction(action, 0);
        advInter.deleteAction(action);

        mainloopValidate.invoke(mainloop);
        assertFalse(advInter.containsAction(action));
        assertFalse(advInter.containsAction(action, 0));
        verifyZeroInteractions(mockObj);
    }

    @Test
    public void advNextFrameNoObjs() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        nextFrame.invoke(mainloop, mockGraphics);
        mainloopValidate.invoke(mainloop);
    }

    @Test
    public void advDeleteAllNoActions() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        advInter.deleteAllActions();
        mainloopValidate.invoke(mainloop);
    }

    @Test
    public void advDeleteAllActions() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        MainLoopAction action1 = advInter.createAddAction(mockObj, 0, 0);
        MainLoopAction action2 = advInter.createClearAction();
        advInter.insertAction(action1, 1);
        advInter.insertAction(action2, 0);
        advInter.deleteAllActions();

        mainloopValidate.invoke(mainloop);
        assertFalse(advInter.containsAction(action1));
        assertFalse(advInter.containsAction(action2));
        verifyZeroInteractions(mockObj);
    }

    @Test
    public void advInsertObj() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        MainLoopAction insert = advInter.createAddAction(mockObj, 0, 0);
        advInter.insertAction(insert, 0);
        nextFrame.invoke(mainloop, mockGraphics);

        mainloopValidate.invoke(mainloop);
        assertFalse(advInter.containsAction(insert));
        assertTrue(advInter.contains(mockObj));
        verify(mockObj).paint(mockGraphics);
    }

    @Test
    public void advInsertObjHighGroupLayerPriority() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        MainLoopAction insert = advInter.createAddAction(mockObj, 10, 10);
        advInter.insertAction(insert, 10);
        nextFrame.invoke(mainloop, mockGraphics);

        mainloopValidate.invoke(mainloop);
        assertFalse(advInter.containsAction(insert));
        assertTrue(advInter.contains(mockObj));
        verify(mockObj).paint(mockGraphics);
    }

    @Test
    public void advInsertSameObjTwice() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        MainLoopAction action1 = advInter.createAddAction(mockObj, 0, 0);
        MainLoopAction action2 = advInter.createAddAction(mockObj, 1, 1);
        advInter.insertAction(action1, 0);
        advInter.insertAction(action2, 1);

        mainloopValidate.invoke(mainloop);
        assertTrue(advInter.containsAction(action1));
        assertTrue(advInter.containsAction(action1, 0));
        assertTrue(advInter.containsAction(action2));
        assertTrue(advInter.containsAction(action2, 1));
        verifyZeroInteractions(mockObj);
    }

    @Test
    public void advInsertSameObjTwiceDiffFrames() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        advInter.insertAction(advInter.createAddAction(mockObj, 0, 0), 0);
        nextFrame.invoke(mainloop, mockGraphics);
        advInter.insertAction(advInter.createAddAction(mockObj, 1, 1), 0);
        nextFrame.invoke(mainloop, mockGraphics);

        mainloopValidate.invoke(mainloop);
        assertTrue(advInter.contains(mockObj));
        verify(mockObj, times(2)).paint(mockGraphics);
    }

    @Test
    public void advRemoveNoObjs() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        MainLoopAction remove = advInter.createRemoveAction(mockObj);
        advInter.insertAction(remove, 1);
        nextFrame.invoke(mainloop, mockGraphics);

        mainloopValidate.invoke(mainloop);
        verifyZeroInteractions(mockObj);
    }

    @Test
    public void advRemoveObj() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        MainLoopAction insert = advInter.createAddAction(mockObj, 0, 0);
        advInter.insertAction(insert, 0);
        nextFrame.invoke(mainloop, mockGraphics);
        MainLoopAction remove = advInter.createRemoveAction(mockObj);
        advInter.insertAction(remove, 0);
        nextFrame.invoke(mainloop, mockGraphics);

        mainloopValidate.invoke(mainloop);
        assertFalse(advInter.containsAction(remove));
        assertFalse(advInter.contains(mockObj));
        verify(mockObj).update();
        verify(mockObj).paint(mockGraphics);
    }

    @Test
    public void advRemoveObjHighGroupLayerPriority() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        MainLoopAction insert = advInter.createAddAction(mockObj, 10, 10);
        advInter.insertAction(insert, 10);
        nextFrame.invoke(mainloop, mockGraphics);
        MainLoopAction remove = advInter.createRemoveAction(mockObj);
        advInter.insertAction(remove, 11);
        nextFrame.invoke(mainloop, mockGraphics);

        mainloopValidate.invoke(mainloop);
        assertFalse(advInter.containsAction(remove));
        assertFalse(advInter.contains(mockObj));
        verify(mockObj).update();
        verify(mockObj).paint(mockGraphics);
    }

    @Test
    public void advClearNoObjs() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        MainLoopAction clear = advInter.createClearAction();
        advInter.insertAction(clear, 0);
        nextFrame.invoke(mainloop, mockGraphics);

        mainloopValidate.invoke(mainloop);
    }

    @Test
    public void advClearObjs() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        MainLoopAction insert = advInter.createAddAction(mockObj, 0, 0);
        advInter.insertAction(insert, 0);
        nextFrame.invoke(mainloop, mockGraphics);
        MainLoopAction clear = advInter.createClearAction();
        advInter.insertAction(clear, 0);
        nextFrame.invoke(mainloop, mockGraphics);

        mainloopValidate.invoke(mainloop);
        assertFalse(advInter.containsAction(clear));
        assertFalse(advInter.contains(mockObj));
        verify(mockObj).update();
        verify(mockObj).paint(mockGraphics);
    }

    @Test
    public void advActionGroupOrder() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        MainLoopAction insert = advInter.createAddAction(mockObj, 0, 0);
        MainLoopAction insert2 = advInter.createAddAction(mockObj2, 0, 0);
        MainLoopAction remove = advInter.createRemoveAction(mockObj);
        MainLoopAction remove2 = advInter.createRemoveAction(mockObj2);
        advInter.insertAction(insert, 0);
        advInter.insertAction(remove, 1);
        advInter.insertAction(insert2, 1);
        advInter.insertAction(remove2, 0);
        nextFrame.invoke(mainloop, mockGraphics);

        mainloopValidate.invoke(mainloop);
        assertFalse(advInter.contains(mockObj));
        assertTrue(advInter.contains(mockObj2));
    }

    @Test
    public void advObjPaintOrder() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        InOrder inOrder = inOrder(mockObj, mockObj2);

        MainLoopAction insert = advInter.createAddAction(mockObj, 0, 1);
        MainLoopAction insert2 = advInter.createAddAction(mockObj2, 0, 0);
        advInter.insertAction(insert, 0);
        advInter.insertAction(insert2, 0);
        nextFrame.invoke(mainloop, mockGraphics);

        mainloopValidate.invoke(mainloop);
        inOrder.verify(mockObj2).paint(mockGraphics);
        inOrder.verify(mockObj).paint(mockGraphics);
    }

    @Test
    public void advObjUpdateOrder() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        InOrder inOrder = inOrder(mockObj, mockObj2);

        MainLoopAction insert = advInter.createAddAction(mockObj, 1, 0);
        MainLoopAction insert2 = advInter.createAddAction(mockObj2, 0, 0);
        advInter.insertAction(insert, 0);
        advInter.insertAction(insert2, 0);
        nextFrame.invoke(mainloop, mockGraphics);
        nextFrame.invoke(mainloop, mockGraphics);

        mainloopValidate.invoke(mainloop);
        inOrder.verify(mockObj2).update();
        inOrder.verify(mockObj).update();
    }
}
