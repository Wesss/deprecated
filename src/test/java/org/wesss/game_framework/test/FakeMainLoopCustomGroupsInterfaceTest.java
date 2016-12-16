package org.wesss.game_framework.test;

import org.wesss.game_framework.canvas.GameCanvasGraphics;
import org.wesss.game_framework.domain.GameObj;
import org.wesss.game_framework.mainLoop.MainLoopGroup;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class FakeMainLoopCustomGroupsInterfaceTest {

    private static final int MAX_PRIORITY = 5;
    private FakeMainLoopCustomGroupsInterface fakeMainLoop;
    private MainLoopGroup group;
    private GameCanvasGraphics mockGraphics = mock(GameCanvasGraphics.class);
    private GameObj obj1 = mock(GameObj.class);
    private GameObj obj2 = mock(GameObj.class);

    @Before
    public void setup() {
        reset(mockGraphics, obj1, obj2);
        fakeMainLoop = FakeMainLoopCustomGroupsInterface.getFake(MAX_PRIORITY);
        group = fakeMainLoop.createGroup(0, 0);
    }

    @Test
    public void gameObjsGetPainted() {
        group.add(obj1);
        fakeMainLoop.nextFrame(mockGraphics);

        verify(obj1).paint(mockGraphics);
    }

    @Test
    public void gameObjsGetFirstUpdateOnSecondFrame() {
        group.add(obj1);

        fakeMainLoop.nextFrame(mockGraphics);
        verify(obj1, never()).update();
        fakeMainLoop.nextFrame(mockGraphics);
        verify(obj1).update();
    }
}
