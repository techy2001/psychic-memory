package mialee.psychicmemory.data;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import static mialee.psychicmemory.PMGame.dir;

//This class is used instead of System.out.print, in order to save copies of the game's logs.
public class PMLogger {
    long logTime;

    public PMLogger() {
        //This time is used to name the log file.
        logTime = Calendar.getInstance().getTimeInMillis();
    }

    //The standard print, uses the standard prefix.
    public void loggedPrint(String string, Object ... args) {
        this.loggedPrint("[Psychic Memory]", string, args);
    }

    //An error print, uses a warning prefix.
    public void loggedError(String string, Object ... args) {
        this.loggedPrint("[Warning]", string, args);
    }

    //The logged print, takes a prefix and a formatted message.
    public void loggedPrint(String prefix, String string, Object ... args) {
        try {
            //Opens a new file writer and appends the newest message to the current log.
            FileWriter writer = new FileWriter(dir + "/logs/log-" + logTime + ".txt", true);
            writer.write(prefix + " " + string.formatted(args) + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.printf("[Warning] Unable to write to log. %s\n", e.getMessage());
        }
        //Prints the message to the console.
        System.out.printf(prefix + " " + string + "\n", args);
    }
}
