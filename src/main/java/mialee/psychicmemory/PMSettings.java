package main.java.mialee.psychicmemory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public class PMSettings {
    public static boolean setting1 = false;
    public static boolean setting2 = false;
    public static boolean setting3 = false;

    protected PMSettings() {
        //load();
        save();
    }


    public static void load() {
        try {
            JSONArray obj = new JSONArray(Files.readString(Path.of("run/settings.json")));
            setting1 = obj.getBoolean("setting1");
            setting2 = obj.getBoolean("setting2");
            setting3 = obj.getBoolean("setting3");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object getIfPresent() {
        return null;
    }

    public static void save() {
        Map<String, Object> settings = new LinkedHashMap<>();
        settings.put("setting1", setting1);
        settings.put("setting2", setting2);
        settings.put("setting3", setting3);

        boolean created = new File("run").mkdir();
        if (created) System.out.println("Creating run directory.");
        try (FileWriter file = new FileWriter("run/settings.json")) {
            file.write(String.valueOf(settings));
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
