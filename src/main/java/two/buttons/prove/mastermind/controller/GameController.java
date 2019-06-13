package two.buttons.prove.mastermind.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import two.buttons.prove.mastermind.model.Game;

@RestController
public class GameController {


    @GetMapping(value = "/init")
    public Game initGame() {

        return Game.builder().build();
    }

}
