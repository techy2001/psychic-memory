package mialee.psychicmemory.menu;

import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.data.DataManager;

import java.awt.*;
import java.util.ArrayList;

/**
 * The score menu is the menu which is opened at the end of the game.
 * Allows you to input your name and will save your score if it was over 0.
 */
public class ScoreMenu {
    private final ArrayList<Text> texts = new ArrayList<>();
    private final int score;
    private final StringBuilder name = new StringBuilder();

    /**
     * Adds all the text to the menu, if the player got 0 score they cannot enter it into the scores file.
     * Also mentions if the score was a high score.
     * @param win If the game was won or lost.
     * @param score The score the player got.
     * @param highScore If the score was a highscore.
     */
    public ScoreMenu(boolean win, int score, boolean highScore) {
        this.score = score;
        texts.add(new Text(win ? "Victory" : "Game Over", 72f, 480, 200, Alignment.CENTER));
        texts.add(new Text("Score: " + score, 36f, 480, 280, Alignment.CENTER));
        if (highScore) texts.add(new Text("New High Score!", 24f, 480, 330, Alignment.CENTER));
        if (score > 0) {
            texts.add(new Text("Enter name:", 36f, 480, 480, Alignment.CENTER));
        } else {
            texts.add(new Text("Press Enter to continue.", 24f, 480, 480, Alignment.CENTER));
        }
    }

    /**
     * Does not draw the name input if the score was 0.
     * @param graphics Graphics to draw the text to.
     */
    public void render(Graphics graphics) {
        for (Text text : texts) {
            text.render(graphics);
        }
        if (score <= 0) return;
        new Text(name + (name.length() < 20 ? ((System.nanoTime() / 400000000 % 2 == 0) ? "_" : "  ") : ""), 36f, 480, 560, Alignment.CENTER).render(graphics);
    }

    /**
     * Adds a character to the name if the name prompt is active.
     * @param keyChar Character to add to the name.
     */
    public void addKey(char keyChar) {
        if (score <= 0) return;
        if (name.length() < 20) name.append(keyChar);
    }

    /**
     * Removes a character from the name if it has any.
     */
    public void removeKey() {
        if (name.length() > 0) name.deleteCharAt(name.length() - 1);
    }

    /**
     * Writes the game to the scores file and restarts the game.
     */
    public void finishName() {
        if (score <= 0) {
            PsychicMemory.restart();
            return;
        }
        if (!name.toString().equals("")) {
            DataManager.writeScore(name.toString(), score);
            PsychicMemory.restart();
        }
    }
}
