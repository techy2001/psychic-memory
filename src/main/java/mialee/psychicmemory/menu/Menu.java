package mialee.psychicmemory.menu;

import mialee.psychicmemory.GameState;
import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.math.MathHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static mialee.psychicmemory.PsychicMemory.SETTING_VALUES;

public class Menu implements KeyListener {
    private int selected = 0;
    private boolean inOptions = false;
    private final Button[] buttonsMain;
    private final Button[] buttonsOptions;

    public Menu() {
        buttonsMain = new Button[]{
                new Button(new ImageIcon[]{PsychicMemory.getIcon("menu/start_selected.png"), PsychicMemory.getIcon("menu/start.png")}, PsychicMemory::start,
                        360, 400, -5),
                new Button(new ImageIcon[]{PsychicMemory.getIcon("menu/options_selected.png"), PsychicMemory.getIcon("menu/options.png")}, () -> {
                        inOptions = true;
                        selected = 0;
                    }, 360, 470, -5),
                new Button(new ImageIcon[]{PsychicMemory.getIcon("menu/quit_selected.png"), PsychicMemory.getIcon("menu/quit.png")}, () -> System.exit(1),
                        360, 540, -5)
        };

        buttonsOptions = new Button[]{
                new Button(new ImageIcon[]{PsychicMemory.getIcon("menu/back_selected.png"), PsychicMemory.getIcon("menu/back.png")}, () -> {
                    inOptions = false;
                    selected = 1;
                }, 360, 540, -5)
        };
    }

    public void render(Graphics graphics) {
        if (!inOptions) {
            for (int i = 0; i < buttonsMain.length; i++) {
                buttonsMain[i].render(graphics, selected == i);
            }
        } else {
            for (int i = 0; i < buttonsOptions.length; i++) {
                buttonsOptions[i].render(graphics, selected == i);
            }
        }
    }

    public void changeSelected(int change) {
        if (!inOptions) {
            this.selected = MathHelper.clampLoop(0, buttonsMain.length - 1, selected + change);
        } else {
            this.selected = MathHelper.clampLoop(0, buttonsOptions.length - 1, selected + change);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (PsychicMemory.gameState == GameState.MENU) {
            int keyCode = e.getKeyCode();
            if (keyCode == SETTING_VALUES.UP_KEY) {
                changeSelected(-1);
            } else if (keyCode == SETTING_VALUES.DOWN_KEY) {
                changeSelected(1);
            } else if (keyCode == SETTING_VALUES.FIRE_KEY) {
                if (!inOptions) {
                    buttonsMain[selected].press().run();
                } else {
                    buttonsOptions[selected].press().run();
                }
            } else if (keyCode == SETTING_VALUES.SLOW_KEY) {
                if (inOptions) {
                    inOptions = false;
                } else {
                    selected = 2;
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
