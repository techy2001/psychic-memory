package mialee.psychicmemory.menu;

import mialee.psychicmemory.GameState;
import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.data.DataManager;
import mialee.psychicmemory.data.PMSettings;
import mialee.psychicmemory.math.MathHelper;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Menu implements KeyListener {
    private final ArrayList<Page> pages = new ArrayList<>();
    private int selectedPage = 0;
    private boolean rebinding = false;
    private int rebindingTarget = 0;

    public Menu() {
        Page pageMain = new Page();
        pageMain.addButton(new Button("Start", PsychicMemory::start, 480, 400, 0));
        pageMain.addButton(new Button("Options", () -> selectedPage = 1, 480, 470, 0));
        pageMain.addButton(new Button("Quit", () -> System.exit(1), 480, 540, 0));
        pages.add(pageMain);

        Page pageOptions = new Page();
        pageOptions.addText(new Text("Rebind Keys", 480, 200));
        pageOptions.addButton(new KeyButton(0, "Left: ", () -> startRebind(0), 480, 300, 0));
        pageOptions.addButton(new KeyButton(1, "Up: ", () -> startRebind(1), 480, 350, 0));
        pageOptions.addButton(new KeyButton(2, "Right: ", () -> startRebind(2), 480, 400, 0));
        pageOptions.addButton(new KeyButton(3, "Down: ", () -> startRebind(3), 480, 450, 0));
        pageOptions.addButton(new KeyButton(4, "Fire: ", () -> startRebind(4), 480, 500, 0));
        pageOptions.addButton(new KeyButton(5, "Slow: ", () -> startRebind(5), 480, 550, 0));
        pageOptions.addButton(new Button("Back", () -> selectedPage = 0, 480, 650, 0));
        pages.add(pageOptions);
    }

    public void render(Graphics graphics) {
        pages.get(selectedPage).render(graphics, rebinding);
    }

    private void startRebind(int target) {
        rebinding = true;
        rebindingTarget = target;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (PsychicMemory.gameState == GameState.MENU) {
            if (!rebinding) {
                int keyCode = e.getKeyCode();
                Page page = pages.get(selectedPage);
                if (keyCode == PsychicMemory.SETTING_VALUES.UP_KEY) {
                    page.changeSelected(-1);
                } else if (keyCode == PsychicMemory.SETTING_VALUES.DOWN_KEY) {
                    page.changeSelected(1);
                } else if (keyCode == PsychicMemory.SETTING_VALUES.FIRE_KEY) {
                    page.pressButton();
                } else if (keyCode == PsychicMemory.SETTING_VALUES.SLOW_KEY) {
                    page.selectLast();
                }
            } else {
                boolean available = true;
                for (int i = 0; i < 6; i++) {
                    if (e.getKeyCode() == PsychicMemory.SETTING_VALUES.getKeyByID(i) && !(rebindingTarget == i)) {
                        available = false;
                        break;
                    }
                }
                if (available) {
                    PsychicMemory.SETTING_VALUES.rebind(rebindingTarget, e.getKeyCode());
                    rebinding = false;
                    DataManager.writeSettings(PsychicMemory.SETTING_VALUES);
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
