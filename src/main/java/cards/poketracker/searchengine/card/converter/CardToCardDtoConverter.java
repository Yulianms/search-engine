package cards.poketracker.searchengine.card.converter;

import cards.poketracker.searchengine.card.Card;
import cards.poketracker.searchengine.card.dto.CardDto;
import cards.poketracker.searchengine.legality.converter.LegalityToLegalityDtoConverter;
import cards.poketracker.searchengine.set.converter.SetToSetDtoConverter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CardToCardDtoConverter implements Converter<Card, CardDto> {

    private final SetToSetDtoConverter setToSetDtoConverter;

    private final LegalityToLegalityDtoConverter legalityToLegalityDtoConverter;

    public CardToCardDtoConverter(SetToSetDtoConverter setToSetDtoConverter, LegalityToLegalityDtoConverter legalityToLegalityDtoConverter) {
        this.setToSetDtoConverter = setToSetDtoConverter;
        this.legalityToLegalityDtoConverter = legalityToLegalityDtoConverter;
    }

    @Override
    public CardDto convert(Card source) {
        return new CardDto(source.getId(),
                source.getName(),
                source.getCategory(),
                source.getSubtype(),
                source.getTypes(),
                source.getEvolvesFrom(),
                source.getRarity(),
                source.getPokedexNumber(),
                source.getSmImg(),
                source.getLgImg(),
                source.getCardNumber(),
                source.getSet() != null
                        ? this.setToSetDtoConverter.convert(source.getSet())
                        : null,
                source.getLegality() != null
                        ? this.legalityToLegalityDtoConverter.convert(source.getLegality())
                        : null);
    }

}
