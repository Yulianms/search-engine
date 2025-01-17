package cards.poketracker.searchengine.legality;

import cards.poketracker.searchengine.card.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LegalityRepository extends JpaRepository<Legality, Card> {
}
