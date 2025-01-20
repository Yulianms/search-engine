package cards.poketracker.searchengine.wishlist;

import cards.poketracker.searchengine.card.tracker.CardTracker;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Wishlist {

    @Id
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL ,mappedBy = "wishlist")
    private User user;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "wishlist")
    private List<CardTracker> cards;

}
