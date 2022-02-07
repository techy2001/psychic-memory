package mialee.psychicmemory;

import java.io.File;

public class Main {
    public static String dir = "PMData";

    public static void main(String[] args) {
        if (new File(dir).mkdir()) System.out.println("Running first time setup.");
        new PMGame();
    }
}
