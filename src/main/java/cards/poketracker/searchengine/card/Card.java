package cards.poketracker.searchengine.card;

import cards.poketracker.searchengine.legality.Legality;
import cards.poketracker.searchengine.rarity.Rarity;
import cards.poketracker.searchengine.set.Set;
import jakarta.persistence.*;

@Entity
@Table(name = "cards")
public class Card {

    @Id
    private String id;

    private String name;

    private String category;

    private String subtype;

    private Integer healthPoints;

    private String types;

    private String evolvesFrom;

    private Boolean retreatCost;

    @ManyToOne
    private Set set;

    @ManyToOne
    private Rarity rarity;

    private Integer pokedexNumber;

    private String smImg;

    private String lgImg;

    private Boolean abilities;

    private Integer cardNumber;

    private String resistances;

    private String weaknesses;

    private String level;

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE},mappedBy = "card")
    private Legality legalities;

    public Card() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public Integer getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(Integer healthPoints) {
        this.healthPoints = healthPoints;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getEvolvesFrom() {
        return evolvesFrom;
    }

    public void setEvolvesFrom(String evolvesFrom) {
        this.evolvesFrom = evolvesFrom;
    }

    public Boolean getRetreatCost() {
        return retreatCost;
    }

    public void setRetreatCost(Boolean retreatCost) {
        this.retreatCost = retreatCost;
    }

    public Set getSet() {
        return set;
    }

    public void setSet(Set set) {
        this.set = set;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

    public Integer getPokedexNumber() {
        return pokedexNumber;
    }

    public void setPokedexNumber(Integer pokedexNumber) {
        this.pokedexNumber = pokedexNumber;
    }

    public String getSmImg() {
        return smImg;
    }

    public void setSmImg(String smImg) {
        this.smImg = smImg;
    }

    public String getLgImg() {
        return lgImg;
    }

    public void setLgImg(String lgImg) {
        this.lgImg = lgImg;
    }

    public Boolean getAbilities() {
        return abilities;
    }

    public void setAbilities(Boolean abilities) {
        this.abilities = abilities;
    }

    public Integer getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getResistances() {
        return resistances;
    }

    public void setResistances(String resistances) {
        this.resistances = resistances;
    }

    public String getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(String weaknesses) {
        this.weaknesses = weaknesses;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Legality getLegalities() {
        return legalities;
    }

    public void setLegalities(Legality legalities) {
        this.legalities = legalities;
    }
}
