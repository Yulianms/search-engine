package cards.poketracker.searchengine.wishlist;

import cards.poketracker.searchengine.pokeuser.PokeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
    Optional<Wishlist> findByUser(PokeUser user);
}
