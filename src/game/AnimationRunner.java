package game;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;
/**
 * Created by Shlomi Rosh.
 */
public class AnimationRunner {
    private GUI gui;
    private int framesPerSecond;
    private Sleeper sleeper;
    private double moveTimeSec;

    /**
     * Run the animation on the GUI.
     * @param gui the gui to be animated.
     */
    public AnimationRunner(GUI gui) {
        this.framesPerSecond = 60;
        this.gui = gui;
        this.sleeper = new Sleeper();
        moveTimeSec = 1.0 / framesPerSecond;

    }
    /**
     * set method.
     * setting the FramesPerSecond
     * @param framesPerSecon - the given value for FramesPerSecond
     */
    public void setFramesPerSecond(int framesPerSecon) {
        this.framesPerSecond = framesPerSecon;
    }
    /**
     * Run the Animation.
     * @param animation the animation to be run.
     */
    public void run(Animation animation) {
        // Run the animation until it decides to stop
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing

            // Show one frame
            DrawSurface d = gui.getDrawSurface();
            animation.doOneFrame(d, moveTimeSec);
            gui.show(d);

            // Wait needed time
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep
                    = (long) (moveTimeSec * 1000) - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }
    }
    /**
     * access method.
     * @return - the gui that being drawing on.
     */
    public GUI getGui() {
        return gui;
    }

}