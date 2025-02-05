package cards.poketracker.searchengine.security;

import cards.poketracker.searchengine.system.Result;
import cards.poketracker.searchengine.system.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.endpoint.base-url}/users")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Result getLoginInfo(Authentication authentication) {
        LOGGER.debug("Authentication : {}", authentication.getName());
        return new Result(true, StatusCode.SUCCESS, "User Info an JSON Web Token", this.authService.createLoginInfo(authentication));
    }

}
