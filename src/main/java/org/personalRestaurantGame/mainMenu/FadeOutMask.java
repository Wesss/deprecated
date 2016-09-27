package org.personalRestaurantGame.mainMenu;

import org.framework.canvas.GameCanvasGraphics;
import org.framework.domain.GameObj;

public class FadeOutMask implements GameObj {

    private int framesToFade;
    private int elapsedFrames;

    public FadeOutMask(int framesToFade) {
        this.framesToFade = framesToFade;
        this.elapsedFrames = 0;
    }

    @Override
    public void update() {
        elapsedFrames++;
    }

    @Override
    public void paint(GameCanvasGraphics g) {
        int opacityPercentage = (int)((100.0 * elapsedFrames) / framesToFade);
        // TODO implement drawing of fade out mask
        g.drawString("Fade out mask: " + opacityPercentage + "%", 100, 100);
    }
}
