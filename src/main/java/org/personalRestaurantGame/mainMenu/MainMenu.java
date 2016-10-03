package org.personalRestaurantGame.mainMenu;

import org.framework.domain.GameEventListener;
import org.personalRestaurantGame.domain.GamePipeline;
import org.personalRestaurantGame.domain.GameStateStore;

public class MainMenu implements GamePipeline {

    private MainMenuModel model;
    private MainMenuEventListener listener;

    // TODO music
    // TODO wire in getting of players name
    public MainMenu(MainMenuModel model, MainMenuEventListener listener) {
        this.model = model;
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

    public MainMenuModel getModel() {
        return model;
    }
}
