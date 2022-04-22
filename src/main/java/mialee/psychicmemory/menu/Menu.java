package mialee.psychicmemory.menu;

import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.data.DataManager;
import mialee.psychicmemory.data.GameRecord;
import mialee.psychicmemory.lang.TranslatableText;

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
        pageMain.addText(new Text(new TranslatableText("pm.game"), 72f, 480, 200, Alignment.CENTER));
        pageMain.addButton(new Button(new TranslatableText("pm.menu.start"), PsychicMemory::start, 480, 330, 0));
        pageMain.addButton(new Button(new TranslatableText("pm.menu.scores"), () -> selectedPage = 2, 480, 400, 0));
        pageMain.addButton(new Button(new TranslatableText("pm.menu.options"), () -> selectedPage = 1, 480, 470, 0));
        pageMain.addButton(new Button(new TranslatableText("pm.menu.quit"), () -> System.exit(1), 480, 540, 0));
        pages.add(pageMain);

        Page pageOptions = new Page();
        pageOptions.addText(new Text(new TranslatableText("pm.menu.settings.rebind"), 72f, 480, 100, Alignment.CENTER));
        pageOptions.addButton(new KeyButton(1, new TranslatableText("pm.menu.settings.up") + ": ", () -> startRebind(1), 480, 200, 0));
        pageOptions.addButton(new KeyButton(3, new TranslatableText("pm.menu.settings.down") + ": ", () -> startRebind(3), 480, 250, 0));
        pageOptions.addButton(new KeyButton(0, new TranslatableText("pm.menu.settings.left") + ": ", () -> startRebind(0), 480, 300, 0));
        pageOptions.addButton(new KeyButton(2, new TranslatableText("pm.menu.settings.right") + ": ", () -> startRebind(2), 480, 350, 0));
        pageOptions.addButton(new KeyButton(4, new TranslatableText("pm.menu.settings.fire") + ": ", () -> startRebind(4), 480, 400, 0));
        pageOptions.addButton(new KeyButton(5, new TranslatableText("pm.menu.settings.blank") + ": ", () -> startRebind(5), 480, 450, 0));
        pageOptions.addButton(new KeyButton(6, new TranslatableText("pm.menu.settings.slow") + ": ", () -> startRebind(6), 480, 500, 0));
        pageOptions.addButton(new KeyButton(7, new TranslatableText("pm.menu.settings.pause") + ": ", () -> startRebind(7), 480, 550, 0));
        pageOptions.addButton(new Button(new TranslatableText("pm.menu.back"), () -> selectedPage = 0, 480, 650, 0));
        pages.add(pageOptions);

        ArrayList<GameRecord> scores = DataManager.readScores();
        Collections.sort(scores);
        Collections.reverse(scores);

        Page pageScores = new Page();
        pageScores.addText(new Text(new TranslatableText("pm.menu.scores.high_scores"), 72f, 480, 100, Alignment.CENTER));
        pageScores.addText(new Text(new TranslatableText("pm.menu.scores.name"), 32f, 230, 180, Alignment.LEFT));
        pageScores.addText(new Text(new TranslatableText("pm.menu.scores.score"), 32, 730, 180, Alignment.RIGHT));
        for (int i = 0; i < 10; i++) {
            if (i < scores.size()) {
                pageScores.addText(new Text(scores.get(i).name(), 24f, 230, 225 + (i * 25), Alignment.LEFT));
                pageScores.addText(new Text(String.valueOf(scores.get(i).score()), 24f, 730, 225 + (i * 25), Alignment.RIGHT));
            } else {
                pageScores.addText(new Text("Techy2001", 24f, 230, 225 + (i * 25), Alignment.LEFT));
                pageScores.addText(new Text(String.valueOf(10000 - (1000 * i)), 24f, 730, 225 + (i * 25), Alignment.RIGHT));
            }
        }
        pageScores.addButton(new Button(new TranslatableText("pm.menu.back"), () -> selectedPage = 0, 480, 650, 0));
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
