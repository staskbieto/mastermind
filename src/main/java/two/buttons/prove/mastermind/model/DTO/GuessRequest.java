package two.buttons.prove.mastermind.model.DTO;

import two.buttons.prove.mastermind.beans.ColorEnum;

import javax.validation.constraints.Size;
import java.util.List;

public class GuessRequest {

    @Size(min=4, max=4)
    private List<ColorEnum> guess;

    private Long gameKey;
}
