package mialee.psychicmemory.lang;

import mialee.psychicmemory.PsychicMemory;
import org.json.JSONException;

/**
 * A text instance which can be converted to the currently loaded language.
 */
public class TranslatableText {
    private final String string;

    /**
     * @param value The string code representing the specific text.
     */
    public TranslatableText(String value) {
        this.string = value;
    }

    /**
     * @return Reads from the currently loaded language and returns the current text translated into said language. If no language is present or the translation is missing will just return the raw string code.
     */
    @Override
    public String toString() {
        String string = this.string;
        try {
            string = PsychicMemory.LANGUAGE.getLang().get(string).toString();
        } catch (JSONException e) {
            PsychicMemory.LOGGER.loggedError(new TranslatableText("pm.lang.missing_translation"), this.string, PsychicMemory.LANGUAGE.getLangName());
        } catch (NullPointerException ignored) {}
        return string;
    }
}
