package asseven;

import game.GameLevel;
import sprite.Callable;

/**
 * Class in charge of killing the player when he was hit.
 */
public class KillPlayer implements HitListener {
    private GameLevel gameLevel;

    /**
     * Creates the killer.
     * @param gameLevel the current game level
     */
    public KillPlayer(GameLevel gameLevel) {
        this.gameLevel = gameLevel;
    }

    /**
     * Kills the player.
     * @param beingHit the object that was hit
     * @param hitter the bullet that hit the object
     */
    @Override
    public void hitEvent(Callable beingHit, Bullet hitter) {
        hitter.removeFromGame(gameLevel);
        Paddle.getInstance().kill();
    }
}