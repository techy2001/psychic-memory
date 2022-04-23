package mialee.psychicmemory.data;

import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.lang.TranslatableText;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.MissingFormatArgumentException;

/**
 * Used to log important messages.
 * These messages will be printed to the console and saved to a log file.
 */
public class TextLogger {
    long logTime;

    /**
     * The {@link #logTime} is used to dictate the file name.
     */
    public TextLogger() {
        logTime = Calendar.getInstance().getTimeInMillis();
    }

    /**
     * Logs with the default game prefix.
     * @param string The string to log.
     * @param args Formatting arguments for the string.
     */
    public void loggedPrint(TranslatableText string, Object ... args) {
        this.loggedPrint("[Psychic Memory]", string.toString(), args);
    }

    /**
     * Logs with the warning prefix, for errors.
     * @param string The string to log.
     * @param args Formatting arguments for the string.
     */
    public void loggedError(TranslatableText string, Object ... args) {
        this.loggedPrint("[Warning]", string.toString(), args);
    }

    /**
     * Handles the logging logic, both printing the text and writing them to a file.
     * @param string The string to log.
     * @param args Formatting arguments for the string.
     */
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
