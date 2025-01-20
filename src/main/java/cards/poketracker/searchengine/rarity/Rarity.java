package cards.poketracker.searchengine.rarity;

import cards.poketracker.searchengine.card.Card;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rarities")
public class Rarity {

    @Id
    private Integer id;

    private String name;

    private Integer rarity;

    public Rarity() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRarity() {
        return rarity;
    }

    public void setRarity(Integer rarity) {
        this.rarity = rarity;
    }
}
