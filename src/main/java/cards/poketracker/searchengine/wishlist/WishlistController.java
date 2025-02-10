package cards.poketracker.searchengine.wishlist;

import cards.poketracker.searchengine.pokeuser.PokeUser;
import cards.poketracker.searchengine.system.Result;
import cards.poketracker.searchengine.system.StatusCode;
import cards.poketracker.searchengine.wishlist.converter.WishlistToWishlistDtoConverter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WishlistController {

    private final WishlistService wishlistService;

    private final WishlistToWishlistDtoConverter wishlistToWishlistDtoConverter;

    public WishlistController(WishlistService wishlistService, WishlistToWishlistDtoConverter wishlistToWishlistDtoConverter) {
        this.wishlistService = wishlistService;
        this.wishlistToWishlistDtoConverter = wishlistToWishlistDtoConverter;
    }

    @GetMapping("/api/v1/wishlist")
    public Result findWishlistByUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Wishlist wl = this.wishlistService.findByUsername(username);

        return new Result(true, StatusCode.SUCCESS, "Wishlist Found", this.wishlistToWishlistDtoConverter.convert(wl));
    }

}
