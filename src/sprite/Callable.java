package sprite;

import game.GameLevel;
import geometry.Rectangle;

/**
 * This class will create the Collidable Object.
 * @author shlomi rosh.
 */
public interface Callable {
    /**
     * Adds the sprite to the game.
     *
     * @param g game
     */
    void addToGame(GameLevel g);

    /**
     * Returns the collision rectangle.
     *
     * @return the collision rectangle.
     */
    Rectangle getCollisionRectangle();
}