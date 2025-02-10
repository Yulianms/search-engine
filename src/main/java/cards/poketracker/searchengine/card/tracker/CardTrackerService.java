package cards.poketracker.searchengine.card.tracker;

import cards.poketracker.searchengine.system.exception.ObjectNotFoundException;
import cards.poketracker.searchengine.wishlist.Wishlist;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardTrackerService {

    private final CardTrackerRepository cardTrackerRepository;

    public CardTrackerService(CardTrackerRepository cardTrackerRepository) {
        this.cardTrackerRepository = cardTrackerRepository;
    }

    public CardTracker save(CardTracker cardTracker) {
        return cardTrackerRepository.save(cardTracker);
    }

    public CardTracker update(int id, CardTracker update) {
        return this.cardTrackerRepository.findById(id)
                .map( cardTracker -> {
                    cardTracker.setCard(update.getCard());
                    cardTracker.setActive(update.getActive());
                    cardTracker.setTargetPrice(update.getTargetPrice());
                    return this.cardTrackerRepository.save(cardTracker);
                }).orElseThrow( () -> new ObjectNotFoundException("Tracker", id));
    }

    public void delete(int id) {
        this.cardTrackerRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Card", id));
        this.cardTrackerRepository.deleteById(id);
    }
}
