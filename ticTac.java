
class Move {
    public int row;
    public int column;
}

public class ticTac {

    public static char[][] board = {{' ', ' ', ' '}, 
                                    {' ', ' ', ' '},
                                     {' ', ' ', ' '}
                                    };

    public static final char HUMAN = 'X';
    public static final char AI = 'O';
    public static final int WIN_STATE = 2,
                            LOSE_STATE = -2,
                            DRAW_STATE = 1,
                            NOT_FINISHED_STATE = 0;


    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);
        int x, y;
        while (checkGameState() == NOT_FINISHED_STATE) {              
            x = read.nextInt();
            y = read.nextInt();
            if (board[x][y] == ' ') 
            {
                board[x][y] = HUMAN;
                displayBoard();

                if (checkGameState() != NOT_FINISHED_STATE) {
                    break;
                }

                Move best = findBestMove();
                System.out.println("bestMove: " + best.row + " row, " + best.column + " col");
                board[best.row][best.column] = AI;
                displayBoard();
            } 
            else {
                System.out.println("you can't write on this cell, it is already filled");
            }

        }
        int gameFinalState = checkGameState();
        if (gameFinalState == DRAW_STATE) {
            System.out.println("It is Draw !");
        } 
        else if (gameFinalState == LOSE_STATE) {
            System.out.println("The Winner is X !");
        } 
        else {
            System.out.println("The winner is O !");
        }
    }

//////////////////////////////////////////////////////////////////////

    static Move findBestMove() {
        int bestVal = Integer.MIN_VALUE;
        Move bestMove = new Move();
        bestMove.row = -1;
        bestMove.column = -1;

        /*  
        for each child of this this state 
        evaluate the outcome of going in this pass
        then get the best one of them
        */

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Check if cell is empty
                if (board[i][j] == ' ') {

                    // Make the move

                    board[i][j] = AI;
                    /* compute evaluation function for this move*/

                    int moveVal = minimax(100, false);

                    // Undo after modifing the original board

                    board[i][j] = ' '; 

                    if (moveVal > bestVal) {
                        bestMove.row = i;
                        bestMove.column = j;
                        bestVal = moveVal;
                    }
                }
            }
        }       
        return bestMove;
    }

    public static int minimax(int depth, boolean isMaximizing) {
        int gameState = checkGameState();

        if (gameState != NOT_FINISHED_STATE || depth == 0) {
            return gameState;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Is the cell available?

                    if (board[i][j] == ' ') {
                        board[i][j] = AI;
                        int score = minimax(depth - 1, false);
                        board[i][j] = ' ';
                        bestScore = Integer.max(score, bestScore);
                    }
                }
            }

            return bestScore;
        } 
        else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Is the cell available?
                    if (board[i][j] == ' ') {
                        board[i][j] = HUMAN;
                        int score = minimax(depth - 1, true);
                        board[i][j] = ' ';
                        bestScore = Integer.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }
/* ****************************************************************** */
    public static boolean equals3(char a, char b, char c) {
        return a == b && b == c && a != ' ';
    }

    public static int checkGameState() {

        int gameState = NOT_FINISHED_STATE;
        
        /* winning from first diagonal */

        if (equals3(board[0][0], board[1][1], board[2][2])) {
            gameState = board[0][0] == AI ? WIN_STATE : LOSE_STATE;
        }
        /* winning from second diagonal */

        if (equals3(board[0][2], board[1][1], board[2][0])) {
            gameState = board[0][2] == AI ? WIN_STATE : LOSE_STATE;
        }
        /* wining from rows */

        for (int i = 0; i < 3; i++) {
            if (equals3(board[i][0], board[i][1], board[i][2])) {
                gameState = board[i][0] == AI ? WIN_STATE : LOSE_STATE;
            }
        }
        /* wining from columns */

        for (int i = 0; i < 3; i++) {
            if (equals3(board[0][i], board[1][i], board[2][i])) {
                gameState = board[0][i] == AI ? WIN_STATE : LOSE_STATE;
            }

        }  
              
        int emptyCells = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    emptyCells++;
                }
            }
        }
        /* check if it is draw */

        if (gameState == NOT_FINISHED_STATE && emptyCells == 0) {
            gameState = DRAW_STATE;
        }
        return gameState;
    }

    public static void displayBoard() {
        System.out.println();
        System.out.print("-------\n|");
        String printedSymbols = "";
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                switch (board[i][j]) {
                    case ' ':
                        printedSymbols = " " + "|";
                        break;
                    case HUMAN:
                        printedSymbols = "X" + "|";
                        break;
                    case AI:
                        printedSymbols = "O" + "|";
                }
                System.out.print(printedSymbols);
            }
            System.out.print("\n-------\n|");
        }
    }
}