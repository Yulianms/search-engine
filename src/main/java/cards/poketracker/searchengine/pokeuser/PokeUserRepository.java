package cards.poketracker.searchengine.pokeuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokeUserRepository extends JpaRepository<PokeUser, Long> {
}
