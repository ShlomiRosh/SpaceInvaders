package asseven;

import biuoop.DrawSurface;
import geometry.Point;
import geometry.Rectangle;
import sprite.Callable;
import sprite.Sprite;

import java.util.ArrayList;
import java.util.List;

/**
 * Any hittable object that can be destroyed by a hit.
 */
abstract class Hittable extends Rectangle implements Callable, HitNotifier, Sprite {
    private int hitPoints = 1;
    private List<HitListener> hitListeners = new ArrayList<HitListener>();

    /**
     * Creates a default alien on specified position and size.
     * @param upperLeft position point
     * @param width width of the block
     * @param height height of the block
     */
    public Hittable(Point upperLeft, double width, double height) {
        super(upperLeft, width, height);
    }

    /**
     * Creates a default alien on specified position and size.
     * @param x x position
     * @param y y position
     * @param width width of the block
     * @param height height of the block
     */
    public Hittable(int x, int y, double width, double height) {
        super(new Point(x, y), width, height);
    }

    /**
     * Return the "collision shape" of the object.
     * @return collision shape
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this;
    }

    /**
     * Reduces the score of the block.
     */
    private void reduceScore() {
        if (hitPoints >= 0) {
            hitPoints--;
        }
    }

    /**
     * Adds listener to hit events.
     * @param hl the listener to add
     */
    @Override
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
    }

    /**
     * Removes the listener from list.
     * @param hl the listener to be removed
     */
    @Override
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
    }

    /**
     * Handles hit event for this alien.
     * @param hitter the bullet that hit
     */
    public void notifyHit(Bullet hitter) {
        reduceScore();

        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners =
                new ArrayList<HitListener>(hitListeners);

        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    @Override
    public void drawOn(DrawSurface d) {

    }

}

