package mialee.psychicmemory;

import mialee.psychicmemory.data.DataManager;
import mialee.psychicmemory.data.PMSave;
import mialee.psychicmemory.data.PMSettings;
import mialee.psychicmemory.data.TextLogger;
import mialee.psychicmemory.game.World;
import mialee.psychicmemory.lang.Language;
import mialee.psychicmemory.lang.TranslatableText;
import mialee.psychicmemory.registry.EntityRegistry;
import mialee.psychicmemory.window.Renderer;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class PsychicMemory {
    public static String dir = "PMData";
    public static TextLogger LOGGER;
    public static Language LANGUAGE;
    public static PMSettings SETTING_VALUES;
    public static Map<Integer, PMSave> SAVE_VALUES;
    public static World world = new World();

    public static void main(String[] args) {
        LOGGER = new TextLogger();
        LANGUAGE = new Language("en_ie");
        if (new File(dir + "/logs/").mkdirs()) LOGGER.loggedPrint(new TranslatableText("pm.data.setup"));
        SETTING_VALUES = DataManager.populateSettings();
        SAVE_VALUES = new LinkedHashMap<>();
        for(int i = 1; i <= 3; i++) SAVE_VALUES.put(i, DataManager.populateSave(i));
        EntityRegistry.initialize();
        Renderer.startRenderer();
    }
}
