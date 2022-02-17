package mialee.psychicmemory.lang;

import mialee.psychicmemory.PMGame;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;

import static mialee.psychicmemory.PMGame.LOGGER;

public class TranslatableText {
    private final String string;
    public TranslatableText(String value) {
        this.string = value;
    }

    @Override
    public String toString() {
        String string = this.string;
        try {
            string = PMGame.LANGUAGE.getLang().get(string).toString();
        } catch (JSONException e) {
            LOGGER.loggedError("Lang entry for \"%s\" not found in language %s.", this.string, PMGame.LANGUAGE.getLangName());
        } catch (NullPointerException ignored) {}
        return string;
    }
}
