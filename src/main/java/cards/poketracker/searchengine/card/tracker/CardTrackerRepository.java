package cards.poketracker.searchengine.card.tracker;

import cards.poketracker.searchengine.wishlist.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardTrackerRepository extends JpaRepository<CardTracker, Integer> {
}
