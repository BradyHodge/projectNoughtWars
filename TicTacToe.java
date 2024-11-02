import java.util.Random;

public class TicTacToe {
    private char[][] board;
    private char currentPlayer;
    private boolean gameEnded;
    private int moveCount;

    public TicTacToe() {
        board = new char[3][3];
        currentPlayer = 'X';
        gameEnded = false;
        moveCount = 0;
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    private void printBoard() {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean makeMove(int row, int col) {
        if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ') {
            board[row][col] = currentPlayer;
            return true;
        }
        return false;
    }

    private void handleInvalidInput() {
        System.out.println("Invalid input. Please enter numbers between 0 and 2.");
    }

    private void aiMove() {
        Random random = new Random();
        int row = random.nextInt(3);
        int col = random.nextInt(3);
        while (!makeMove(row, col)) {
            row = random.nextInt(3);
            col = random.nextInt(3);
        }
    }

    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
        moveCount = 0;
        currentPlayer = 'X';
        gameEnded = false;
    }

    public void play() {
        long startTime = System.currentTimeMillis();

        while (!gameEnded) {
            printBoard();
            System.out.println("Player " + currentPlayer + "'s turn");
            
            if (currentPlayer == 'X') {
                System.out.print("Enter row (0-2): ");
                int row = getValidInput();
                System.out.print("Enter column (0-2): ");
                int col = getValidInput();
                
                if (makeMove(row, col)) {
                    moveCount++;
                    
                    if (moveCount > 9) {
                        aiMove();
                        moveCount++;
                    }
                } else {
                    handleInvalidInput();
                }
            } else {
                aiMove();
                moveCount++;
            }

            if (System.currentTimeMillis() - startTime > 30000) { 
                System.out.println("Time's up! Switching players.");
                switchPlayers();
            }
        }

        printBoard();
        if (moveCount == 9) {
            System.out.println("It's a draw!");
        } else {
            System.out.println("AI wins!");
        }
    }

    private int getValidInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                int input = scanner.nextInt();
                if (input >= 0 && input <= 2) {
                    return input;
                } else {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                handleInvalidInput();
            }
        }
    }

    private void switchPlayers() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.play();
    }
}
