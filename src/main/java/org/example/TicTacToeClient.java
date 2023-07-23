package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class TicTacToeClient {
    Socket socket;
    BufferedReader in;
    BufferedWriter out;

    public TicTacToeClient(Socket socket) {
        this.socket = socket;
        try {
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        try {
            String msg = in.readLine();
            if (msg.startsWith("START")) {
                String player = msg.split(" ")[1];
                System.out.println("You are " + player);
            }

            Scanner scanner = new Scanner(System.in);

            while (true) {
                msg = in.readLine();
                String winner;
                if (msg.startsWith("BOARD")) {
                    String board = msg.substring(5);
                    System.out.println(board);
                }

                if (msg.startsWith("MOVE")) {
                    System.out.print("Enter your move (1-9): ");
                    String move = scanner.nextLine();
                    while (!isMoveValid(move)) {
                        System.out.print("Not a valid move, enter your move (1-9): ");
                        move = scanner.nextLine();
                    }
                    sendMessage(move);
                }
                else if (msg.startsWith("AGAIN")) {
                        System.out.print("Place is occupied, enter your move (1-9): ");
                        String move = scanner.nextLine();
                        sendMessage(move);
                    }
                else if (msg.startsWith("WIN")) {
                    winner = msg.split(" ")[1];
                    System.out.println("Player " + winner + " wins!");
                    break;
                }

                else if (msg.startsWith("TIE")) {
                    System.out.println("It's a tie!");
                    break;
                }
                }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendMessage(String message) {
        try {
            out.write(message);
            out.newLine();
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isMoveValid(String playerMove) {
        try {
            int number = Integer.parseInt(playerMove);
            return number >= 1 && number <= 9;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12342);
            TicTacToeClient client = new TicTacToeClient(socket);
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}