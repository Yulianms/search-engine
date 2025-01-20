package cards.poketracker.searchengine.card;

public class CardNotFoundException extends RuntimeException {

    public CardNotFoundException(String id) {
        super("Could not find card with ID: " + id);
    }

}
