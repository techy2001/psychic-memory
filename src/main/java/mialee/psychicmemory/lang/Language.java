package mialee.psychicmemory.lang;

import org.json.JSONObject;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import static mialee.psychicmemory.PMGame.LOGGER;

public class Language {
    private JSONObject lang;
    private String langName;

    public Language(String name) {
        URL resource = TranslatableText.class.getClassLoader().getResource("assets/lang/%s.json".formatted(name));
        try {
            assert resource != null;
            System.out.println(resource.toURI());
            System.out.println(Path.of(resource.toURI()));
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
