package mialee.psychicmemory.lang;

import mialee.psychicmemory.PsychicMemory;
import org.json.JSONObject;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

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
            PsychicMemory.LOGGER.loggedError(new TranslatableText("pm.data.lang.missing"), name, e.getMessage());
        }
    }

    public JSONObject getLang() {
        return lang;
    }

    public String getLangName() {
        return langName;
    }
}
