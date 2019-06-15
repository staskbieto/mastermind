package two.buttons.prove.mastermind.model.DTO;

import lombok.Getter;
import two.buttons.prove.mastermind.beans.ColorEnum;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
public class GuessRequest {

    @Size(min=4, max=4)
    private List<ColorEnum> guess;

    private Long gameKey;
}
