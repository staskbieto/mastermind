package two.buttons.prove.mastermind.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {


    @GetMapping(value = "/init")
    public Long initGame() {

        return 0L;
    }

}
