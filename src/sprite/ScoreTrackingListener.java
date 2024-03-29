package sprite;
import asseven.Bullet;
import asseven.HitListener;
import game.Counter;


/**
 * Counts the player's score.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * Create a score tracking listener.
     *
     * @param scoreCounter reference to counter of player's score
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        currentScore = scoreCounter;
    }

    /**
     * Adds the proper amount of points to player when ball hits a block.
     *
     * @param beingHit the object that was hit
     * @param hitter   the ball that hit the object
     */
    public void hitEvent(Callable beingHit, Bullet hitter) {
        currentScore.increase(100);
    }
}
