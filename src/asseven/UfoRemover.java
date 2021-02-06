package asseven;

import game.Counter;
import game.GameLevel;
import sprite.Callable;

/**
 * Class in charge of removing aliens from the gameLevel, and keeping count of
 * the number of aliens that were removed.
 */
public class UfoRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter aliensCount;
    private Enemy formation;

    /**
     * Constructs the block remover.
     * @param gameLevelRef reference to a gameLevel object
     * @param aliensCountRef counter of removed aliens
     * @param formation the formation to which the alien belongs
     */
    public UfoRemover(GameLevel gameLevelRef, Counter aliensCountRef, Enemy formation) {
        this.gameLevel = gameLevelRef;
        this.aliensCount = aliensCountRef;
        this.formation = formation;
    }

    /**
     * If the hit block reached 0 hit points - remove it.
     * @param beingHit the object that was hit
     * @param hitter the bullet that hit the object
     */
    @Override
    public void hitEvent(Callable beingHit, Bullet hitter) {
        // Remove the bullet
        hitter.removeFromGame(gameLevel);
        // Remove the alien
        Ufo alienHit = (Ufo) beingHit;
        alienHit.removeHitListener(this);
        formation.removeAlien(alienHit);
        alienHit.removeFromGame(gameLevel);
        aliensCount.decrease(1);
    }
}
