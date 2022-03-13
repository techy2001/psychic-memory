package mialee.psychicmemory.lang;

import mialee.psychicmemory.PsychicMemory;
import org.json.JSONException;

import static mialee.psychicmemory.PsychicMemory.LOGGER;

public class TranslatableText {
    private final String string;
    public TranslatableText(String value) {
        this.string = value;
    }

    @Override
    public String toString() {
        String string = this.string;
        try {
            string = PsychicMemory.LANGUAGE.getLang().get(string).toString();
        } catch (JSONException e) {
            LOGGER.loggedError(new TranslatableText("pm.lang.missing_translation"), this.string, PsychicMemory.LANGUAGE.getLangName());
        } catch (NullPointerException ignored) {}
        return string;
    }
}
