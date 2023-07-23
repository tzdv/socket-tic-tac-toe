package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Player {
    private String symbol;
    private BufferedWriter out;
    public BufferedReader in;

    public Player(Socket player) {
        try {
            this.out = new BufferedWriter(new OutputStreamWriter(player.getOutputStream()));
            this.in = new BufferedReader(new InputStreamReader(player.getInputStream()));
        } catch (IOException var3) {
            throw new RuntimeException(var3);
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

    public void sendBoard(String[][] board) {
        sendMessage("BOARD" + board[0][0] + " | " + board[0][1] + " | " + board[0][2]);
        sendMessage("BOARD----------");
        sendMessage("BOARD" + board[1][0] + " | " + board[1][1] + " | " + board[1][2]);
        sendMessage("BOARD----------");
        sendMessage("BOARD" + board[2][0] + " | " + board[2][1] + " | " + board[2][2]);
    }

    public String readMessage() {
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getSymbol() {

        return this.symbol;
    }

    public void setSymbol(String symbol) {

        this.symbol = symbol;
    }
}
