package asseven;

import game.GameLevel;
import geometry.Point;

/**
 * Any object that can be hit by a player.
 */
public abstract class PlayerHittable extends Hittable {

    /**
     * Creates a player hittable object on specified position and size.
     * @param upperLeft position point
     * @param width width of the block
     * @param height height of the block
     */
    public PlayerHittable(Point upperLeft, double width, double height) {
        super(upperLeft, width, height);
    }

    /**
     * Adds the block to the game.
     * @param game game
     */
    @Override
    public void addToGame(GameLevel game) {
        game.addPlayerCollidable(this);
    }

    /**
     * Removes the block from the gameLevel.
     * @param game the gameLevel object to remove from
     */
    public void removeFromGame(GameLevel game) {
        game.removePlayerCollidable(this);
    }
}

