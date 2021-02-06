import assix.HighScoreTask;
import assix.HighScoresTable;
import assix.Menu;
import assix.MenuAnimation;
import assix.Task;
import biuoop.GUI;
import game.AnimationRunner;
import game.GameFlow;
import game.GameLoadingAnimation;

import java.io.File;
import java.io.IOException;


/**
 * @author shlomi rosh.
 */
public class Ass7Game {

    private static int width = 800;
    private static int height = 600;

    /**
     * the maim of ass5.
     * @param args Receives arguments from the user.
     */
    public static void main(String[] args) {
        /*Creates the new .txt File to hold the high Scores.*/
        String scoresFile = "highscores.txt";
        final File highScoresTableFile = new File(scoresFile);
        HighScoresTable temp = null;
        try {
            if (!highScoresTableFile.exists()) {
                highScoresTableFile.createNewFile();
                temp = new HighScoresTable(3);
                temp.save(highScoresTableFile);
            } else {
                temp = HighScoresTable.loadFromFile(highScoresTableFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        final HighScoresTable table = temp;

        GUI gui = new GUI("arkoind", width, height);
        AnimationRunner animationRunner = new AnimationRunner(gui);
        GameFlow gameFlow = new GameFlow(animationRunner, gui.getKeyboardSensor(), 3, table);
        animationRunner.run(new GameLoadingAnimation());
        Menu<Task<Void>> menu = new MenuAnimation<Task<Void>>("Menu", gui.getKeyboardSensor(), animationRunner);
        Task<Void> quit = new Task<Void>() {
            @Override
            public Void run() {
                System.exit(1);
                return null;
            }
        };

        GameFlow finalGameFlow = gameFlow;
        Task<Void> gameTask = new Task<Void>() {
            @Override
            public Void run() {
                try {
                    finalGameFlow.runLevels();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        menu.addSelection("s", "Start Game", gameTask);
        menu.addSelection("h", "high scores", new HighScoreTask(animationRunner, gui, highScoresTableFile));
        menu.addSelection("q", "quit", quit);

        while (true) {
            animationRunner.run(menu);
            // wait for user selection
            Task<Void> task = menu.getStatus();
            task.run();
        }
    }
    //public void :)() {
    //    String str = "Good Grades"
    //}
   //public void :( () {
   //     String str = "Bad Grades"
    //}
   //boolean ThirdYear = true;
    //ThirdYear ? :) : :(;
}