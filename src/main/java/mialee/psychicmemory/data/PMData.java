package main.java.mialee.psychicmemory.data;

import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public class PMData {
    // Populates any given LinkedHashMap<String, Object> with the games settings,
    // the games settings are initialized here, and the values here are the default values.
    public static void populateSettings(LinkedHashMap<String, Object> map) {
        PMSettings pmSettings = new PMSettings();
        for (Field field : pmSettings.getClass().getFields()) {
            try {
                map.put(field.getName(), field.get(pmSettings));
            } catch (Exception ignored) {}
        }
        PMData.load(Path.of("run/settings.json"), map);
        PMData.save(Path.of("run/settings.json"), map);
    }

    public static PMSave populateSave(int save) {
        PMSave pmSave = new PMSave();
        PMData.load(Path.of("run/save%d.json".formatted(save)), pmSave);
        PMData.save(Path.of("run/save%d.json".formatted(save)), pmSave);
        return pmSave;
    }

    //Loads
    private static void load(Path path, Object map) {
        if (path.isAbsolute()) {
            try {
                //Reads data from the existing json file.
                JSONObject jsonObject = new JSONObject(Files.readString(path));

                //Sets all existing data in the map to match the json file.
                //Any other data not declared in the populate methods would not be needed.
                for (Field field : map.getClass().getFields()) {
                    try {
                        if (jsonObject.has(field.getName())) {
                            map.getClass().getField(field.getName()).set(map, jsonObject.get(field.getName()));
                        }
                    } catch (Exception ignored) {
                    }
                }
            } catch (IOException ignored) {}
        }
    }

    private static void save(Path path, Object map) {
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            jsonObject.put(entry.getKey(), entry.getValue());
        }
        try (FileWriter jsonFile = new FileWriter(String.valueOf(path))) {
            jsonFile.write(jsonObject.toString());
            jsonFile.flush();
        } catch (IOException ignored) {}
    }
}
