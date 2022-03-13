package mialee.psychicmemory.data;

import mialee.psychicmemory.lang.TranslatableText;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.MissingFormatArgumentException;

import static mialee.psychicmemory.PsychicMemory.dir;

//This class is used instead of System.out.print, in order to save copies of the game's logs.
public class TextLogger {
    long logTime;

    public TextLogger() {
        //This time is used to name the log file.
        logTime = Calendar.getInstance().getTimeInMillis();
    }

    //The standard print, uses the standard prefix.
    public void loggedPrint(TranslatableText string, Object ... args) {
        this.loggedPrint("[Psychic Memory]", string.toString(), args);
    }

    //An error print, uses a warning prefix.
    public void loggedError(TranslatableText string, Object ... args) {
        this.loggedPrint("[Warning]", string.toString(), args);
    }

    //The logged print, takes a prefix and a formatted message.
    public void loggedPrint(String prefix, String string, Object ... args) {
        String formatted;
        try {
            formatted = "%s %s\n".formatted(prefix, string.formatted(args));
        } catch (MissingFormatArgumentException e) {
            System.out.printf("[Warning] Not enough args for string \"%s\".\n", string);
            formatted = "%s %s\n".formatted(prefix, string);
        }
        try {
            //Opens a new file writer and appends the newest message to the current log.
            FileWriter writer = new FileWriter(dir + "/logs/log-" + logTime + ".txt", true);
            writer.write(formatted);
            writer.close();
        } catch (IOException e) {
            System.out.printf("[Warning] Unable to write to log. %s\n", e.getMessage());
        }
        //Prints the message to the console.
        System.out.print(formatted);
    }
}
