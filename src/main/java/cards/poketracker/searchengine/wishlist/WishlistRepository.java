package cards.poketracker.searchengine.wishlist;

import cards.poketracker.searchengine.pokeuser.PokeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

    public Wishlist findByUser(PokeUser user);

}
