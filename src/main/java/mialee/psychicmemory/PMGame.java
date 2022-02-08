package mialee.psychicmemory;

import mialee.psychicmemory.data.PMData;
import mialee.psychicmemory.data.PMLogger;
import mialee.psychicmemory.data.PMSave;
import mialee.psychicmemory.data.PMSettings;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class PMGame {
    //Name given to the saved data folder.
    public static String dir = "PMData";
    //Logger used to keep records of past games.
    public static PMLogger LOGGER;
    //The games current settings values.
    public static PMSettings SETTING_VALUES;
    //A map containing all the individual save files by number.
    public static Map<Integer, PMSave> SAVE_VALUES;

    public static void main(String[] args) {
        //Starts by creating a new logger, which will be used instead of System.out.print.
        LOGGER = new PMLogger();

        //Prints an extra message if the data directory has just been created.
        if (new File(dir + "/logs/").mkdirs()) LOGGER.loggedPrint("Running first time setup.");

        //Takes the settings and save file data from the data directory for easy access.
        SETTING_VALUES = PMData.populateSettings();
        SAVE_VALUES = new LinkedHashMap<>();
        for(int i = 1; i <= 3; i++) SAVE_VALUES.put(i, PMData.populateSave(i));
    }
}
