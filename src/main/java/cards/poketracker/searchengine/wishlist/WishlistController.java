package cards.poketracker.searchengine.wishlist;

import cards.poketracker.searchengine.pokeuser.PokeUser;
import cards.poketracker.searchengine.system.Result;
import cards.poketracker.searchengine.system.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WishlistController {

    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @GetMapping("/api/v1/wishlist/{user}")
    public Result findWishlistByUser(@PathVariable PokeUser user) {
        return null;
    }

}
