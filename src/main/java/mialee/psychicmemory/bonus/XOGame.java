package mialee.psychicmemory.bonus;

import java.util.Scanner;

public class XOGame {
    static Scanner scanner = new Scanner(System.in);
    static char[][] board = new char[3][3];

    public static void main(String[] args) {
        makeBoard();

        boolean xTurn = true;
        while(true) {
            printBoard();

            setCell(xTurn ? 'X' : 'O');
            xTurn = !xTurn;

            char state = getBoardState();
            if (((state == 'X') || (state == 'O') || (state == 'D'))) {
                printBoard();
                printBoardState();
                break;
            }
        }
    }

    public static void makeBoard() {
        board = new char[3][3];
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }
    public static void setCell(char character) {
        while(true) {
            try {
                System.out.print("Enter the coordinates: ");
                String[] input = scanner.nextLine().split(" ");
                int x = Integer.parseInt(input[0]) - 1;
                int y = Integer.parseInt(input[1]) - 1;
                if (x > 2 || x < 0 || y > 2 || y < 0) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    continue;
                }
                if (board[x][y] == 'X' || board[x][y] == 'O') {
                    System.out.println("This cell is occupied! Choose another one!");
                    continue;
                }
                board[x][y] = character;
                return;
            } catch (Exception ignored) {
                System.out.println("You should enter numbers!");
            }
        }
    }
    public static void printBoard() {
        System.out.println("---------");
        for(int i = 0; i < 3; i++) {
            System.out.print("| ");
            for(int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }
    public static char getBoardState() {
        int xWins = 0;
        int oWins = 0;
        int xCount;
        int oCount;

        for(int i = 0; i < 3; i++) {
            xCount = 0;
            oCount = 0;
            for(int j = 0; j < 3; j++) {
                switch (board[i][j]) {
                    case 'X' -> xCount++;
                    case 'O' -> oCount++;
                }
            }
            if (xCount == 3) {
                xWins++;
            } else if (oCount == 3) {
                oWins++;
            }
        }
        for(int i = 0; i < 3; i++) {
            xCount = 0;
            oCount = 0;
            for(int j = 0; j < 3; j++) {
                switch (board[j][i]) {
                    case 'X' -> xCount++;
                    case 'O' -> oCount++;
                }
            }
            if (xCount == 3) {
                xWins++;
            } else if (oCount == 3) {
                oWins++;
            }
        }
        xCount = 0;
        oCount = 0;
        for(int i = 0; i < 3; i++) {
            switch (board[i][i]) {
                case 'X' -> xCount++;
                case 'O' -> oCount++;
            }
            if (xCount == 3) {
                xWins++;
            } else if (oCount == 3) {
                oWins++;
            }
        }
        xCount = 0;
        oCount = 0;
        for(int i = 0; i < 3; i++) {
            switch (board[i][2 - i]) {
                case 'X' -> xCount++;
                case 'O' -> oCount++;
            }
            if (xCount == 3) {
                xWins++;
            } else if (oCount == 3) {
                oWins++;
            }
        }

        xCount = 0;
        oCount = 0;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                switch (board[i][j]) {
                    case 'X' -> xCount++;
                    case 'O' -> oCount++;
                }
            }
        }

        if ((Math.abs(xCount - oCount) > 1) || (xWins > 0 && oWins > 0)) return 'I';
        if (xCount + oCount == 9 && xWins == 0 && oWins == 0) return 'D';
        if (xWins == 0 && oWins == 0) return 'U';
        if (xWins > 0) return 'X';
        if (oWins > 0) return 'O';
        return 'E';
    }
    public static void printBoardState() {
        switch (getBoardState()) {
            case 'U' -> System.out.println("Game not finished");
            case 'D' -> System.out.println("Draw");
            case 'X' -> System.out.println("X wins");
            case 'O' -> System.out.println("O wins");
            case 'I' -> System.out.println("Impossible");
            default -> System.out.println("Error");
        }
    }
}
