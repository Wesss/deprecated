package org.personalRestaurantGame.mainMenu;

import org.framework.mainLoop.MainLoopCustomGroupsInterface;
import org.framework.mainLoop.MainLoopGroup;
import org.framework.test.MainLoopGroupTest;
import org.junit.Before;
import org.junit.Test;
import org.personalRestaurantGame.RestaurantGame;

import static org.mockito.Mockito.*;
import static org.personalRestaurantGame.mainMenu.MainMenuModel.*;

public class MainMenuTest {

    // TODO abstract out a test main loop custom groups interface

    private MainMenuModel model;
    private RestaurantGame mockGame = mock(RestaurantGame.class);
    private MainLoopCustomGroupsInterface mockMainLoop = mock(MainLoopCustomGroupsInterface.class);
    private MainLoopGroup backgroundGroup;
    private MainLoopGroup foregroundGroup;
    private MainLoopGroup maskGroup;

    @Before
    public void setup() {
        reset(mockGame, mockMainLoop);

        maskGroup = new MainLoopGroupTest(DEFAULT_PRIORITY, MASK_LAYER);
        foregroundGroup = new MainLoopGroupTest(DEFAULT_PRIORITY, FOREGROUND_LAYER);
        when(mockMainLoop.createGroup(DEFAULT_PRIORITY, MASK_LAYER)).thenReturn(maskGroup);
        when(mockMainLoop.createGroup(DEFAULT_PRIORITY, FOREGROUND_LAYER)).thenReturn(foregroundGroup);

        MainMenu mainMenu = MainMenuFactory.getMainMenu(mockGame, mockMainLoop);
        model = mainMenu.getModel();
        nextFrame(); // to resolve add actions
    }

    @Test
    public void exitButton() {
        model.moveSelectorDown();
        model.moveSelectorDown();
        model.selectWithKeyboard();
        nextFrame();

        verify(mockGame).exit();
    }

    @Test
    public void newGameButton() {
        model.moveSelectorDown();
        model.selectWithKeyboard();
        for (int i = 0; i < TRANSITION_OUT_CYCLES + 1; i++) {
            nextFrame();
        }

        verify(mockGame).swapState(RestaurantGame.State.NEW_GAME);
    }

    private void nextFrame() {
        foregroundGroup.update();
        maskGroup.update();
    }
}
