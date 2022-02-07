package mialee.psychicmemory.data;

import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;

import static mialee.psychicmemory.Main.dir;

//This class is used to manage Settings and Save data.
public class PMData {
    //Creates a new PMSettings object and populates it with settings read from the settings.json file.
    //If no json is present generates a new one and saves it.
    public static PMSettings populateSettings() {
        PMSettings pmSettings = new PMSettings();
        PMData.load(Path.of("%s/settings.json".formatted(dir)), pmSettings);
        PMData.save(Path.of("%s/settings.json".formatted(dir)), pmSettings);
        return pmSettings;
    }

    //Creates a new PMSave object and populates it with save data read from a saveX.json file.
    //If no save is present generates a new one and saves it.
    public static PMSave populateSave(int save) {
        PMSave pmSave = new PMSave();
        PMData.load(Path.of("%s/save%d.json".formatted(dir, save)), pmSave);
        PMData.save(Path.of("%s/save%d.json".formatted(dir, save)), pmSave);
        return pmSave;
    }

    //Loads data from a selected json file to the selected Object.
    private static void load(Path path, Object map) {
        try {
            //Reads data from the existing json file.
            //If file is not present, throws an exception and exits the method safely with the try, catch.
            //If this occurs, default values are used and saved to a new file.
            JSONObject jsonObject = new JSONObject(Files.readString(path));

            //Writes all data from the json file to the object.
            //If the data has no place in the object skips that data using try, catch.
            for (Field field : map.getClass().getFields()) {
                try {
                    if (jsonObject.has(field.getName())) {
                        map.getClass().getField(field.getName()).set(map, jsonObject.get(field.getName()));
                    }
                } catch (Exception ignored) {}
            }
        } catch (IOException ignored) {}
    }

    //Saves data from an object to a selected json file at the path.
    private static void save(Path path, Object map) {
        JSONObject jsonObject = new JSONObject();

        //Takes data from all fields in the Object and writes them to the json object.
        for (Field field : map.getClass().getFields()) {
            try {
                jsonObject.put(field.getName(), map.getClass().getField(field.getName()).get(map));
            } catch (Exception ignored) {}
        }

        //Writes the data to a new json file at given path.
        try (FileWriter jsonFile = new FileWriter(String.valueOf(path))) {
            jsonFile.write(jsonObject.toString());
            jsonFile.flush();
        } catch (IOException ignored) {}
    }
}
