package cards.poketracker.searchengine.legality.converter;

import cards.poketracker.searchengine.legality.Legality;
import cards.poketracker.searchengine.legality.dto.LegalityDto;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

@Component
public class LegalityToLegalityDtoConverter implements Converter<Legality, LegalityDto> {

    @Override
    public LegalityDto convert(Legality source) {
        return new LegalityDto(source.getStandard(),
                               source.getExpanded(),
                               source.getUnlimited());
    }

}
