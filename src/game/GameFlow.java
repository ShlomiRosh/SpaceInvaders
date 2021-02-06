package game;

import asseven.TheLevel;
import assix.HighScoresAnimation;
import assix.HighScoresTable;
import assix.ScoreInfo;
import biuoop.DialogManager;
import biuoop.KeyboardSensor;

import assix.KeyPressStoppableAnimation;

import java.io.File;
import java.io.IOException;
/**
 * Class in charge of creating the game levels and running them one after
 * another.
 */
public class GameFlow {
    private KeyboardSensor keyboardSensor;
    private AnimationRunner animationRunner;
    private HighScoresTable highScoresTable;
    private Counter playerScore = new Counter();
    private Counter livesLeft = new Counter();
    private int live;
    /**
     * Creates the game flow.
     * @param runner the animation runner
     * @param keySensor the keyboard sensor
     * @param lives amount of lives user will have
     * @param table the score table
     */
    public GameFlow(AnimationRunner runner, KeyboardSensor keySensor, int lives, HighScoresTable table) {
        this.animationRunner = runner;
        this.keyboardSensor = keySensor;
        this.livesLeft.increase(lives);
        this.live = lives;
        this.highScoresTable = table;
    }

    /**
     * Run the game.
     * @throws IOException problem reading file
     */
    public void runLevels() throws IOException {
        boolean playerAlive = true;
        int levelNum = 1;

        // Run levels as they are in list
        while (playerAlive) {
            // Create the current level
            GameLevel level =
                    new GameLevel(new TheLevel(levelNum), keyboardSensor, animationRunner, playerScore, livesLeft);
            level.initialize();

            // Play current level while there are more aliens and lives
            while (level.areAliensLeft() && livesLeft.getValue() > 0) {
                level.playOneTurn();
            }
            // No more lives - stop the game
            if (livesLeft.getValue() == 0) {
                playerAlive = false;
            }
            levelNum++;
            // currLevel = new Battle(levelNum);
        }

        if (highScoresTable.getRank(this.playerScore.getValue()) <= highScoresTable.size()) {
            DialogManager dialog = animationRunner.getGui().getDialogManager();
            String name = dialog.showQuestionDialog("Name",
                    "What is your name?", "Anonymous");
            ScoreInfo currentInfo = new ScoreInfo(name,
                    this.playerScore.getValue());
            highScoresTable.add(currentInfo);

            File highScoresTableFile = new File("highscores.txt");
            try {
                highScoresTable.save(highScoresTableFile);
            } catch (Exception e) {
                System.out.println("Problem saving to high scores file!");
            }
        }

        this.animationRunner.run(new KeyPressStoppableAnimation(keyboardSensor,
                KeyboardSensor.SPACE_KEY, new EndScreen(playerScore, livesLeft)));

        if (livesLeft.getValue() == 0) {
            this.livesLeft.increase(live);
        }
        this.playerScore = new Counter();

        this.animationRunner.run(new KeyPressStoppableAnimation(keyboardSensor,
                KeyboardSensor.SPACE_KEY, new HighScoresAnimation(highScoresTable)));
        // Game ended
    }
}