package cards.poketracker.searchengine.pokeuser.converter;

import cards.poketracker.searchengine.card.Card;
import cards.poketracker.searchengine.card.dto.CardDto;
import cards.poketracker.searchengine.pokeuser.PokeUser;
import cards.poketracker.searchengine.pokeuser.dto.PokeUserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDtoConverter implements Converter<PokeUser, PokeUserDto> {

    @Override
    public PokeUserDto convert(PokeUser user) {
        return new PokeUserDto(user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.isEnabled(),
                user.getRoles(),
                user.getMemberSince());
    }

}
