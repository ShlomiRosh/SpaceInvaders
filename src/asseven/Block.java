package asseven;


import biuoop.DrawSurface;
import game.GameLevel;

import java.awt.Color;

/**
 * This class represents a block - rectangle that a ball may collide.
 */
public class Block extends Hittable {
    private static final int SIZE = 5;
    private int x, y;
    private Color color;
    /**
     * Creates a default alien on specified position and size.
     * @param x x position
     * @param y y position
     * @param color color of the block
     */
    public Block(int x, int y, Color color) {
        super(x, y, SIZE, SIZE);
        this.x = x;
        this.y = y;
        this.color = color;
    }

    /**
     * Gets the size of block.
     * @return block's size
     */
    public static int getSize() {
        return SIZE;
    }

    @Override
    public void timePassed(double dt) {

    }

    /**
     * Adds the block to the game.
     * @param game game
     */
    @Override
    public void addToGame(GameLevel game) {
        game.addPlayerCollidable(this);
        game.addEnemyCollidable(this);
    }

    /**
     * Removes the block from the gameLevel.
     * @param game the gameLevel object to remove from
     */
    public void removeFromGame(GameLevel game) {
        game.removePlayerCollidable(this);
        game.removeEnemyCollidable(this);
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(color);
        d.fillRectangle(x, y, getSize(), getSize());
    }
}

