package two.buttons.prove.mastermind.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import two.buttons.prove.mastermind.exceptions.GameFinishedException;
import two.buttons.prove.mastermind.exceptions.GameNotFoundException;
import two.buttons.prove.mastermind.model.DTO.GuessRequest;
import two.buttons.prove.mastermind.model.DTO.GuessResponse;
import two.buttons.prove.mastermind.model.Guess;
import two.buttons.prove.mastermind.service.GameService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class GameController {

    private final GameService gameService;

    @GetMapping(value = "/init")
    public ResponseEntity<Long> initGame() {

        return new ResponseEntity<>(gameService.initGame(), HttpStatus.CREATED);

    }

    @PostMapping(value = "/guess")
    public ResponseEntity<GuessResponse> guess(
            @RequestBody GuessRequest guessRequest){

        try {

            return new ResponseEntity<>(gameService.guess(guessRequest), HttpStatus.OK);

        } catch (GameNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } catch (GameFinishedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

    }

    @GetMapping(value = "/historic/{gameKey}")
    public ResponseEntity<List<Guess>> historic(
            @PathVariable("gameKey") Long gameKey
    ){
        try {

            return new ResponseEntity<>(gameService.getHistoric(gameKey), HttpStatus.OK);
        } catch (GameNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

    }


}
