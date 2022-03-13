package mialee.psychicmemory;

import mialee.psychicmemory.data.DataManager;
import mialee.psychicmemory.data.TextLogger;
import mialee.psychicmemory.data.PMSave;
import mialee.psychicmemory.data.PMSettings;
import mialee.psychicmemory.game.Board;
import mialee.psychicmemory.game.entities.Entity;
import mialee.psychicmemory.lang.Language;
import mialee.psychicmemory.lang.TranslatableText;
import mialee.psychicmemory.math.Vec2d;
import mialee.psychicmemory.window.Renderer;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class PsychicMemory {
    //Name given to the saved data folder.
    public static String dir = "PMData";
    //Logger used to keep records of past games.
    public static TextLogger LOGGER;
    //Language allows for multiple language translations.
    public static Language LANGUAGE;
    //The games current settings values.
    public static PMSettings SETTING_VALUES;
    //A map containing all the individual save files by number.
    public static Map<Integer, PMSave> SAVE_VALUES;


    public static Board board = new Board();

    public static void main(String[] args) {
        //Starts by creating a new logger, which will be used instead of System.out.print.
        LOGGER = new TextLogger();

        //Sets a new language.
        LANGUAGE = new Language("en_ie");

        //Prints an extra message if the data directory has just been created.
        if (new File(dir + "/logs/").mkdirs()) LOGGER.loggedPrint(new TranslatableText("pm.data.setup"));

        //Takes the settings and save file data from the data directory for easy access.
        SETTING_VALUES = DataManager.populateSettings();
        SAVE_VALUES = new LinkedHashMap<>();
        for(int i = 1; i <= 3; i++) SAVE_VALUES.put(i, DataManager.populateSave(i));

        Renderer.startRenderer();

        board.entities.add(new Entity(board, new Vec2d(0, 0), new Vec2d(0, 0)));
    }
}
