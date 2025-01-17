package cards.poketracker.searchengine.card;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card findById(String id) {
        return this.cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException(id));
    }

}
