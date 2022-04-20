package mialee.psychicmemory.menu;

import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.data.DataManager;
import mialee.psychicmemory.data.GameRecord;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Menu {
    public final ArrayList<Page> pages = new ArrayList<>();
    public int selectedPage = 0;
    public boolean rebinding = false;
    public int rebindingTarget = 0;

    public Menu() {
        Page pageMain = new Page();
        pageMain.addText(new Text("Psychic Memory", 72f, 480, 200, Alignment.CENTER));
        pageMain.addButton(new Button("Start", PsychicMemory::start, 480, 330, 0));
        pageMain.addButton(new Button("Scores", () -> selectedPage = 2, 480, 400, 0));
        pageMain.addButton(new Button("Options", () -> selectedPage = 1, 480, 470, 0));
        pageMain.addButton(new Button("Quit", () -> System.exit(1), 480, 540, 0));
        pages.add(pageMain);

        Page pageOptions = new Page();
        pageOptions.addText(new Text("Rebind Keys", 72f, 480, 100, Alignment.CENTER));
        pageOptions.addButton(new KeyButton(0, "Left: ", () -> startRebind(0), 480, 250, 0));
        pageOptions.addButton(new KeyButton(1, "Up: ", () -> startRebind(1), 480, 300, 0));
        pageOptions.addButton(new KeyButton(2, "Right: ", () -> startRebind(2), 480, 350, 0));
        pageOptions.addButton(new KeyButton(3, "Down: ", () -> startRebind(3), 480, 400, 0));
        pageOptions.addButton(new KeyButton(4, "Fire: ", () -> startRebind(4), 480, 450, 0));
        pageOptions.addButton(new KeyButton(5, "Slow: ", () -> startRebind(5), 480, 500, 0));
        pageOptions.addButton(new KeyButton(6, "Pause: ", () -> startRebind(6), 480, 550, 0));
        pageOptions.addButton(new Button("Back", () -> selectedPage = 0, 480, 650, 0));
        pages.add(pageOptions);

        ArrayList<GameRecord> scores = DataManager.readScores();
        Collections.sort(scores);
        Collections.reverse(scores);

        Page pageScores = new Page();
        pageScores.addText(new Text("High Scores", 72f, 480, 100, Alignment.CENTER));
        pageScores.addText(new Text("Name", 32f, 230, 180, Alignment.LEFT));
        pageScores.addText(new Text("Score", 32, 730, 180, Alignment.RIGHT));
        for (int i = 0; i < 10; i++) {
            if (i < scores.size()) {
                pageScores.addText(new Text(scores.get(i).name(), 24f, 230, 225 + (i * 25), Alignment.LEFT));
                pageScores.addText(new Text(String.valueOf(scores.get(i).score()), 24f, 730, 225 + (i * 25), Alignment.RIGHT));
            } else {
                pageScores.addText(new Text("null", 24f, 230, 225 + (i * 25), Alignment.LEFT));
                pageScores.addText(new Text(String.valueOf(0), 24f, 730, 225 + (i * 25), Alignment.RIGHT));
            }
        }
        pageScores.addButton(new Button("Back", () -> selectedPage = 0, 480, 650, 0));
        pages.add(pageScores);
    }

    public void render(Graphics graphics) {
        pages.get(selectedPage).render(graphics, rebinding);
    }

    private void startRebind(int target) {
        rebinding = true;
        rebindingTarget = target;
    }
}
