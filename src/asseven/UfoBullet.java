package asseven;


import game.GameLevel;
import geometry.Point;
import geometry.Velocity;
import java.awt.Color;

/**
 * A bullet shot from an alien.
 */
public class UfoBullet extends Bullet {

    /**
     * Constructs an alien's bullet.
     * @param x x coord
     * @param y y coord
     */
    public UfoBullet(double x, double y) {
        super(new Point(x, y), 4, Color.red, Color.BLUE, Velocity.fromAngleAndSpeed(180, 400));
    }
    /**
     * Adds the bullet to the game.
     * @param g game
     */
    @Override
    public void addToGame(GameLevel g) {
        setEnvironment(g.getEnemyEnvironment());
        super.addToGame(g);
    }
}
