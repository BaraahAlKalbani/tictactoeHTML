package com.TicTacToeBot.TicTacToeBot.app.Models;

public class BotMoveResponse {
    private final int botMove;

    /**
     * Constructor for the bot move response.
     * @param botMove The index of the cell where the bot moved.
     */
    public BotMoveResponse(int botMove) {
        this.botMove = botMove;
    }

    /**
     * Returns the index of the cell where the bot moved.
     * @return The index of the cell where the bot moved.
     */
    public int getBotMove() {
        return botMove;
    }
}
