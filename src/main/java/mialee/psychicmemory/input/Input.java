package mialee.psychicmemory.input;

import mialee.psychicmemory.GameState;
import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.data.DataManager;
import mialee.psychicmemory.menu.Menu;
import mialee.psychicmemory.menu.Page;
import mialee.psychicmemory.menu.ScoreMenu;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {
    private static final boolean[] pressedKeys = new boolean[128];
    public static Menu menu;
    public static ScoreMenu scoreMenu;

    public static boolean getKey(int keyCode) {
        return pressedKeys[keyCode];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() < 128) pressedKeys[e.getKeyCode()] = true;

        if (PsychicMemory.gameState == GameState.MENU) {
            if (!menu.rebinding) {
                int keyCode = e.getKeyCode();
                Page page = menu.pages.get(menu.selectedPage);
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
                    if (e.getKeyCode() == PsychicMemory.SETTING_VALUES.getKeyByID(i) && !(menu.rebindingTarget == i)) {
                        available = false;
                        break;
                    }
                }
                if (available) {
                    PsychicMemory.SETTING_VALUES.rebind(menu.rebindingTarget, e.getKeyCode());
                    menu.rebinding = false;
                    DataManager.writeSettings(PsychicMemory.SETTING_VALUES);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() < 128) pressedKeys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (PsychicMemory.gameState == GameState.SCORE) {
            if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                scoreMenu.removeKey();
                return;
            }
            if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                scoreMenu.finishName();
                return;
            }
            scoreMenu.addKey(e.getKeyChar());
        }
    }
}
