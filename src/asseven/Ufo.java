package asseven;
import game.GameLevel;
import geometry.Point;

/**
 * This class represents a block - rectangle that a ball may collide.
 */
public class Ufo extends PlayerHittable {
    /**
     * Creates a default alien on specified position and size.
     * @param upperLeft position point
     * @param width width of the block
     * @param height height of the block
     */
    public Ufo(Point upperLeft, double width, double height) {
        super(upperLeft, width, height);
    }

    /**
     * Makes the alien fire.
     * @param game game to fire in
     */
    public void shoot(GameLevel game) {
        Point pos = getUpperLeft();
        Bullet bullet = new UfoBullet(pos.getX() + (getWidth() / 2),
                pos.getY() + getHeight());
        bullet.addToGame(game);   // Add bullet to game
    }

    /**
     * Moves the alien one step right if step is positive. Otherwise - left.
     * @param step step to move right
     */
    public void moveRight(int step) {
        getUpperLeft().moveX(step); // move freely
    }

    /**
     * Moves the alien one step down. If step negative - move up.
     * @param step step to move down
     */
    public void moveDown(int step) {
        getUpperLeft().moveY(step);
    }

    /**
     * Moves the alien.
     * @param stepRight step to move right
     * @param stepDown step to move down
     */
    public void move(int stepRight, int stepDown) {
        moveRight(stepRight);
        moveDown(stepDown);
    }

    @Override
    public void timePassed(double dt) {

    }
}

