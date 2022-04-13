package mialee.psychicmemory.menu;

import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.math.MathHelper;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Menu implements KeyListener {
    private int selected = 0;
    private boolean inOptions = false;

    public void changeSelected(int change) {
        this.selected = MathHelper.clampLoop(0, 2, selected + change);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == 38 || keyCode == 87) {
            changeSelected(-1);
        } else if (keyCode == 83 || keyCode == 40) {
            changeSelected(1);
        } else if (keyCode == 90 || keyCode == 10) {
            if (!inOptions) {
                switch (selected) {
                    case 0 -> PsychicMemory.start();
                    case 1 -> {
                        inOptions = true;
                        selected = 0;
                    }
                    case 2 -> System.exit(1);
                }
            } else {
                inOptions = false;
                selected = 1;
            }
        } else if (keyCode == 88 || keyCode == 27) {
            if (inOptions) {
                inOptions = false;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
