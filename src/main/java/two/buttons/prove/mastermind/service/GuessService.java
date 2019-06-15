package two.buttons.prove.mastermind.service;

import two.buttons.prove.mastermind.beans.ColorEnum;
import two.buttons.prove.mastermind.model.DTO.GuessResponse;
import two.buttons.prove.mastermind.model.Game;
import two.buttons.prove.mastermind.model.Guess;

import java.util.List;

public interface GuessService {
    GuessResponse builGuessResponse(Game game, Guess guess);

    Guess buildGuess(List<ColorEnum> guessCode, List<ColorEnum> code);

}
