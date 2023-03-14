package com.TicTacToeBot.TicTacToeBot.app.Controllers;


import com.TicTacToeBot.TicTacToeBot.app.Models.Board;
import com.TicTacToeBot.TicTacToeBot.app.Models.BotMoveResponse;
import com.TicTacToeBot.TicTacToeBot.app.Service.BotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * Controller class for a Tic Tac Toe bot.
 */
@RestController
@RequestMapping("/api/bot")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BotController {
    @Autowired
    private BotService botService;

    /**
     * Handles HTTP POST requests to the /api/bot/make-move endpoint.
     * Generates a bot move based on the current state of the board.
     * @param board The current state of the Tic Tac Toe board.
     * @return A BotMoveResponse object representing the bot's move.
     */
    @PostMapping("/make-move")
    public BotMoveResponse makeMove(@RequestBody Board board) {
        int botIndex = BotService.getBestBotIndex(board.getBoard());
        BotMoveResponse response = new BotMoveResponse(botIndex);
        return response;
    }
}

