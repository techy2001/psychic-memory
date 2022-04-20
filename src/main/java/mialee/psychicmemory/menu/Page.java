package mialee.psychicmemory.menu;

import mialee.psychicmemory.math.MathHelper;

import java.awt.Graphics;
import java.util.ArrayList;

public class Page {
    private final ArrayList<Button> buttons = new ArrayList<>();
    private final ArrayList<Text> texts = new ArrayList<>();
    private int selected = 0;

    public void addButton(Button button) {
        buttons.add(button);
    }

    public void addText(Text text) {
        texts.add(text);
    }

    public void render(Graphics graphics, boolean rebinding) {
        for (int i = 0; i < buttons.size(); i++) {
            Button button = buttons.get(i);
            if (button instanceof KeyButton keyButton) {
                keyButton.renderKeyButton(graphics, selected == i, rebinding);
            } else {
                button.render(graphics, selected == i);
            }
        }
        for (Text text : texts) {
            text.render(graphics);
        }
    }

    public void changeSelected(int change) {
        if (buttons.size() > 0) {
            this.selected = MathHelper.clampLoop(0, buttons.size() - 1, selected + change);
        }
    }

    public void selectLast() {
        this.selected = buttons.size() - 1;
    }

    public void pressButton() {
        this.buttons.get(selected).press();
    }
}
