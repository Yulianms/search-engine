package cards.poketracker.searchengine.security;

import cards.poketracker.searchengine.pokeuser.PokeUser;
import cards.poketracker.searchengine.pokeuser.UserPrincipal;
import cards.poketracker.searchengine.pokeuser.converter.UserToUserDtoConverter;
import cards.poketracker.searchengine.pokeuser.dto.PokeUserDto;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final JwtProvider jwtProvider;

    private final UserToUserDtoConverter userToUserDtoConverter;

    public AuthService(JwtProvider jwtProvider, UserToUserDtoConverter userToUserDtoConverter) {
        this.jwtProvider = jwtProvider;
        this.userToUserDtoConverter = userToUserDtoConverter;
    }

    public Map<String, Object> createLoginInfo(Authentication authentication) {
        // Creates user's information.
        UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();
        PokeUser pokeUser = userPrincipal.getPokeUser();
        PokeUserDto userDto = this.userToUserDtoConverter.convert(pokeUser);

        // Create a JWT.
        String token = this.jwtProvider.createToken(authentication);

        Map<String, Object> loginInfoMap = new HashMap<>();

        loginInfoMap.put("userInfo", userDto);
        loginInfoMap.put("token", token);

        return loginInfoMap;
    }
}
