package org.framework.interfaces;

/**
 * TODO
 * @author Wesley
 */
public enum AspectRatio {

    ONE_TO_ONE (1, 1),
    FOUR_TO_THREE (4, 3),
    SIXTEEN_TO_NINE (16, 9);

    private final int xRatio;
    private final int yRatio;

    AspectRatio(int x, int y) {
        xRatio = x;
        yRatio = y;
    }

    public int getXRatio() {
        return xRatio;
    }

    public int getYRatio() {
        return yRatio;
    }
}
