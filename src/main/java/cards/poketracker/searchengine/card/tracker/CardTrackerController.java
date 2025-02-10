package cards.poketracker.searchengine.card.tracker;

import cards.poketracker.searchengine.card.tracker.converter.CardTrackerToCardTrackerDtoConverter;
import cards.poketracker.searchengine.system.Result;
import cards.poketracker.searchengine.system.StatusCode;
import org.springframework.web.bind.annotation.*;

@RestController
public class CardTrackerController {

    private final CardTrackerService cardTrackerService;

    private final CardTrackerToCardTrackerDtoConverter cardTrackerToCardTrackerDtoConverter;

    public CardTrackerController(CardTrackerService cardTrackerService, CardTrackerToCardTrackerDtoConverter cardTrackerToCardTrackerDtoConverter) {
        this.cardTrackerService = cardTrackerService;
        this.cardTrackerToCardTrackerDtoConverter = cardTrackerToCardTrackerDtoConverter;
    }

    @PostMapping("${api.endpoint.base-url}/wishlist/cards")
    public Result saveCardTracker(@RequestBody CardTracker cardTracker) {
        this.cardTrackerService.save(cardTracker);
        return new Result(true, StatusCode.SUCCESS, "Add Success", cardTrackerToCardTrackerDtoConverter.convert(cardTracker));
    }

    @PutMapping("${api.endpoint.base-url}/wishlist/cards/{trackerId}")
    public Result updateCardTracker(@PathVariable int trackerId, @RequestBody CardTracker update) {
        CardTracker updatedCardTracker = this.cardTrackerService.update(trackerId, update);
        return new Result(true, StatusCode.SUCCESS, "Update Success", cardTrackerToCardTrackerDtoConverter.convert(updatedCardTracker));
    }

    @DeleteMapping("${api.endpoint.base-url}/wishlist/cards/{trackerId}")
    public Result deleteCardTracker(@PathVariable int trackerId) {
        this.cardTrackerService.delete(trackerId);
        return new Result(true, StatusCode.SUCCESS, "Delete Success", trackerId);
    }
}
