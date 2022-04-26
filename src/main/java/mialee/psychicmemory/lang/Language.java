package mialee.psychicmemory.lang;

import mialee.psychicmemory.Main;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

/**
 * A class used to keep track of language files stored in JSON.
 */
public class Language {
    private JSONObject lang;
    private String langName;

    /**
     * The language instance will store a JSONObject of translations, allowing the {@link mialee.psychicmemory.lang.TranslatableText} to be translated.
     * @param name Name of the language file to read from.
     */
    public Language(String name) {
        URL resource = TranslatableText.class.getClassLoader().getResource("assets/lang/%s.json".formatted(name));
        try {
            assert resource != null;
            InputStream stream = resource.openStream();
            StringBuilder langRaw = new StringBuilder();
            int cp;
            while ((cp = stream.read()) != -1) {
                langRaw.append((char) cp);
            }
            lang = new JSONObject(langRaw.toString());
            langName = name;
        } catch (Exception e) {
            Main.LOGGER.loggedError(new TranslatableText("pm.data.lang.missing"), name, e.getMessage());
        }
    }

    public JSONObject getLang() {
        return lang;
    }

    public String getLangName() {
        return langName;
    }
}
