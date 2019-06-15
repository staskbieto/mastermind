package two.buttons.prove.mastermind.service;
import org.springframework.stereotype.Service;
import two.buttons.prove.mastermind.beans.ColorEnum;
import two.buttons.prove.mastermind.exceptions.GameFinishedException;
import two.buttons.prove.mastermind.exceptions.GameNotFoundException;
import two.buttons.prove.mastermind.model.DTO.GuessRequest;
import two.buttons.prove.mastermind.model.DTO.GuessResponse;
import two.buttons.prove.mastermind.model.Game;
import two.buttons.prove.mastermind.model.Guess;
import two.buttons.prove.mastermind.properties.GameProperties;
import two.buttons.prove.mastermind.repository.GameRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class GameServiceImpl implements GameService {



    private final GuessService guessService;
    private final GameRepository gameRepository;
    private final GameProperties gameProperties;

    public GameServiceImpl(GuessService guessService, GameRepository gameRepository, GameProperties gameProperties) {
        this.guessService = guessService;
        this.gameRepository = gameRepository;
        this.gameProperties = gameProperties;
    }


    @Override
    public Long initGame() {

        Game createdGame = Game.builder()
                .key(generateGameId())
                .code(generateRandomCode())
                .numberTries(gameProperties.getNumberTries())
                .build()
                ;

        gameRepository.save(createdGame);

        return createdGame.getKey();
    }

    @Override
    public GuessResponse guess(GuessRequest guessRequest) throws GameNotFoundException, GameFinishedException {
        Game game  = gameRepository.findByKey(guessRequest.getGameKey())
                .orElseThrow(GameNotFoundException::new);

        if(game.getIsFinished()){
            throw new GameFinishedException();
        }

        Guess guess = guessService.buildGuess(guessRequest.getGuess(),game.getCode());

        if(guess.getExact() == game.getCode().size()){
            game.setIsFinished(true);
        }

        game.getHistoric().add(guess);
        game.setTriesDone(game.getTriesDone()+1);
        gameRepository.save(game);

        return guessService.builGuessResponse(game,guess);
    }

    @Override
    public Long generateGameId(){
        Random randomGenerator = new Random();
        return System.currentTimeMillis()+randomGenerator.nextLong();
    }

    @Override
    public List<ColorEnum> generateRandomCode(){
        List<ColorEnum> colorCode = new ArrayList<>();

        for(int i=0;i<=4;i++){
            colorCode.add(ColorEnum.getRandomColor());
        }
        return colorCode;
    }

}
