package mialee.psychicmemory.menu;

import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.math.MathHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Menu implements KeyListener {
    private int selected = 0;
    private boolean inOptions = false;
    private final Button[] buttonsMain;
    private final Button[] buttonsOptions;

    public Menu() {
        buttonsMain = new Button[]{
                new Button(new ImageIcon[]{PsychicMemory.getIcon("menu/start_selected.png"), PsychicMemory.getIcon("menu/start.png")}, PsychicMemory::start, 700, 400, -5),
                new Button(new ImageIcon[]{PsychicMemory.getIcon("menu/options_selected.png"), PsychicMemory.getIcon("menu/options.png")}, () -> {
                        inOptions = true;
                        selected = 0;
                    }, 700, 460, -5),
                new Button(new ImageIcon[]{PsychicMemory.getIcon("menu/quit_selected.png"), PsychicMemory.getIcon("menu/quit.png")}, () -> System.exit(1), 700, 520, -5)
        };

        buttonsOptions = new Button[]{
                new Button(new ImageIcon[]{PsychicMemory.getIcon("menu/back_selected.png"), PsychicMemory.getIcon("menu/back.png")}, () -> {
                    inOptions = false;
                    selected = 1;
                }, 700, 520, -5)
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
        int keyCode = e.getKeyCode();
        if (keyCode == 38 || keyCode == 87) {
            changeSelected(-1);
        } else if (keyCode == 83 || keyCode == 40) {
            changeSelected(1);
        } else if (keyCode == 90 || keyCode == 10) {
            if (!inOptions) {
                buttonsMain[selected].press().run();
            } else {
                buttonsOptions[selected].press().run();
            }
        } else if (keyCode == 88 || keyCode == 27) {
            if (inOptions) {
                inOptions = false;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
