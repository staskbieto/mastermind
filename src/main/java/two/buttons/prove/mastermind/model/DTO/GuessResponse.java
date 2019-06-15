package two.buttons.prove.mastermind.model.DTO;

import lombok.*;
import two.buttons.prove.mastermind.model.Guess;

import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GuessResponse {

    @Builder.Default
    private int exact = 0;

    @Builder.Default
    private int near = 0;

    @Builder.Default
    private Boolean isFinished = false;

    @Builder.Default
    private Integer triesDone = 0;

    @Builder.Default
    private Integer numberTries = 0;

    @Builder.Default
    private List<Guess> historic = Collections.emptyList();

}
