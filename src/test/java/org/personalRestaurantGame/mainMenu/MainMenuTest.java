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

    private RestaurantGame mockGame = mock(RestaurantGame.class);
    private MainLoopCustomGroupsInterface mockMainLoop = mock(MainLoopCustomGroupsInterface.class);
    private MainLoopGroup backgroundGroup;
    private MainLoopGroup foregroundGroup;
    private MainLoopGroup maskGroup;
    private MainMenuController controller;

    @Before
    public void setup() {
        reset(mockGame, mockMainLoop);

        maskGroup = new MainLoopGroupTest(DEFAULT_PRIORITY, MASK_LAYER);
        foregroundGroup = new MainLoopGroupTest(DEFAULT_PRIORITY, FOREGROUND_LAYER);
        when(mockMainLoop.createGroup(DEFAULT_PRIORITY, MASK_LAYER)).thenReturn(maskGroup);
        when(mockMainLoop.createGroup(DEFAULT_PRIORITY, FOREGROUND_LAYER)).thenReturn(foregroundGroup);

        MainMenu mainMenu = MainMenuFactory.getMainMenu(mockGame, mockMainLoop);
        controller = mainMenu.getMainMenuController();
        nextFrame(); // to resolve add actions
    }

    @Test
    public void exitButton() {
        controller.moveSelectorDown();
        controller.moveSelectorDown();
        controller.selectWithKeyboard();
        nextFrame();

        verify(mockGame).exit();
    }

    private void nextFrame() {
        foregroundGroup.update();
        maskGroup.update();
    }
}
