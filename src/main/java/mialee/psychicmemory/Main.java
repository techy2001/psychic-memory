package mialee.psychicmemory;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        if (new File("run").mkdir()) System.out.println("Running first time setup.");
        new PMGame();
    }
}
