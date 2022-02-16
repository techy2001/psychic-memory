package mialee.psychicmemory.lang;

import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;

import static mialee.psychicmemory.PMGame.LOGGER;

public class TranslatableText {
    private static String lang = "en_ie";

    private final String string;
    public TranslatableText(String value) {
        this.string = value;
    }

    public static String load(String string) {
        try {
            //Reads data from the lang file.
            JSONObject jsonObject = new JSONObject(TranslatableText.class.getClassLoader().getResource("assets/lang/%s.json".formatted(lang)));
            LOGGER.loggedPrint(TranslatableText.class.getClassLoader().getResource("assets/lang/%s.json".formatted(lang)).toString());
            //LOGGER.loggedPrint(TranslatableText.class.getClassLoader().getResource("assets/lang/%s.json".formatted(lang)).getFile());
            //LOGGER.loggedPrint(jsonObject.get(string).toString());
            //return string;
            return jsonObject.get(string).toString();
        } catch (Exception e) {
            //Logs error .
            LOGGER.loggedError(e.toString());
            LOGGER.loggedError("Lang entry for \"%s\" not found.", string);
            return string;
        }
    }

    @Override
    public String toString() {
        return load(string);
    }
}
