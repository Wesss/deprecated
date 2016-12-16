package org.wesss.game_framework.test;

import org.wesss.game_framework.domain.GameObj;
import org.wesss.game_framework.mainLoop.MainLoopAdvancedInterface;
import org.wesss.game_framework.mainLoop.MainLoopGroup;

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
