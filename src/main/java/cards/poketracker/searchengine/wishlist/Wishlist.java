package cards.poketracker.searchengine.wishlist;

import cards.poketracker.searchengine.card.tracker.CardTracker;
import cards.poketracker.searchengine.pokeuser.PokeUser;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Wishlist {

    @Id
    private Integer id;

    @OneToOne
    @JoinColumn(name = "owned_by")
    private PokeUser user;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "wishlist")
    private List<CardTracker> cards;

}
