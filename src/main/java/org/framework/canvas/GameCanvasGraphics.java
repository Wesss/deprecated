package org.framework.canvas;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

/**
 * An Interface for drawing to the game's canvas (aka. actually drawing images onto the screen).
 * Serves as a transformer for java.awt.Graphics
 * TODO describe usage
 */
public class GameCanvasGraphics {

    // TODO make this object mutable, pass in new graphics object on each re-run instead of creating new PanelGraphics object
    // TODO figure out resizing of Strings (visit GraphicsEnvironment.getLocalGraphicsEnvironment().preferProportionalFonts();
    // TODO make this extend Graphics

    //////////////////////////////////////////////////
    // Definition
    //////////////////////////////////////////////////

    /*
     * componentGraphics != null
     * gamePanel != null
     */
    private Graphics componentGraphics;
    private GameCanvas canvas;

    public GameCanvasGraphics(Graphics g, GameCanvas canvas) {
        componentGraphics = g;
        this.canvas = canvas;
    }

    //////////////////////////////////////////////////
    // Graphics Proxy
    //////////////////////////////////////////////////

    /**
     * @see Graphics#translate
     */
    public void translate(int x, int y) {
        componentGraphics.translate(canvas.virtualToActualX(x), canvas.virtualToActualY(y));
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
        componentGraphics.clipRect(canvas.virtualToActualX(x), canvas.virtualToActualY(y), canvas.virtualToActualX(width), canvas.virtualToActualY(height));
    }

    /**
     * @see Graphics#setClip
     */
    public void setClip(int x, int y, int width, int height) {
        componentGraphics.setClip(canvas.virtualToActualX(x), canvas.virtualToActualY(y), canvas.virtualToActualX(width), canvas.virtualToActualY(height));
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
        componentGraphics.copyArea(canvas.virtualToActualX(x), canvas.virtualToActualY(y), canvas.virtualToActualX(width), canvas.virtualToActualY(height), canvas.virtualToActualX(dx), canvas.virtualToActualY(dy));
    }

    /**
     * @see Graphics#drawLine
     */
    public void drawLine(int x1, int y1, int x2, int y2) {
        componentGraphics.drawLine(canvas.virtualToActualX(x1), canvas.virtualToActualY(y1), canvas.virtualToActualX(x2), canvas.virtualToActualY(y2));
    }

    /**
     * @see Graphics#fillRect
     */
    public void fillRect(int x, int y, int width, int height) {
        componentGraphics.fillRect(canvas.virtualToActualX(x), canvas.virtualToActualY(y), canvas.virtualToActualX(width), canvas.virtualToActualY(height));
    }

    /**
     * @see Graphics#clearRect
     */
    public void clearRect(int x, int y, int width, int height) {
        componentGraphics.clearRect(canvas.virtualToActualX(x), canvas.virtualToActualY(y), canvas.virtualToActualX(width), canvas.virtualToActualY(height));
    }

    /**
     * @see Graphics#drawRoundRect
     */
    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        componentGraphics.drawRoundRect(canvas.virtualToActualX(x), canvas.virtualToActualY(y), canvas.virtualToActualX(width), canvas.virtualToActualY(height), arcWidth, arcHeight);
    }

    /**
     * @see Graphics#fillRoundRect
     */
    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        componentGraphics.fillRoundRect(canvas.virtualToActualX(x), canvas.virtualToActualY(y), canvas.virtualToActualX(width), canvas.virtualToActualY(height), arcWidth, arcHeight);
    }

    /**
     * @see Graphics#drawOval
     */
    public void drawOval(int x, int y, int width, int height) {
        componentGraphics.drawOval(canvas.virtualToActualX(x), canvas.virtualToActualY(y), canvas.virtualToActualX(width), canvas.virtualToActualY(height));
    }

    /**
     * @see Graphics#fillOval
     */
    public void fillOval(int x, int y, int width, int height) {
        componentGraphics.fillOval(canvas.virtualToActualX(x), canvas.virtualToActualY(y), canvas.virtualToActualX(width), canvas.virtualToActualY(height));
    }

    /**
     * @see Graphics#drawArc
     */
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        componentGraphics.drawArc(canvas.virtualToActualX(x), canvas.virtualToActualY(y), canvas.virtualToActualX(width), canvas.virtualToActualY(height), startAngle, arcAngle);
    }

    /**
     * @see Graphics#fillArc
     */
    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        componentGraphics.fillArc(canvas.virtualToActualX(x), canvas.virtualToActualY(y), canvas.virtualToActualX(width), canvas.virtualToActualY(height), startAngle, arcAngle);
    }

    /**
     * @see Graphics#drawPolyline
     */
    public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
        componentGraphics.drawPolyline(canvas.virtualToActualX(xPoints), canvas.virtualToActualY(yPoints), nPoints);
    }

    /**
     * @see Graphics#drawPolygon
     */
    public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        componentGraphics.drawPolygon(canvas.virtualToActualX(xPoints), canvas.virtualToActualY(yPoints), nPoints);
    }

    /**
     * @see Graphics#fillPolygon
     */
    public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        componentGraphics.fillPolygon(canvas.virtualToActualX(xPoints), canvas.virtualToActualY(yPoints), nPoints);
    }

    /**
     * All String related drawing currently does not support scaling
     * @see Graphics#drawString
     */
    public void drawString(String str, int x, int y) {
        componentGraphics.drawString(str, canvas.virtualToActualX(x), canvas.virtualToActualY(y));
    }

    /**
     * All String related drawing currently does not support scaling
     * @see Graphics#drawString
     */
    public void drawString(AttributedCharacterIterator iterator, int x, int y) {
        componentGraphics.drawString(iterator, canvas.virtualToActualX(x), canvas.virtualToActualY(y));
    }

    /**
     * @see Graphics#drawImage
     */
    public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
        return componentGraphics.drawImage(img, canvas.virtualToActualX(x), canvas.virtualToActualY(y), observer);
    }

    /**
     * @see Graphics#drawImage
     */
    public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
        return componentGraphics.drawImage(img, canvas.virtualToActualX(x), canvas.virtualToActualY(y), canvas.virtualToActualX(width), canvas.virtualToActualY(height), observer);
    }

    /**
     * @see Graphics#drawImage
     */
    public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
        return componentGraphics.drawImage(img, canvas.virtualToActualX(x), canvas.virtualToActualY(y), bgcolor, observer);
    }

    /**
     * @see Graphics#drawImage
     */
    public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
        return componentGraphics.drawImage(img, canvas.virtualToActualX(x), canvas.virtualToActualY(y), canvas.virtualToActualX(width), canvas.virtualToActualY(height), bgcolor, observer);
    }

    /**
     * @see Graphics#drawImage
     */
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
                             ImageObserver observer) {
        return componentGraphics.drawImage(img, canvas.virtualToActualX(dx1), canvas.virtualToActualY(dy1), canvas.virtualToActualX(dx2), canvas.virtualToActualY(dy2), sx1, sy1, sx2, sy2, observer);
    }

    /**
     * @see Graphics#drawImage
     */
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
                             Color bgcolor, ImageObserver observer) {
        return componentGraphics.drawImage(img, canvas.virtualToActualX(dx1), canvas.virtualToActualY(dy1), canvas.virtualToActualX(dx2), canvas.virtualToActualY(dy2), sx1, sy1, sx2, sy2, bgcolor, observer);
    }

    /**
     * @see Graphics#dispose
     */
    public void dispose() {
        componentGraphics.dispose();
    }
}
