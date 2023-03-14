package com.TicTacToeBot.TicTacToeBot.app.Models;
/**
 * A class representing the Tic-Tac-Toe board.
 */
public class Board {
    //An array of strings representing the Tic-Tac-Toe board.
    String[] board;

    /**
     * Constructs an empty Tic-Tac-Toe board.
     */
    public Board() {
    }

    /**
     * Gets the current state of the Tic-Tac-Toe board.
     *
     * @return the current state of the Tic-Tac-Toe board
     */
    public String[] getBoard() {
        return board;
    }

    /**
     * Sets the state of the Tic-Tac-Toe board.
     *
     * @param board the new state of the Tic-Tac-Toe board
     */
    public void setBoard(String[] board) {
        this.board = board;
    }
}