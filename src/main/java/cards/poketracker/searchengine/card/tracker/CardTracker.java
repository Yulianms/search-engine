package cards.poketracker.searchengine.card.tracker;

import cards.poketracker.searchengine.card.Card;
import cards.poketracker.searchengine.wishlist.Wishlist;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class CardTracker {

    @Id
    private Integer id;

    private Float targetPrice;

    private Boolean active;

    @ManyToOne
    private Card card;

    @ManyToOne
    private Wishlist wishlist;

    public CardTracker() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(Float targetPrice) {
        this.targetPrice = targetPrice;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Wishlist getWishlist() {
        return wishlist;
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }
}
