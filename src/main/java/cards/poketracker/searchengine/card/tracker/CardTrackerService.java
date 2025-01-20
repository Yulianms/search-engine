package cards.poketracker.searchengine.card.tracker;

import org.springframework.stereotype.Service;

@Service
public class CardTrackerService {

    private final CardTrackerRepository cardTrackerRepository;

    public CardTrackerService(CardTrackerRepository cardTrackerRepository) {
        this.cardTrackerRepository = cardTrackerRepository;
    }

    public CardTracker save(CardTracker cardTracker) {
        return cardTrackerRepository.save(cardTracker);
    }

}
