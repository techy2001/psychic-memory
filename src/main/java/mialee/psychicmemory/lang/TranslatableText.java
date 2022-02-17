package mialee.psychicmemory.lang;

import mialee.psychicmemory.PMGame;
import org.json.JSONException;

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
            LOGGER.loggedError(new TranslatableText("pm.lang.missing_translation"), this.string, PMGame.LANGUAGE.getLangName());
        } catch (NullPointerException ignored) {}
        return string;
    }
}
