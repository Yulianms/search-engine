package cards.poketracker.searchengine.set.converter;

import cards.poketracker.searchengine.set.Set;
import cards.poketracker.searchengine.set.dto.SetDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SetToSetDtoConverter implements Converter<Set, SetDto> {

    @Override
    public SetDto convert(Set source) {
        return new SetDto(source.getId(),
                                   source.getName(),
                                   source.getCode(),
                                   source.getSymbol(),
                                   source.getLogo(),
                                   source.getNumberOfCards());
    }

}
