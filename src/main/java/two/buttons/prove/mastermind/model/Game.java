package two.buttons.prove.mastermind.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import two.buttons.prove.mastermind.beans.ColorEnum;

import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document
public class Game {

    @Id
    private String id;

    @Indexed(unique = true)
    private Long key;

    @Builder.Default
    private Boolean isFinished = false;

    @Size(min=4, max=4)
    private List<ColorEnum> code;

    @Builder.Default
    private List<Guess> historic = Collections.emptyList();


}
