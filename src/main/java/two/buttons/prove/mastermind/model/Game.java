package two.buttons.prove.mastermind.model;
import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Game {

    @Id
    public String id;


}
