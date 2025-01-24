package cards.poketracker.searchengine.pokeuser.converter;

import cards.poketracker.searchengine.pokeuser.PokeUser;
import cards.poketracker.searchengine.pokeuser.dto.PokeUserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoToUserConverter implements Converter<PokeUserDto, PokeUser> {

    @Override
    public PokeUser convert(PokeUserDto dto) {
        PokeUser user = new PokeUser();
        user.setId(dto.id());
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setEnabled(dto.active());
        user.setRoles(dto.roles());
        user.setMemberSince(dto.memberSince());
        return user;
    }

}
