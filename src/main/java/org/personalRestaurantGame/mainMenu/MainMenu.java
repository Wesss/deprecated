package org.personalRestaurantGame.mainMenu;

import org.framework.domain.GameEventListener;
import org.personalRestaurantGame.domain.GamePipeline;
import org.personalRestaurantGame.domain.GameStateStore;

public class MainMenu implements GamePipeline {

    private MainMenuModel model;
    private MainMenuController controller;
    private MainMenuEventListener listener;

    // TODO music
    // TODO wire in getting of players name
    public MainMenu(MainMenuModel model, MainMenuController controller, MainMenuEventListener listener) {
        this.model = model;
        this.controller = controller;
        this.listener = listener;
    }

    @Override
    public void acceptGameStateStore(GameStateStore store) {
        model.setStore(store);
    }

    @Override
    public GameStateStore returnGameStateStore() {
        return model.getStore();
    }

    @Override
    public GameEventListener dispatchEventListener() {
        return listener;
    }

    // Transition notes
    //disable buttons (cut off events) while transitioning
    //set flag to transitioning out
    //let update handle fading or moving out
    //let update call swap state
}
