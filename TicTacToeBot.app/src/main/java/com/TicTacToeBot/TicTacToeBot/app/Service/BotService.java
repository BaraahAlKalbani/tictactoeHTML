package com.TicTacToeBot.TicTacToeBot.app.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service

public class BotService {
    private static final Logger logger = LoggerFactory.getLogger(BotService.class);
    /**
     * Generates a random bot move based on the current state of the board.
     * @param board The current state of the Tic Tac Toe board.
     * @return An integer representing the bot's move index.
     */
    private int getRandomBotIndex(String[] board) {
        // Implement your bot logic here to generate a random bot index
        // based on the current state of the board
        Random random = new Random();
        int botIndex = random.nextInt(board.length);
        while (!board[botIndex].equals("")) {
            botIndex = random.nextInt(board.length);
        }
        logger.info("Bot Move: " + botIndex);
        return botIndex;
    }

    /**
     * Generates the best possible bot move based on the current state of the board.
     * @param board The current state of the Tic Tac Toe board.
     * @return An integer representing the bot's move index.
     */
    public static int getBestBotIndex(String[] board) {
        // Define the scoring system
        int[] scores = {0, 1, 10, 100}; // The higher the score, the better the move
        // The scores array assumes that the board has four spaces: "", "X", "O", and "B" (blocked)

        // Find all available moves
        List<Integer> availableMoves = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            if (board[i].equals("")) {
                availableMoves.add(i);
            }
        }

        // Evaluate each move
        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;
        for (int move : availableMoves) {
            int score = 0;
            // Check for potential wins
            String[] tempBoard = board.clone();
            tempBoard[move] = "O"; // Assume the bot makes this move
            if (isWinner(tempBoard, "O")) {
                score += scores[3]; // The highest score for an immediate win
            }
            // Check for potential losses
            tempBoard[move] = "X"; // Assume the opponent makes this move
            if (isWinner(tempBoard, "X")) {
                score += scores[2]; // A high score for blocking the opponent's win
            }
            // Add a random factor to encourage variety
            score += scores[1] * new Random().nextFloat();
            // Update the best move if necessary
            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }
        }
        logger.info("Bot Move: " + bestMove);
        return bestMove;
    }
    /**
     * Checks if the given player has won the game on the current board.
     * @param board the current state of the game board
     * @param player the player to check for winning
     */
    private static boolean isWinner(String[] board, String player) {
        // Check all possible winning combinations
        return (board[0].equals(player) && board[1].equals(player) && board[2].equals(player))
                || (board[3].equals(player) && board[4].equals(player) && board[5].equals(player))
                || (board[6].equals(player) && board[7].equals(player) && board[8].equals(player))
                || (board[0].equals(player) && board[3].equals(player) && board[6].equals(player))
                || (board[1].equals(player) && board[4].equals(player) && board[7].equals(player))
                || (board[2].equals(player) && board[5].equals(player) && board[8].equals(player))
                || (board[0].equals(player) && board[4].equals(player) && board[8].equals(player))
                || (board[2].equals(player) && board[4].equals(player) && board[6].equals(player));
    }
}
