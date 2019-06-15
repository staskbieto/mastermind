package two.buttons.prove.mastermind.service;

import org.springframework.stereotype.Service;
import two.buttons.prove.mastermind.beans.ColorEnum;
import two.buttons.prove.mastermind.model.DTO.GuessResponse;
import two.buttons.prove.mastermind.model.Game;
import two.buttons.prove.mastermind.model.Guess;

import java.util.ArrayList;
import java.util.List;

@Service
public class GuessServiceImpl implements GuessService {

    @Override
    public GuessResponse builGuessResponse(Game game, Guess guess){
        return GuessResponse.builder()
                .exact(guess.getExact())
                .near(guess.getNear())
                .isFinished(game.getIsFinished())
                .triesDone(game.getNumberTries())
                .numberTries(game.getNumberTries())
                .historic(game.getHistoric())
                .build();
    }



    @Override
    public Guess buildGuess(List<ColorEnum> guessCode, List<ColorEnum> code) {


        List<ColorEnum> notExactMatchGuess = new ArrayList<>();
        List<ColorEnum> notExactMatchCode = new ArrayList<>();

        int exactMatches = 0;
        for (int i = 0; i < code.size(); i++) {
            if(code.get(i).equals(guessCode.get(i))) {
                exactMatches += 1;
            }else{
                notExactMatchGuess.add(guessCode.get(i));
                notExactMatchCode.add(code.get(i));
            }

        }
        int nearMatches = 0;
        for(ColorEnum color : notExactMatchGuess){
            nearMatches += notExactMatchCode.contains(color)? 1 : 0;
        }


        return Guess.builder()
                .guess(guessCode)
                .exact(exactMatches)
                .near(nearMatches)
                .build();
    }



}
