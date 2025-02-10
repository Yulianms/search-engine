package cards.poketracker.searchengine.wishlist.converter;

import cards.poketracker.searchengine.card.tracker.CardTracker;
import cards.poketracker.searchengine.card.tracker.CardTrackerService;
import cards.poketracker.searchengine.card.tracker.converter.CardTrackerToCardTrackerDtoConverter;
import cards.poketracker.searchengine.card.tracker.dto.CardTrackerDTO;
import cards.poketracker.searchengine.pokeuser.PokeUser;
import cards.poketracker.searchengine.pokeuser.converter.UserToUserDtoConverter;
import cards.poketracker.searchengine.wishlist.Wishlist;
import cards.poketracker.searchengine.wishlist.dto.WishlistDTO;
import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

@Component
public class WishlistToWishlistDtoConverter implements Converter<Wishlist, WishlistDTO> {

    private final CardTrackerToCardTrackerDtoConverter cardTrackerToCardTrackerDtoConverter;

    private final UserToUserDtoConverter userToUserDtoConverter;

    public WishlistToWishlistDtoConverter(CardTrackerToCardTrackerDtoConverter cardTrackerToCardTrackerDtoConverter, UserToUserDtoConverter userToUserDtoConverter) {
        this.cardTrackerToCardTrackerDtoConverter = cardTrackerToCardTrackerDtoConverter;
        this.userToUserDtoConverter = userToUserDtoConverter;
    }

    @Override
    public WishlistDTO convert(Wishlist wishlist) {
        List<CardTrackerDTO> cardsTrackers = wishlist.getCards().stream()
                .map(this.cardTrackerToCardTrackerDtoConverter::convert).toList();
        return new WishlistDTO(wishlist.getId(), userToUserDtoConverter.convert(wishlist.getUser()), cardsTrackers);
    }
}
