package mialee.psychicmemory.data;

import mialee.psychicmemory.PsychicMemory;
import mialee.psychicmemory.lang.TranslatableText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class DataManager {
    public static PMSettings readSettings() {
        try {
            PMSettings settings = new PMSettings();
            Scanner scanner = new Scanner(new File("PMData/settings.txt"));
            settings.LEFT_KEY = Integer.parseInt(scanner.nextLine().split(" ")[1]);
            settings.UP_KEY = Integer.parseInt(scanner.nextLine().split(" ")[1]);
            settings.RIGHT_KEY = Integer.parseInt(scanner.nextLine().split(" ")[1]);
            settings.DOWN_KEY = Integer.parseInt(scanner.nextLine().split(" ")[1]);
            settings.FIRE_KEY = Integer.parseInt(scanner.nextLine().split(" ")[1]);
            settings.SLOW_KEY = Integer.parseInt(scanner.nextLine().split(" ")[1]);
            return settings;
        } catch (FileNotFoundException e) {
            PsychicMemory.LOGGER.loggedError(new TranslatableText("pm.data.file.missing"), "settings.txt", e.getMessage());
        } catch (NoSuchElementException | NumberFormatException e) {
            PsychicMemory.LOGGER.loggedError(new TranslatableText("pm.data.file.corrupt"), "settings.txt", e.getMessage());
        }
        writeSettings(new PMSettings());
        return new PMSettings();
    }

    public static void writeSettings(PMSettings settings) {
        try {
            File file = new File("PMData/settings.txt");
            FileWriter writer = new FileWriter(file, false);
            writer.write("LEFT_KEY: %d\n".formatted(settings.LEFT_KEY));
            writer.write("UP_KEY: %d\n".formatted(settings.UP_KEY));
            writer.write("RIGHT_KEY: %d\n".formatted(settings.RIGHT_KEY));
            writer.write("DOWN_KEY: %d\n".formatted(settings.DOWN_KEY));
            writer.write("FIRE_KEY: %d\n".formatted(settings.FIRE_KEY));
            writer.write("SLOW_KEY: %d\n".formatted(settings.SLOW_KEY));
            writer.close();
        } catch (IOException e) {
            PsychicMemory.LOGGER.loggedError(new TranslatableText("pm.data.file.missing"), "settings.txt", e.getMessage());
        }
    }

    public static GameRecord readHighScore() {
        ArrayList<GameRecord> scores = readScores();
        GameRecord highScore = new GameRecord("null", 0);
        for (GameRecord score : scores) {
            if (score.score() > highScore.score()) highScore = score;
        }
        return highScore;
    }

    public static ArrayList<GameRecord> readScores() {
        try {
            ArrayList<GameRecord> records = new ArrayList<>();
            Scanner scanner = new Scanner(new File("PMData/scores.txt"));
            while (scanner.hasNextLine()) {
                try {
                    String[] nextLine = scanner.nextLine().split(" ", 2);
                    GameRecord record = new GameRecord(nextLine[1], Integer.parseInt(nextLine[0]));
                    records.add(record);
                } catch (ArrayIndexOutOfBoundsException e) {
                    PsychicMemory.LOGGER.loggedError(new TranslatableText("pm.data.score.corrupt"), "scores.txt", e.getMessage());
                }
            }
            Collections.sort(records);
            return records;
        } catch (FileNotFoundException e) {
            PsychicMemory.LOGGER.loggedError(new TranslatableText("pm.data.file.missing"), "scores.txt", e.getMessage());
        } catch (NoSuchElementException | NumberFormatException e) {
            PsychicMemory.LOGGER.loggedError(new TranslatableText("pm.data.file.corrupt"), "scores.txt", e.getMessage());
        }
        return new ArrayList<>();
    }

    public static void writeScore(String name, int score) {
        try {
            File file = new File("PMData/scores.txt");
            FileWriter writer = new FileWriter(file, true);
            writer.write("\n%d %s".formatted(score, name));
            writer.close();
        } catch (IOException e) {
            PsychicMemory.LOGGER.loggedError(new TranslatableText("pm.data.file.missing"), "scores.txt", e.getMessage());
        }
    }
}
