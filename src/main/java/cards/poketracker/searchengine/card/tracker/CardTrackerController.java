package cards.poketracker.searchengine.card.tracker;

import cards.poketracker.searchengine.system.Result;
import cards.poketracker.searchengine.system.StatusCode;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardTrackerController {

    private final CardTrackerRepository cardTrackerRepository;

    public CardTrackerController(CardTrackerRepository cardTrackerRepository) {
        this.cardTrackerRepository = cardTrackerRepository;
    }

    public Result saveCardTracker(CardTracker cardTracker) {
        this.cardTrackerRepository.save(cardTracker);
        return new Result(true, StatusCode.SUCCESS, "Add Success", cardTracker);
    }

}
