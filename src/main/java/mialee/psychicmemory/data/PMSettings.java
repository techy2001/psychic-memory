package mialee.psychicmemory.data;

/**
 * An instance of this class is used to keep track of the player's custom settings.
 * In this case all the settings are custom keybindings.
 */
public class PMSettings {
    public int LEFT_KEY = 37;
    public int UP_KEY = 38;
    public int RIGHT_KEY = 39;
    public int DOWN_KEY = 40;
    public int FIRE_KEY = 90;
    public int BLANK_KEY = 88;
    public int SLOW_KEY = 16;
    public int PAUSE_KEY = 27;

    /**
     * Used to change a key, using an int to select the key.
     * Helps shorten code elsewhere.
     * @param key The input to change.
     * @param newInput The new key to use for said input.
     */
    public void rebind(int key, int newInput) {
        switch (key) {
            case 0 -> LEFT_KEY = newInput;
            case 1 -> UP_KEY = newInput;
            case 2 -> RIGHT_KEY = newInput;
            case 3 -> DOWN_KEY = newInput;
            case 4 -> FIRE_KEY = newInput;
            case 5 -> BLANK_KEY = newInput;
            case 6 -> SLOW_KEY = newInput;
            case 7 -> PAUSE_KEY = newInput;
        }
    }

    /**
     * Used to get the key for a specific input, by ID.
     * Helps shorten code elsewhere.
     * @param key The input to get the key for.
     * @return The keycode of the input.
     */
    public int getKeyByID(int key) {
        return switch (key) {
            case 0 -> LEFT_KEY;
            case 1 -> UP_KEY;
            case 2 -> RIGHT_KEY;
            case 3 -> DOWN_KEY;
            case 4 -> FIRE_KEY;
            case 5 -> BLANK_KEY;
            case 6 -> SLOW_KEY;
            default -> PAUSE_KEY;
        };
    }
}
