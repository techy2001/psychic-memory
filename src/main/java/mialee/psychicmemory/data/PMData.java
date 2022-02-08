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

        //If the file isn't loaded, print that it will generate a new one.
        boolean loaded = PMData.load(Path.of("%s/settings.json".formatted(dir)), pmSettings);
        if (!loaded) System.out.print("Generating new settings file.\n");

        //Saves file immediately, easier to edit if needed.
        PMData.save(Path.of("%s/settings.json".formatted(dir)), pmSettings);

        return pmSettings;
    }

    //Creates a new PMSave object and populates it with save data read from a saveX.json file.
    //If no save is present generates a new one and saves it.
    public static PMSave populateSave(int save) {
        PMSave pmSave = new PMSave();

        //If the file isn't loaded, print that it will generate a new one.
        boolean loaded = PMData.load(Path.of("%s/save%d.json".formatted(dir, save)), pmSave);
        if (!loaded) System.out.printf("Generating new save file in save slot %d.\n", save);

        //Saves file immediately, easier to edit if needed.
        PMData.save(Path.of("%s/save%d.json".formatted(dir, save)), pmSave);

        return pmSave;
    }

    //Loads data from a selected json file to the selected Object.
    public static boolean load(Path path, Object map) {
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
                } catch (Exception ignored) {

                }
            }

            //Returns true if file was loaded successfully.
            System.out.printf("%s read successfully.\n", path.getName(path.getNameCount() - 1));
            return true;
        } catch (IOException e) {
            //Returns false if file not found and sends to log.
            System.out.printf("File %s not found.\n", path.getName(path.getNameCount() - 1));
            return false;
        }
    }

    //Saves data from an object to a selected json file at the path.
    public static void save(Path path, Object map) {
        JSONObject jsonObject = new JSONObject();

        //Takes data from all fields in the Object and writes them to the json object.
        for (Field field : map.getClass().getFields()) {
            try {
                jsonObject.put(field.getName(), map.getClass().getField(field.getName()).get(map));
            } catch (NoSuchFieldException e) {
                //This won't occur as fields are taken from the class.
                System.out.printf("Unexpected error.\n%s\n", e.getMessage());
            } catch (IllegalAccessException e) {
                System.out.printf("Unable to save %s classes to files as they are inaccessible.\n%s\n", map.getClass().getName(), e.getMessage());
            }
        }

        //Writes the data to a new json file at given path.
        try (FileWriter jsonFile = new FileWriter(String.valueOf(path))) {
            jsonFile.write(jsonObject.toString());
            jsonFile.flush();
        } catch (IOException e) {
            System.out.printf("Failed to save data.\n%s\n", e.getMessage());
        }
    }

    //Deletes data from a json returning it to its original values.
    public static void wipe(Path path, Object map) {
        try {
            save(path, map.getClass().newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            System.out.printf("Failed to wipe save data.\n%s\n", e.getMessage());
        }
    }
}
