package org.framework;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

/**
 * An Interface for drawing to the game's panel (aka. actually drawing images onto the screen).
 * Serves as a transformer for java.awt.Graphics
 * TODO describe usage
 */
public class GamePanelGraphics {

    // TODO make this object mutable, pass in new graphics object on each re-run instead of creating new PanelGraphics object
    // TODO figure out resizing of images/Strings
    // TODO figure out documentation linking to overloaded methods

    //////////////////////////////////////////////////
    // Definition
    //////////////////////////////////////////////////

    /*
     * componentGraphics != null
     * gamePanel != null
     */
    private Graphics componentGraphics;
    private GamePanel panel;

    public GamePanelGraphics(Graphics g, GamePanel panel) {
        componentGraphics = g;
        this.panel = panel;
    }

    //////////////////////////////////////////////////
    // Framework Functionality
    //////////////////////////////////////////////////

    public int getActualX() {
        return panel.getActualX();
    }

    public int getActualY() {
        return panel.getActualY();
    }

    public int getVirtualX() {
        return panel.getVirtualX();
    }

    public void setVirtualX(int virtualX) {
        panel.setVirtualX(virtualX);
    }

    public int getVirtualY() {
        return panel.getVirtualY();
    }

    public void setVirtualY(int virtualY) {
        panel.setVirtualY(virtualY);
    }

    // TODO test these functions
    private int transformX(int x) {
        return transform(x, panel.getActualX(), panel.getVirtualX());
    }

    private int transformY(int y) {
        return transform(y, panel.getActualY(), panel.getVirtualY());
    }

    private static int transform(int metric, int actual, int virtual) {
        long product = ((long)metric) * ((long)actual);
        return (int)(product / virtual);
    }

    private int[] transformX(int[] metrics) {
        int[] result = new int[metrics.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = transformX(metrics[i]);
        }
        return result;
    }

    private int[] transformY(int[] metrics) {
        int[] result = new int[metrics.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = transformY(metrics[i]);
        }
        return result;
    }

    //////////////////////////////////////////////////
    // Graphics Proxy
    //////////////////////////////////////////////////

    /**
     * @see Graphics#translate
     */
    public void translate(int x, int y) {
        componentGraphics.translate(transformX(x), transformY(y));
    }

    /**
     * @see Graphics#getColor
     */
    public Color getColor() {
        return componentGraphics.getColor();
    }

    /**
     * @see Graphics#setColor
     */
    public void setColor(Color c) {
        componentGraphics.setColor(c);
    }

    /**
     * @see Graphics#setPaintMode
     */
    public void setPaintMode() {
        componentGraphics.setPaintMode();
    }

    /**
     * @see Graphics#setXORMode
     */
    public void setXORMode(Color c1) {
        componentGraphics.setXORMode(c1);
    }

    /**
     * All String related drawing currently does not support scaling
     * @see Graphics#getFont
     */
    public Font getFont() {
        return componentGraphics.getFont();
    }

    /**
     * All String related drawing currently does not support scaling
     * @see Graphics#setFont
     */
    public void setFont(Font font) {
        componentGraphics.setFont(font);
    }

    /**
     * All String related drawing currently does not support scaling
     * @see Graphics#getFontMetrics
     */
    public FontMetrics getFontMetrics(Font f) {
        return componentGraphics.getFontMetrics(f);
    }

    /**
     * @see Graphics#getClipBounds
     */
    public Rectangle getClipBounds() {
        return componentGraphics.getClipBounds();
    }

    /**
     * @see Graphics#clipRect
     */
    public void clipRect(int x, int y, int width, int height) {
        componentGraphics.clipRect(transformX(x), transformY(y), transformX(width), transformY(height));
    }

    /**
     * @see Graphics#setClip
     */
    public void setClip(int x, int y, int width, int height) {
        componentGraphics.setClip(transformX(x), transformY(y), transformX(width), transformY(height));
    }

    /**
     * @see Graphics#getClip
     */
    public Shape getClip() {
        return componentGraphics.getClip();
    }

    /**
     * @see Graphics#setClip
     */
    public void setClip(Shape clip) {
        componentGraphics.setClip(clip);
    }

    /**
     * @see Graphics#copyArea
     */
    public void copyArea(int x, int y, int width, int height, int dx, int dy) {
        componentGraphics.copyArea(transformX(x), transformY(y), transformX(width), transformY(height), transformX(dx), transformY(dy));
    }

    /**
     * @see Graphics#drawLine
     */
    public void drawLine(int x1, int y1, int x2, int y2) {
        componentGraphics.drawLine(transformX(x1), transformY(y1), transformX(x2), transformY(y2));
    }

    /**
     * @see Graphics#fillRect
     */
    public void fillRect(int x, int y, int width, int height) {
        componentGraphics.fillRect(transformX(x), transformY(y), transformX(width), transformY(height));
    }

    /**
     * @see Graphics#clearRect
     */
    public void clearRect(int x, int y, int width, int height) {
        componentGraphics.clearRect(transformX(x), transformY(y), transformX(width), transformY(height));
    }

    /**
     * @see Graphics#drawRoundRect
     */
    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        componentGraphics.drawRoundRect(transformX(x), transformY(y), transformX(width), transformY(height), arcWidth, arcHeight);
    }

    /**
     * @see Graphics#fillRoundRect
     */
    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        componentGraphics.fillRoundRect(transformX(x), transformY(y), transformX(width), transformY(height), arcWidth, arcHeight);
    }

    /**
     * @see Graphics#drawOval
     */
    public void drawOval(int x, int y, int width, int height) {
        componentGraphics.drawOval(transformX(x), transformY(y), transformX(width), transformY(height));
    }

    /**
     * @see Graphics#fillOval
     */
    public void fillOval(int x, int y, int width, int height) {
        componentGraphics.fillOval(transformX(x), transformY(y), transformX(width), transformY(height));
    }

    /**
     * @see Graphics#drawArc
     */
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        componentGraphics.drawArc(transformX(x), transformY(y), transformX(width), transformY(height), startAngle, arcAngle);
    }

    /**
     * @see Graphics#fillArc
     */
    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        componentGraphics.fillArc(transformX(x), transformY(y), transformX(width), transformY(height), startAngle, arcAngle);
    }

    /**
     * @see Graphics#drawPolyline
     */
    public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
        componentGraphics.drawPolyline(transformX(xPoints), transformY(yPoints), nPoints);
    }

    /**
     * @see Graphics#drawPolygon
     */
    public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        componentGraphics.drawPolygon(transformX(xPoints), transformY(yPoints), nPoints);
    }

    /**
     * @see Graphics#fillPolygon
     */
    public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        componentGraphics.fillPolygon(transformX(xPoints), transformY(yPoints), nPoints);
    }

    /**
     * All String related drawing currently does not support scaling
     * @see Graphics#drawString
     */
    public void drawString(String str, int x, int y) {
        componentGraphics.drawString(str, transformX(x), transformY(y));
    }

    /**
     * All String related drawing currently does not support scaling
     * @see Graphics#drawString
     */
    public void drawString(AttributedCharacterIterator iterator, int x, int y) {
        componentGraphics.drawString(iterator, transformX(x), transformY(y));
    }

    /**
     * @see Graphics#drawImage
     */
    public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
        return componentGraphics.drawImage(img, transformX(x), transformY(y), observer);
    }

    /**
     * @see Graphics#drawImage
     */
    public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
        return componentGraphics.drawImage(img, transformX(x), transformY(y), transformX(width), transformY(height), observer);
    }

    /**
     * @see Graphics#drawImage
     */
    public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
        return componentGraphics.drawImage(img, transformX(x), transformY(y), bgcolor, observer);
    }

    /**
     * @see Graphics#drawImage
     */
    public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
        return componentGraphics.drawImage(img, transformX(x), transformY(y), transformX(width), transformY(height), bgcolor, observer);
    }

    /**
     * @see Graphics#drawImage
     */
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
                             ImageObserver observer) {
        return componentGraphics.drawImage(img, transformX(dx1), transformY(dy1), transformX(dx2), transformY(dy2), sx1, sy1, sx2, sy2, observer);
    }

    /**
     * @see Graphics#drawImage
     */
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
                             Color bgcolor, ImageObserver observer) {
        return componentGraphics.drawImage(img, transformX(dx1), transformY(dy1), transformX(dx2), transformY(dy2), sx1, sy1, sx2, sy2, bgcolor, observer);
    }

    /**
     * @see Graphics#dispose
     */
    public void dispose() {
        componentGraphics.dispose();
    }
}
