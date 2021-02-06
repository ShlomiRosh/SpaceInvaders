package asseven;


import game.GameLevel;
import geometry.Point;
import geometry.Velocity;

import java.awt.Color;
/**
 * Constructs a player's bullet.
 */
public class PlayerBullet extends Bullet {
    /**
     * Constructs a player's bullet.
     * @param x x coord
     * @param y y coord
     */
    public PlayerBullet(double x, double y) {
        super(new Point(x, y), 2, new Color(65, 255, 0), new Color(47, 185, 0),
                Velocity.fromAngleAndSpeed(0, 500));
    }

    /**
     * Adds the bullet to the game.
     * @param g game
     */
    @Override
    public void addToGame(GameLevel g) {
        setEnvironment(g.getPlayerEnvironment());
        super.addToGame(g);
    }
}

