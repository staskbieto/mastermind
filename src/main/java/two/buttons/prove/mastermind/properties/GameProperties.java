package two.buttons.prove.mastermind.properties;


import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Getter
@Configuration
public class GameProperties {

    @Value("${game.numberTries}")
    private Integer numberTries;
}
