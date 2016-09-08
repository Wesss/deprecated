import org.framework.GameFramework;
import org.framework.canvas.GameCanvasController;
import org.framework.interfaces.Game;
import org.framework.mainLoop.MainLoopController;

public class RestaurantGame implements Game {

    public static final int FPS = 60;

    public static void main(String[] args) throws InstantiationException {
        GameFramework.startGame(RestaurantGame.class, new EventListener(), FPS);
    }

    public RestaurantGame(MainLoopController mainLoop, GameCanvasController canvas) {

    }
}
