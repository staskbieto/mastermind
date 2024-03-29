package two.buttons.prove.mastermind.service;

import two.buttons.prove.mastermind.beans.ColorEnum;
import two.buttons.prove.mastermind.exceptions.GameFinishedException;
import two.buttons.prove.mastermind.exceptions.GameNotFoundException;
import two.buttons.prove.mastermind.model.DTO.GuessRequest;
import two.buttons.prove.mastermind.model.DTO.GuessResponse;
import two.buttons.prove.mastermind.model.Guess;

import java.util.List;

public interface GameService {

    public Long initGame();

    GuessResponse guess(GuessRequest guessRequest) throws GameNotFoundException, GameFinishedException;

    List<Guess> getHistoric(Long gameKey) throws GameNotFoundException;

    Long generateGameId();

    List<ColorEnum> generateRandomCode();
}
