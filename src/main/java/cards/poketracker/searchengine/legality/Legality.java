package cards.poketracker.searchengine.legality;

import cards.poketracker.searchengine.card.Card;
import jakarta.persistence.*;

@Entity
@Table(name = "legalities")
public class Legality {

    @Id
    private Integer id;

    @OneToOne
    @JoinColumn(name = "card_id")
    private Card card;

    private Boolean unlimited;

    private Boolean standard;

    private Boolean expanded;

    public Legality() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Boolean getUnlimited() {
        return unlimited;
    }

    public void setUnlimited(Boolean unlimited) {
        this.unlimited = unlimited;
    }

    public Boolean getStandard() {
        return standard;
    }

    public void setStandard(Boolean standard) {
        this.standard = standard;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }
}
