package com.TicTacToeBot.TicTacToeBot.app.Controllers;


import com.TicTacToeBot.TicTacToeBot.app.Models.Board;
import com.TicTacToeBot.TicTacToeBot.app.Models.BotMoveResponse;
import com.TicTacToeBot.TicTacToeBot.app.Service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public BotMoveResponse makeHardBotMove(@RequestBody Board board) {
        int botIndex = BotService.getRandomBotIndex(board.getBoard());
        BotMoveResponse response = new BotMoveResponse(botIndex);
        return response;
    }
    @PostMapping("/make-move/hard")
    public BotMoveResponse makeEasyBotMove(@RequestBody Board board) {
        int botIndex = BotService.getBestBotIndex(board.getBoard());
        BotMoveResponse response = new BotMoveResponse(botIndex);
        return response;
    }

}

