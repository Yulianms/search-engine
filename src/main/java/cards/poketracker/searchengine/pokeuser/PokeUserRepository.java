package cards.poketracker.searchengine.pokeuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PokeUserRepository extends JpaRepository<PokeUser, Long> {

    Optional<PokeUser> findByUsername(String username);

}
