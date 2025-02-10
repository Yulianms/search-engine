package cards.poketracker.searchengine.wishlist;

import cards.poketracker.searchengine.pokeuser.PokeUser;
import cards.poketracker.searchengine.pokeuser.PokeUserService;
import cards.poketracker.searchengine.system.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    private final PokeUserService pokeUserService;

    public WishlistService(WishlistRepository wishlistRepository, PokeUserService pokeUserService) {
        this.wishlistRepository = wishlistRepository;
        this.pokeUserService = pokeUserService;
    }

    public Wishlist findByUsername(String username) {
        // Fetches the authenticated user
        PokeUser user = this.pokeUserService.findByUsername(username);

        return this.wishlistRepository.findByUser(user)
                .orElseThrow( () -> new ObjectNotFoundException("wishlist"));
    }
}
