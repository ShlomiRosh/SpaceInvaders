/* name: shalomi rosh
   id: 308154418
   oop
*/
package game;


import asseven.Barricade;
import asseven.Bullet;
import asseven.Enemy;
import asseven.KillPlayer;
import assix.KeyPressStoppableAnimation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import sprite.Callable;
import sprite.LevelInformation;
import sprite.Sprite;
import sprite.SpriteCollection;
import asseven.Paddle;
import sprite.ScoreTrackingListener;
import sprite.ScoreIndicator;
import sprite.LevelIndicator;
import sprite.LivesIndicator;
import asseven.UfoRemover;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * This class holds all the sprites and is in charge of animation.
 */
public class GameLevel implements Animation {
    private AnimationRunner runner;
    private boolean running;
    private SpriteCollection sprites = new SpriteCollection();
    private GameEnvironment playerEnvironment = new GameEnvironment();
    private GameEnvironment enemyEnvironment = new GameEnvironment();
    private KeyboardSensor keyboard;
    private Counter aliensToRemove = new Counter();
    private Counter playerScore;
    private Counter livesLeft;
    private LevelInformation levelInfo;
    private long lastShootTime = -1;
    private Enemy enemy = null;
    private KillPlayer killer;
    private List<Bullet> bulletsOnScreen = new LinkedList<Bullet>();

    /**
     * Creates the game level.
     * @param levelInfo current level information
     * @param keyboard keyboard sensor of the game
     * @param runner the game's animation runner
     * @param score user's current score
     * @param livesLeft player's lives left
     * @throws IOException problem reading file
     */
    public GameLevel(LevelInformation levelInfo, KeyboardSensor keyboard,
                     AnimationRunner runner, Counter score, Counter livesLeft)
            throws IOException {
        // Game properties
        this.runner = runner;
        this.keyboard = keyboard;
        this.playerScore = score;
        this.livesLeft = livesLeft;

        // Current level properties
        this.levelInfo = levelInfo;
        aliensToRemove.increase(levelInfo.numberOfAliensToRemove());
        killer = new KillPlayer(this);

    }

    /**
     * Adds a collidable to collection.
     * @param c new collidable
     */
    public void addPlayerCollidable(Callable c) {
        playerEnvironment.addCollidable(c);
    }

    /**
     * Adds a collidable to collection.
     * @param c new collidable
     */
    public void addEnemyCollidable(Callable c) {
        enemyEnvironment.addCollidable(c);
    }

    /**
     * Initializes game level. Creates the background, status bar, the ship and
     * the enemy.
     * @throws IOException problem reading file
     */
    public void initialize() throws IOException {
        levelInfo.getBackground().addToGame(this); // add the background
        LevelIndicator levelIndicator = new LevelIndicator(this.levelInfo.levelName());
        levelIndicator.addToGame(this);
        ScoreIndicator scoreIndicator = new ScoreIndicator(this.playerScore);
        scoreIndicator.addToGame(this);
        LivesIndicator livesIndicator = new LivesIndicator(this.livesLeft);
        livesIndicator.addToGame(this);
        enemy = new Enemy(levelInfo.aliens(), levelInfo.aliensSpeed(), this);
        enemy.addToGame(this);
        createShip();
        createBarricades();
    }

    /**
     * Creates the barricades below the player.
     */
    private void createBarricades() {
        int barrNum = 3;
        int gameWidth = 800;
        int margin = (gameWidth - Barricade.WIDTH * barrNum) / (barrNum + 1);

        // Create colors for the barricades
        ArrayList<Color> colors = new ArrayList<Color>();
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);

        int barricadeStart = margin;
        for (int i = 0; i < barrNum; i++) {
            new Barricade(barricadeStart, colors.get(i)).addToGame(this);
            barricadeStart += Barricade.WIDTH + margin;
        }
    }

    /**
     * Creates and returns a score tracking listener.
     * @return score tracking listener.
     */
    public ScoreTrackingListener getScoreListener() {
        return new ScoreTrackingListener(playerScore);
    }

    /**
     * Creates and returns an alien remover listener.
     * @return alien remover listener.
     */
    public UfoRemover getAlienRemover() {
        return new UfoRemover(this, aliensToRemove, enemy);
    }

    /**
     * Adds a sprite to collection.
     * @param s sprite
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    /**
     * Loads a level.
     */
    public void playOneTurn() {
        Paddle.getInstance().reborn();
        enemy.reset(levelInfo.aliensStartX(), levelInfo.aliensStartY());
        clearBulletsFromScreen();
        // Count down
        runner.run(new CountdownAnimation(2, 3, sprites));
        // Run the game
        running = true;
        runner.run(this); // run the turn
    }

    /**
     * Creates a ship and adds it to the game.
     */
    private void createShip() {
        Paddle ship = Paddle.getInstance(keyboard, levelInfo.shipSpeed(),
                levelInfo.shipWidth());
        ship.addToGame(this);
        ship.addHitListener(killer);
    }

    /**
     * Plays one game frame.
     * @param d  the game surface
     * @param dt seconds passed since last move
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        sprites.drawAllOn(d);
        sprites.notifyAllTimePassed(dt);

        // Check if we need to stop the game
        if (!areAliensLeft()) {
            running = false;
        } else if (!Paddle.getInstance().isAlive()) {
            livesLeft.decrease(1);
            running = false;
        }

        // Run pause screen animation if needed
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(keyboard,
                    KeyboardSensor.SPACE_KEY, new PauseScreen()));

            // Check if the user shot
        } else if (canShoot() && keyboard.isPressed("space")) {
            Paddle.getInstance().shoot(this);
            lastShootTime = System.currentTimeMillis();
        }
    }

    /**
     * Checks if enough time has passed and the user can fire.
     * @return can fire - true. False otherwise
     */
    private boolean canShoot() {
        return (lastShootTime == -1)
                || (System.currentTimeMillis() >= lastShootTime + 350);
    }

    /**
     * Returnes whether the game should stop.
     * @return check result
     */
    @Override
    public boolean shouldStop() {
        return !running;
    }

    /**
     * Removes the collidable from the collidables collection.
     * @param c the collidable to remove
     */
    public void removePlayerCollidable(Callable c) {
        playerEnvironment.removeColliadable(c);
    }

    /**
     * Removes the collidable from the collidables collection.
     * @param c the collidable to remove
     */
    public void removeEnemyCollidable(Callable c) {
        enemyEnvironment.removeColliadable(c);
    }

    /**
     * Removes the sprite from the sprites collection.
     * @param s the sprite to remove
     */
    public void removeSprite(Sprite s) {
        sprites.removeSprite(s);
    }

    /**
     * Returns whether there are more aliens.
     * @return true or false
     */
    public boolean areAliensLeft() {
        return aliensToRemove.getValue() > 0;
    }

    /**
     * Gets the game playerEnvironment.
     * @return the game playerEnvironment
     */
    public GameEnvironment getPlayerEnvironment() {
        return playerEnvironment;
    }

    /**
     * Gets the game enemy environment.
     * @return the game enemy environment
     */
    public GameEnvironment getEnemyEnvironment() {
        return enemyEnvironment;
    }

    /**
     * Adds a bullet to the collection.
     * @param newBullet new created bullet
     */
    public void addBullet(Bullet newBullet) {
        bulletsOnScreen.add(newBullet);
    }

    /**
     * Clears all the bullets from the screen.
     */
    private void clearBulletsFromScreen() {
        for (Bullet bullet : bulletsOnScreen) {
            bullet.removeFromGame(this);
        }
    }
}
