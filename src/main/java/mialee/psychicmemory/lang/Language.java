package mialee.psychicmemory.lang;

import org.json.JSONObject;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import static mialee.psychicmemory.PsychicMemory.LOGGER;

public class Language {
    private JSONObject lang;
    private String langName;

    public Language(String name) {
        URL resource = TranslatableText.class.getClassLoader().getResource("assets/lang/%s.json".formatted(name));
        try {
            assert resource != null;
            lang = new JSONObject(Files.readString(Path.of(resource.toURI())));
            langName = name;
        } catch (Exception e) {
            LOGGER.loggedError(new TranslatableText("pm.lang.missing"), name);
        }
    }

    public JSONObject getLang() {
        return lang;
    }

    public String getLangName() {
        return langName;
    }
}
