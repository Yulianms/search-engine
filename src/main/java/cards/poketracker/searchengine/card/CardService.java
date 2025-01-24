package cards.poketracker.searchengine.card;

import cards.poketracker.searchengine.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card findById(String id) {
        return this.cardRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Card", id));
    }

    public List<Card> findAll() {
        return cardRepository.findAll();
    }

    public Card save(Card card) {
        return cardRepository.save(card);
    }

    public Card update(String cardId, Card updatedCard) {
        return this.cardRepository.findById(cardId)
                .map(oldCard -> {
                    oldCard.setName(updatedCard.getName());
                    oldCard.setCategory(updatedCard.getCategory());
                    oldCard.setHealthPoints(updatedCard.getHealthPoints());
                    return this.cardRepository.save(oldCard);
                })
                .orElseThrow(() -> new ObjectNotFoundException("Card", cardId));
    }
}
