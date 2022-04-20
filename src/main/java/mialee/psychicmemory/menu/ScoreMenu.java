package mialee.psychicmemory.menu;

import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.data.DataManager;

import java.awt.*;
import java.util.ArrayList;

public class ScoreMenu {
    private final ArrayList<Text> texts = new ArrayList<>();
    private final int score;
    private final StringBuilder name = new StringBuilder();

    public ScoreMenu(boolean win, int score, boolean highScore) {
        this.score = score;
        texts.add(new Text(win ? "Victory" : "Game Over", 72f, 480, 200, Alignment.CENTER));
        if (highScore) texts.add(new Text("New High Score!", 24f, 480, 230, Alignment.CENTER));
        texts.add(new Text("Enter name:", 36f, 480, 280, Alignment.CENTER));
    }

    public void render(Graphics graphics) {
        for (Text text : texts) {
            text.render(graphics);
        }
        new Text(name + ((System.nanoTime() / 400000000 % 2 == 0) ? "_" : "  "), 36f, 480, 360, Alignment.CENTER).render(graphics);
    }

    public void addKey(char keyChar) {
        name.append(keyChar);
    }

    public void removeKey() {
        name.deleteCharAt(name.length() - 1);
    }

    public void finishName() {
        DataManager.writeScore(name.toString(), score);
        PsychicMemory.restart();
    }
}
