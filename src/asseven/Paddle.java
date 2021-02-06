package asseven;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.GameLevel;
import geometry.Point;
import sprite.Sprite;

import java.awt.Color;


/**
 * sprites.Paddle - the player in the game.
 */
public final class Paddle extends Hittable implements Sprite {
    // Properties
    private static Paddle instance;
    private biuoop.KeyboardSensor keyboard;
    private double step;
    private boolean isAlive = true;

    /**
     * Create a new paddle with the keyboard sensor.
     * @param keyboard keyboard sensor
     * @param step paddle's speed
     * @param width paddle's width
     */
    private Paddle(KeyboardSensor keyboard, double step, double width) {
        super((int) (400 - width / 2), 575, width, 20);
        this.keyboard = keyboard;
        this.step = step;
    }

    /**
     * Returns a ship with new width and speed. If doesn't exist - create it.
     * @param keyboard keyboard sensor
     * @param speed ship's speed
     * @param width ship's width
     * @return the ship
     */
    public static Paddle getInstance(KeyboardSensor keyboard, double speed,
                                   double width) {
        if (instance == null) { // create if doesn't exist
            instance = new Paddle(keyboard, speed, width);
        } else {
            instance.setStep(speed);
            instance.setWidth(width);
            instance.moveToDefault();
        }
        return instance;
    }

    /**
     * Returns the ship instance. If doesn't exist return null.
     * @return the ship
     */
    public static Paddle getInstance() {
        return instance;
    }

    /**
     * Sets paddle speed.
     * @param newStep new speed
     */
    private void setStep(double newStep) {
        step = newStep;
    }

    /**
     * Moves the paddle to default place.
     */
    public void moveToDefault() {
        getUpperLeft().setX(400 - getWidth() / 2);
    }

    /**
     * Draws the paddle on surface d.
     * @param d the draw surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        // Draw a border
        Point pos = getUpperLeft();
        d.setColor(Color.RED);
        d.drawRectangle((int) pos.getX(), (int) pos.getY(), (int) getWidth(), (int) getHeight());
        d.setColor(Color.YELLOW);
        d.fillRectangle((int) pos.getX(), (int) pos.getY(), (int) getWidth(), (int) getHeight());
    }

    /**
     * Notify the sprite that time has passed.
     * @param dt time passed since last invocation
     */
    @Override
    public void timePassed(double dt) {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft(dt);
        } else if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight(dt);
        }
    }

    /**
     * Adds the paddle to the game.
     * @param g game
     */
    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addEnemyCollidable(this);
    }

    /**
     * Moves right.
     * @param moveTimeSec seconds for move
     */
    public void moveRight(double moveTimeSec) {
        int speed = (int) (step * moveTimeSec);
        Point pos = getUpperLeft();
        // Make sure not to pass the screen borders
        if ((pos.getX() + getWidth() + speed) > 795) {
            pos.setX(795 - getWidth());
        } else {
            pos.moveX(speed);
        }
    }

    /**
     * Moves left.
     * @param moveTimeSec seconds for move
     */
    public void moveLeft(double moveTimeSec) {
        int speed = (int) (step * moveTimeSec);
        Point pos = getUpperLeft();
        // Make sure not to pass the screen borders
        if ((pos.getX() - speed) < 5) {
            pos.setX(5);
        } else {
            pos.moveX(-speed);
        }
    }

    /**
     * Shoots a bullet.
     * @param game the game level to fire to
     */
    public void shoot(GameLevel game) {
        Point pos = getUpperLeft();
        Bullet bullet = new PlayerBullet(pos.getX() + (getWidth() / 2), pos.getY());
        bullet.addToGame(game);   // Add bullet to game
    }

    /**
     * Kills the ship.
     */
    public void kill() {
        isAlive = false;
    }

    /**
     * Checks if ship is alive.
     * @return true if alive
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Sets the ship as alive.
     */
    public void reborn() {
        isAlive = true;
        moveToDefault();
    }
}
