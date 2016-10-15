package org.personalRestaurantGame.mainMenu;

import org.framework.domain.GameEventListener;
import org.framework.mainLoop.MainLoopCustomGroupsInterface;
import org.framework.mainLoop.MainLoopGroup;
import org.framework.test.FakeMainLoopCustomGroupsInterface;
import org.framework.test.MainLoopGroupTest;
import org.junit.Before;
import org.junit.Test;
import org.personalRestaurantGame.RestaurantGame;

import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.*;
import static org.mockito.Mockito.*;
import static org.personalRestaurantGame.mainMenu.MainMenuModel.*;

public class MainMenuTest {

    private GameEventListener eventListener;
    private RestaurantGame mockGame = mock(RestaurantGame.class);
    private FakeMainLoopCustomGroupsInterface fakeMainLoop;

    @Before
    public void setup() {
        reset(mockGame);
        fakeMainLoop = FakeMainLoopCustomGroupsInterface.getFake(RestaurantGame.MAXIMUM_UPDATE_PRIORITY);

        MainMenu mainMenu = MainMenuFactory.getMainMenu(mockGame, fakeMainLoop);
        eventListener = mainMenu.dispatchEventListener();
        fakeMainLoop.nextFrame(); // to resolve add actions
    }

    @Test
    public void exitButton() {
        moveSelectorDown();
        moveSelectorDown();
        selectWithKeyboard();

        verify(mockGame).exit();
    }

    @Test
    public void newGameButton() {
        moveSelectorDown();
        selectWithKeyboard();
        for (int i = 0; i < TRANSITION_OUT_CYCLES + 1; i++) {
            fakeMainLoop.nextFrame();
        }

        verify(mockGame).swapState(RestaurantGame.State.NEW_GAME);
    }

    private void moveSelectorDown() {
        eventListener.keyPressed(VK_DOWN);
        eventListener.keyReleased(VK_DOWN);
    }

    private void selectWithKeyboard() {
        eventListener.keyPressed(VK_ENTER);
        eventListener.keyReleased(VK_ENTER);
    }
}
