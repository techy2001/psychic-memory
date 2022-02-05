package main.java.mialee.psychicmemory;

import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PMSettings {
    public static boolean setting1 = false;
    public static boolean setting2 = false;
    public static boolean setting3 = false;

    protected PMSettings() {
        load();
        save();
    }

    public static void load() {
        if (Path.of("run/settings.json").isAbsolute())
        try {
            JSONObject obj = new JSONObject(Files.readString(Path.of("run/settings.json")));

            if (obj.has("settings1")) setting1 = obj.getBoolean("setting1");
            if (obj.has("settings2")) setting2 = obj.getBoolean("setting2");
            if (obj.has("settings3")) setting3 = obj.getBoolean("setting3");

        } catch (IOException ignored) {}
    }

    public static void save() {
        JSONObject settings = new JSONObject();

        settings.put("setting1", setting1);
        settings.put("setting2", setting2);
        settings.put("setting3", setting3);

        if (new File("run").mkdir()) System.out.println("Creating run directory.");
        try (FileWriter file = new FileWriter("run/settings.json")) {
            file.write(settings.toString());
            file.flush();
        } catch (IOException ignored) {}
    }
}
