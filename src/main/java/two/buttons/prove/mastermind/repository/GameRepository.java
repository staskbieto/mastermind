package two.buttons.prove.mastermind.repository;

import org.springframework.stereotype.Repository;
import two.buttons.prove.mastermind.model.Game;

import org.springframework.data.mongodb.repository.MongoRepository;


@Repository
public interface GameRepository extends MongoRepository<Game, Long> {

}
