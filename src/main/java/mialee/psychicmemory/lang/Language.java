package mialee.psychicmemory.lang;

import org.json.JSONObject;

import java.net.URL;

import static mialee.psychicmemory.PMGame.LOGGER;

public class Language {
    private JSONObject lang;
    private String langName;

    public Language(String name) {
        URL resource = TranslatableText.class.getClassLoader().getResource("assets/lang/%s.json".formatted(name));
        try {
            assert resource != null;
            lang = new JSONObject(resource);
            langName = name;
        } catch (Exception e) {
            LOGGER.loggedError(e.toString());
            LOGGER.loggedError("Language \"%s\" not found.", name);
        }
    }

    public JSONObject getLang() {
        return lang;
    }

    public String getLangName() {
        return langName;
    }
}
