package two.buttons.prove.mastermind.model;


import lombok.*;
import two.buttons.prove.mastermind.beans.ColorEnum;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Guess {

    @Size(min=4, max=4)
    private List<ColorEnum> guess;

    private int exact;

    private int near;
}
