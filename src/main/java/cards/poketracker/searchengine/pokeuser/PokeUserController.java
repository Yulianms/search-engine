package cards.poketracker.searchengine.pokeuser;

import cards.poketracker.searchengine.pokeuser.converter.UserDtoToUserConverter;
import cards.poketracker.searchengine.pokeuser.converter.UserToUserDtoConverter;
import cards.poketracker.searchengine.pokeuser.dto.PokeUserDto;
import cards.poketracker.searchengine.system.Result;
import cards.poketracker.searchengine.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PokeUserController {

    private final UserDtoToUserConverter userDtoToUserConverter;

    private final UserToUserDtoConverter userToUserDtoConverter;

    private final PokeUserService pokeUserService;

    public PokeUserController(UserDtoToUserConverter userDtoToUserConverter, UserToUserDtoConverter userToUserDtoConverter, PokeUserService pokeUserService) {
        this.userDtoToUserConverter = userDtoToUserConverter;
        this.userToUserDtoConverter = userToUserDtoConverter;
        this.pokeUserService = pokeUserService;
    }

    @GetMapping("${api.endpoint.base-url}/users")
    public Result findAllUsers() {
        List<PokeUser> foundUsers = this.pokeUserService.findAll();
        List<PokeUserDto> userDtos = foundUsers.stream()
                .map(this.userToUserDtoConverter::convert)
                .toList();
        return new Result(true, StatusCode.SUCCESS, "Find All Success", userDtos);
    }

    @GetMapping("${api.endpoint.base-url}/users/{userId}")
    public Result findUserById(@PathVariable Long userId) {
        PokeUser foundUser = this.pokeUserService.findById(userId);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", this.userToUserDtoConverter.convert(foundUser));
    }

    @PostMapping("${api.endpoint.base-url}/users")
    public Result createUser(@Valid @RequestBody PokeUser user) {
        this.pokeUserService.create(user);
        return new Result(true, StatusCode.SUCCESS, "Create Success", this.userToUserDtoConverter.convert(user));
    }

    @PutMapping("${api.endpoint.base-url}/users/{userId}")
    public Result updateUser(@PathVariable Long userId, @RequestBody PokeUserDto userDto) {
        PokeUser user = this.userDtoToUserConverter.convert(userDto);
        PokeUser updatedUser = this.pokeUserService.update(userId, user);
        return new Result(true, StatusCode.SUCCESS, "Update Success", this.userToUserDtoConverter.convert(updatedUser));
    }

    @DeleteMapping("${api.endpoint.base-url}/users/{userId}")
    public Result deleteUser(@PathVariable Long userId) {
        this.pokeUserService.delete(userId);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }

}
