package cards.poketracker.searchengine.card.tracker.converter;

import cards.poketracker.searchengine.card.converter.CardToCardDtoConverter;
import cards.poketracker.searchengine.card.tracker.CardTracker;
import cards.poketracker.searchengine.card.tracker.dto.CardTrackerDTO;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

@Component
public class CardTrackerToCardTrackerDtoConverter implements Converter<CardTracker, CardTrackerDTO> {

    private final CardToCardDtoConverter cardToCardDtoConverter;

    public CardTrackerToCardTrackerDtoConverter(CardToCardDtoConverter cardToCardDtoConverter) {
        this.cardToCardDtoConverter = cardToCardDtoConverter;
    }

    @Override
    public CardTrackerDTO convert(CardTracker cardTracker) {
        return new CardTrackerDTO(cardTracker.getId(),
                cardTracker.getTargetPrice(),
                cardTracker.getActive(),
                cardToCardDtoConverter.convert(cardTracker.getCard()));
    }

}
