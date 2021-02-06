package asseven;

import sprite.Callable;

/**
 * The hit listener interface.
 */
public interface HitListener {
    /**
     * This method is called whenever the beingHit object is hit.
     * @param beingHit the object that was hit
     * @param hitter the bullet that hit the object
     */
    void hitEvent(Callable beingHit, Bullet hitter);
}