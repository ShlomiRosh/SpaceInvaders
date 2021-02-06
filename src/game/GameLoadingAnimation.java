package game;

import biuoop.DrawSurface;
import geometry.Point;
import geometry.Velocity;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;

/**
 * The tag game animation.
 * @author shlomi rosh.
 */
public class GameLoadingAnimation implements Animation {
    private double numOfSeconds;
    private double countDown;
    private boolean stop = false;
    private double initialTime = System.currentTimeMillis();
    private double x = 10, y = -300;
    private Point piss = new Point(x, y);
    private Velocity v = Velocity.fromAngleAndSpeed(180, 1000);
    /**
     * Constructor Method.
     */
    public GameLoadingAnimation() {
        this.numOfSeconds = 2;
        this.countDown = numOfSeconds;

    }
    /**
     * Does the animation at each frame.
     * @param d what to draw.
     * @param dt the frames per second.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        Image image = null;
        try {
            image = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("Background-2.png"));
            ImageIO.setUseCache(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        d.drawImage(0, 0, image);

        Image image2 = null;
        try {
            image2 = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("4.png"));
            ImageIO.setUseCache(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        d.drawImage(500, 50, image2);

        explosion();
        Image image3 = null;
        try {
            image3 = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("3.png"));
            ImageIO.setUseCache(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        d.drawImage((int) piss.getX(), (int) piss.getY(), image3);

        if (countDown < 1.5) {
            Image image1 = null;
            try {
                image1 = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream("1.png"));
                ImageIO.setUseCache(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
            d.drawImage(50, 450, image1);
        }

        double currentTime = System.currentTimeMillis();
        if ((currentTime - initialTime) > (numOfSeconds) * 800) {
            countDown -= 0.5;
            initialTime = System.currentTimeMillis();
        }
        if (countDown == 0) {
            stop = true;
        }
    }

    /**
     * Particles of the exploding block.
     */
    public void explosion() {
        piss = v.applyToPoint(piss, 90);
        y += 3;
        if (y > 300) {
            y = 300;
        }
        piss = new Point(x, y);
    }

    /**
     * Checks if the animation should stop or not.
     * @return true or false.
     */
    public boolean shouldStop() {
        return this.stop;
    }

}
