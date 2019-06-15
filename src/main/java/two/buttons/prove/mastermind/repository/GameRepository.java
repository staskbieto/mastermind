package two.buttons.prove.mastermind.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import two.buttons.prove.mastermind.model.Game;

import java.util.Optional;


@Repository
public interface GameRepository extends MongoRepository<Game, Long> {

    public Optional<Game> findByKey(Long gameKey);

}
