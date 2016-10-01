package org.personalRestaurantGame.mainMenu;

public class MainMenuController {

    MainMenuModel model;

    public MainMenuController() {

    }

    public void setModelReference(MainMenuModel model) {
        this.model = model;
    }

    public void selectWithKeyboard() {
        model.getButtonList().selectWithKeyboard();
    }

    public void moveSelectorUp() {
        model.getButtonList().moveSelectorUp();
    }

    public void moveSelectorDown() {
        model.getButtonList().moveSelectorDown();
    }

    public void updateMousePosition(int x, int y) {
        model.getButtonList().updateMousePosition(x, y);
    }

    public void selectWithMouse(int x, int y) {
        model.getButtonList().selectWithMouse(x, y);
    }
}
