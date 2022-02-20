package mialee.psychicmemory.bonus;

import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class XOGameAI {
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();
    static char[][] board = new char[3][3];
    static Difficulty difficulty1;
    static Difficulty difficulty2;

    public static void main(String[] args) {
        while(true) {
            System.out.print("Input command: ");
            String[] input = scanner.nextLine().split(" ");

            if (Objects.equals(input[0], "start") && input.length > 2) {
                switch (input[1]) {
                    case "easy":
                        difficulty1 = Difficulty.EASY;
                        break;
                    case "medium":
                        difficulty1 = Difficulty.MEDIUM;
                        break;
                    case "hard":
                        difficulty1 = Difficulty.HARD;
                        break;
                    case "user":
                        difficulty1 = Difficulty.USER;
                        break;
                    default:
                        System.out.println("Bad parameters!");
                        continue;
                }
                switch (input[2]) {
                    case "easy":
                        difficulty2 = Difficulty.EASY;
                        break;
                    case "medium":
                        difficulty2 = Difficulty.MEDIUM;
                        break;
                    case "hard":
                        difficulty2 = Difficulty.HARD;
                        break;
                    case "user":
                        difficulty2 = Difficulty.USER;
                        break;
                    default:
                        System.out.println("Bad parameters!");
                        continue;
                }
                game();
            } else if(Objects.equals(input[0], "exit")) {
                return;
            } else {
                System.out.println("Bad parameters!");
            }
        }
    }

    public static void game() {
        makeBoard();

        boolean xTurn = true;
        while(true) {
            printBoard();

            gameTurn(xTurn ? difficulty1 : difficulty2, xTurn ? 'X' : 'O', true);
            xTurn = !xTurn;

            char state = getBoardState();
            if (((state == 'X') || (state == 'O') || (state == 'D'))) {
                printBoard();
                printBoardState();
                break;
            }
        }
    }

    public static void gameTurn(Difficulty difficulty, char character, boolean print) {
        switch (difficulty) {
            case USER:
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
            case EASY:
                if (print) System.out.println("Making move level \"easy\"");
                while(true) {
                    int x = random.nextInt(3);
                    int y = random.nextInt(3);
                    if (board[x][y] == 'X' || board[x][y] == 'O') {
                        continue;
                    }
                    board[x][y] = character;
                    return;
                }
            case MEDIUM:
                if (print) System.out.println("Making move level \"medium\"");

                int thisCount;

                for(int i = 0; i < 3; i++) {
                    thisCount = 0;
                    for(int j = 0; j < 3; j++) {
                        if (board[i][j] == character) {
                            thisCount++;
                        }
                    }
                    if (thisCount == 2) {
                        for(int j = 0; j < 3; j++) {
                            if (board[i][j] == ' ') {
                                board[i][j] = character;
                                return;
                            }
                        }
                    }
                }
                for(int i = 0; i < 3; i++) {
                    thisCount = 0;
                    for(int j = 0; j < 3; j++) {
                        if (board[j][i] == character) {
                            thisCount++;
                        }
                    }
                    if (thisCount == 2) {
                        for(int j = 0; j < 3; j++) {
                            if (board[j][i] == ' ') {
                                board[j][i] = character;
                                return;
                            }
                        }
                    }
                }

                thisCount = 0;
                for(int i = 0; i < 3; i++) {
                    if (board[i][i] == character) {
                        thisCount++;
                    }
                }
                if (thisCount == 2) {
                    for(int i = 0; i < 3; i++) {
                        if (board[i][i] == ' ') {
                            board[i][i] = character;
                            return;
                        }
                    }
                }

                thisCount = 0;
                for(int i = 0; i < 3; i++) {
                    if (board[i][2 - i] == character) {
                        thisCount++;
                    }
                }
                if (thisCount == 2) {
                    for(int i = 0; i < 3; i++) {
                        if (board[i][2 - i] == ' ') {
                            board[i][2 - i] = character;
                            return;
                        }
                    }
                }

                int otherCount;
                char otherChar = character == 'X' ? 'O' : 'X';

                for(int i = 0; i < 3; i++) {
                    otherCount = 0;
                    for(int j = 0; j < 3; j++) {
                        if (board[i][j] == otherChar) {
                            otherCount++;
                        }
                    }
                    if (otherCount == 2) {
                        for(int j = 0; j < 3; j++) {
                            if (board[i][j] == ' ') {
                                board[i][j] = character;
                                return;
                            }
                        }
                    }
                }
                for(int i = 0; i < 3; i++) {
                    otherCount = 0;
                    for(int j = 0; j < 3; j++) {
                        if (board[j][i] == otherChar) {
                            otherCount++;
                        }
                    }
                    if (otherCount == 2) {
                        for(int j = 0; j < 3; j++) {
                            if (board[j][i] == ' ') {
                                board[j][i] = character;
                                return;
                            }
                        }
                    }
                }

                otherCount = 0;
                for(int i = 0; i < 3; i++) {
                    if (board[i][i] == otherChar) {
                        otherCount++;
                    }
                }
                if (otherCount == 2) {
                    for(int i = 0; i < 3; i++) {
                        if (board[i][i] == ' ') {
                            board[i][i] = character;
                            return;
                        }
                    }
                }

                otherCount = 0;
                for(int i = 0; i < 3; i++) {
                    if (board[i][2 - i] == otherChar) {
                        otherCount++;
                    }
                }
                if (otherCount == 2) {
                    for(int i = 0; i < 3; i++) {
                        if (board[i][2 - i] == ' ') {
                            board[i][2 - i] = character;
                            return;
                        }
                    }
                }

                gameTurn(Difficulty.EASY, character, false);
                break;
            case HARD:
                if (print) System.out.println("Making move level \"hard\"");

                int best = Integer.MIN_VALUE;
                int[] pos = null;
                for(int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (board[i][j] == ' ') {
                            board[i][j] = character;
                            int score = posValue(0, character, false);
                            board[i][j] = ' ';
                            if (score > best) {
                                best = score;
                                pos = new int[]{i, j};
                            }
                        }
                    }
                }
                if (pos == null) {
                    gameTurn(Difficulty.MEDIUM, character, false);
                } else {
                    board[pos[0]][pos[1]] = character;
                }
                break;
        }
    }

    public static int posValue(int depth, char player, boolean playerTurn) {
        switch (getBoardState()) {
            case 'D':
                return 0;
            case 'X':
                return player == 'X' ? 1 : -1;
            case 'O':
                return player == 'O' ? 1 : -1;
        }
        char enemy = player == 'X' ? 'O' : 'X';

        int best = playerTurn ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = playerTurn ? player : enemy;
                    int score = posValue(depth + 1, player, !playerTurn);
                    board[i][j] = ' ';
                    best = playerTurn ? Math.max(score, best) : Math.min(score, best);
                }
            }
        }
        return best;
    }

    public static void makeBoard() {
        board = new char[3][3];
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                board[i][j] = ' ';
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
                    case 'X':
                        xCount++;
                        break;
                    case 'O':
                        oCount++;
                        break;
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
                    case 'X':
                        xCount++;
                        break;
                    case 'O':
                        oCount++;
                        break;
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
                case 'X':
                    xCount++;
                    break;
                case 'O':
                    oCount++;
                    break;
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
                case 'X':
                    xCount++;
                    break;
                case 'O':
                    oCount++;
                    break;
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
                    case 'X':
                        xCount++;
                        break;
                    case 'O':
                        oCount++;
                        break;
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
            case 'U':
                System.out.println("Game not finished");
                break;
            case 'D':
                System.out.println("Draw");
                break;
            case 'X':
                System.out.println("X wins");
                break;
            case 'O':
                System.out.println("O wins");
                break;
            case 'I':
                System.out.println("Impossible");
                break;
            default:
                System.out.println("Error");
                break;
        }
    }
    public static void setBoard() {
        System.out.print("Enter the cells: ");
        char[] input = scanner.nextLine().toCharArray();

        for(int i = 0; i < 3; i++) {
            System.arraycopy(input, (i * 3), board[i], 0, 3);
        }
    }
}

enum Difficulty {
    USER,
    EASY,
    MEDIUM,
    HARD
}