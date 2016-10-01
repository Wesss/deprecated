package org.framework.test;

import org.framework.domain.GameObj;
import org.framework.mainLoop.MainLoopAdvancedInterface;
import org.framework.mainLoop.MainLoopGroup;

import static org.mockito.Mockito.mock;

/**
 * An instantiable test instance of MainLoopGroup
 */
public class MainLoopGroupTest extends MainLoopGroup {

    public MainLoopGroupTest(int priority, int layer) {
        super(mock(MainLoopAdvancedInterface.class), priority, layer);
    }

    @Override
    public void update() {
        for (GameObj gameObj : getGameObjsInMainLoop()) {
            gameObj.update();
        }

        super.update();
    }
}
