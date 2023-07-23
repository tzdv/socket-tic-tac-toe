package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TicTacToeServer {
    public TicTacToeServer() {
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(12342);
            System.out.println("Server started. Waiting for clients...");
            Socket client1 = serverSocket.accept();
            Player player1 = new Player(client1);
            System.out.println("Player 1 connected.");
            Socket client2 = serverSocket.accept();
            Player player2 = new Player(client2);
            System.out.println("Player 2 connected.");
            TicTacToeGame game = new TicTacToeGame(player1, player2);
            game.start();
            System.out.println("closing the server.");
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        TicTacToeServer server = new TicTacToeServer();
        server.start();
    }
}

