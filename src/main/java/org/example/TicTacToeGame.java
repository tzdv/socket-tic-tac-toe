package org.example;

import java.io.IOException;

public class TicTacToeGame {
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private String[][] board;
    private int moves;
    private boolean gameOver;

    public TicTacToeGame(Player player1, Player player2) throws IOException {
        player1.setSymbol("X");
        player2.setSymbol("O");
        this.player1 = player1;
        this.player2 = player2;

    }

    public void start() {
        board = new String[][]{
                {" ", " ", " "},
                {" ", " ", " "},
                {" ", " ", " "}};
        player1.sendMessage("START " + player1.getSymbol());
        player2.sendMessage("START " + player2.getSymbol());

        currentPlayer = player1;
        player1.sendBoard(board);
        player2.sendBoard(board);
        while (true){

            // Get the move from the current player
            currentPlayer.sendMessage("MOVE");
            String currentPlayerMove = currentPlayer.readMessage();
            while(!isMoveValid(currentPlayerMove)){
                currentPlayer.sendMessage("AGAIN");
                currentPlayerMove = currentPlayer.readMessage();
            }
            makeMove(currentPlayerMove);
            moves++;
            System.out.println("Player " + currentPlayer.getSymbol() + " made a move.");
            player1.sendBoard(board);
            player2.sendBoard(board);
            if (checkForWin()) {
                player1.sendMessage("WIN " + currentPlayer.getSymbol());
                player2.sendMessage("WIN " + currentPlayer.getSymbol());
                break;
            }
            else if ((checkForTie())){
                player1.sendMessage("TIE");
                player2.sendMessage("TIE");
                break;
            }
            switchPlayers();
        }


    }

    private boolean isMoveValid(String playerMove) {
        int playerMoveInt = Integer.parseInt(playerMove);
        int row = (playerMoveInt - 1) / 3;
        int col = (playerMoveInt - 1) % 3;
        return board[row][col].equals(" ");



    }

    private void makeMove(String playerMove) {
        int playerMoveInt = Integer.parseInt(playerMove);
        int row = (playerMoveInt - 1) / 3;
        int col = (playerMoveInt - 1) % 3;
        board[row][col] = currentPlayer.getSymbol();
    }

    private boolean checkForWin() {
        for (int i = 0; i <= 2; i++) {
            if (!board[i][0].equals(" ") && board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2]))
                return true;

            if (!board[0][i].equals(" ") && board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i]))
                return true;
        }
        // Check diagonals
        if (!board[0][0].equals(" ") && board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]))
            return true;

        if (!board[0][2].equals(" ") && board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]))
            return true;
        return false;
    }

    private boolean checkForTie() {
        return moves == 9 && !checkForWin();

    }

    private void switchPlayers() {
        if(currentPlayer.getSymbol().equals(player1.getSymbol()))
            currentPlayer = player2;
        else
            currentPlayer = player1;

    }
}
