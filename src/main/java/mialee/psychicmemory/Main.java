package main.java.mialee.psychicmemory;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (new File("run").mkdir()) System.out.println("Running first time setup.");
        new PMGame();
    }
}
