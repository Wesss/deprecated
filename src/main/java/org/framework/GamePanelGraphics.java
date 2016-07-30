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

    Graphics componentGraphics;

    public GamePanelGraphics(Graphics g) {
        componentGraphics = g;
    }

    /**
     *
     * @param x
     * @param y
     *
     * @see Graphics#translate
     */
    public void translate(int x, int y) {
        componentGraphics.translate(x, y);
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
     * @see Graphics#getFont
     */
    public Font getFont() {
        return componentGraphics.getFont();
    }

    /**
     * @see Graphics#setFont
     */
    public void setFont(Font font) {
        componentGraphics.setFont(font);
    }

    /**
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
        componentGraphics.clipRect(x,y,width,height);
    }

    /**
     * @see Graphics#setClip
     */
    public void setClip(int x, int y, int width, int height) {
        componentGraphics.setClip(x,y,width, height);
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
        componentGraphics.copyArea( x, y, width, height, dx, dy);
    }

    /**
     * @see Graphics#drawLine
     */
    public void drawLine(int x1, int y1, int x2, int y2) {
        componentGraphics.drawLine(x1,y1,x2,y2);
    }

    /**
     * @see Graphics#fillRect
     */
    public void fillRect(int x, int y, int width, int height) {
        componentGraphics.fillRect(x,y,width,height);
    }

    /**
     * @see Graphics#clearRect
     */
    public void clearRect(int x, int y, int width, int height) {
        componentGraphics.clearRect(x, y,width,height);
    }

    /**
     * @see Graphics#drawRoundRect
     */
    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        componentGraphics.drawRoundRect(x, y,width,height,arcWidth,arcHeight);
    }

    /**
     * @see Graphics#fillRoundRect
     */
    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        componentGraphics.fillRoundRect(x,  y, width, height, arcWidth, arcHeight);
    }

    /**
     * @see Graphics#drawOval
     */
    public void drawOval(int x, int y, int width, int height) {
        componentGraphics.drawOval(x,  y, width, height);
    }

    /**
     * @see Graphics#fillOval
     */
    public void fillOval(int x, int y, int width, int height) {
        componentGraphics.fillOval(x, y, width, height);
    }

    /**
     * @see Graphics#drawArc
     */
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        componentGraphics.drawArc(x, y, width, height, startAngle, arcAngle);
    }

    /**
     * @see Graphics#fillArc
     */
    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        componentGraphics.fillArc(x, y, width, height, startAngle, arcAngle);
    }

    /**
     * @see Graphics#drawPolyline
     */
    public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
        componentGraphics.drawPolyline(xPoints, yPoints, nPoints);
    }

    /**
     * @see Graphics#drawPolygon
     */
    public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        componentGraphics.drawPolygon(xPoints, yPoints, nPoints);
    }

    /**
     * @see Graphics#fillPolygon
     */
    public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        componentGraphics.fillPolygon(xPoints, yPoints, nPoints);
    }

    /**
     * @see Graphics#drawString
     */
    public void drawString(String str, int x, int y) {
        componentGraphics.drawString(str, x, y);
    }

    /**
     * @see Graphics#drawString
     */
    public void drawString(AttributedCharacterIterator iterator, int x, int y) {
        componentGraphics.drawString(iterator, x, y);
    }

    /**
     * @see Graphics#drawImage
     */
    public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
        return componentGraphics.drawImage(img, x, y, observer);
    }

    /**
     * @see Graphics#drawImage
     */
    public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
        return componentGraphics.drawImage(img, x, y, width, height, observer);
    }

    /**
     * @see Graphics#drawImage
     */
    public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
        return componentGraphics.drawImage(img, x, y, bgcolor, observer);
    }

    /**
     * @see Graphics#drawImage
     */
    public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
        return componentGraphics.drawImage(img, x, y, width, height, bgcolor, observer);
    }

    /**
     * @see Graphics#drawImage
     */
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
                             ImageObserver observer) {
        return componentGraphics.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer);
    }

    /**
     * @see Graphics#drawImage
     */
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2,
                             Color bgcolor, ImageObserver observer) {
        return componentGraphics.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, bgcolor, observer);
    }

    /**
     * @see Graphics#dispose
     */
    public void dispose() {
        componentGraphics.dispose();
    }
}
