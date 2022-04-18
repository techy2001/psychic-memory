package mialee.psychicmemory.data;

import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.lang.TranslatableText;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.MissingFormatArgumentException;

public class TextLogger {
    long logTime;

    public TextLogger() {
        logTime = Calendar.getInstance().getTimeInMillis();
    }

    public void loggedPrint(TranslatableText string, Object ... args) {
        this.loggedPrint("[Psychic Memory]", string.toString(), args);
    }

    public void loggedError(TranslatableText string, Object ... args) {
        this.loggedPrint("[Warning]", string.toString(), args);
    }

    public void loggedPrint(String prefix, String string, Object ... args) {
        String formatted;
        try {
            formatted = "%s %s\n".formatted(prefix, string.formatted(args));
        } catch (MissingFormatArgumentException e) {
            System.out.printf("[Warning] Not enough args for string \"%s\".\n", string);
            formatted = "%s %s\n".formatted(prefix, string);
        }
        try {
            FileWriter writer = new FileWriter(PsychicMemory.dir + "/logs/log-" + logTime + ".txt", true);
            writer.write(formatted);
            writer.close();
        } catch (IOException e) {
            System.out.printf("[Warning] Unable to write to log. %s\n", e.getMessage());
        }
        System.out.print(formatted);
    }
}
