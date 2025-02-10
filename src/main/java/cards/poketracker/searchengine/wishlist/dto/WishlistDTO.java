package cards.poketracker.searchengine.wishlist.dto;

import cards.poketracker.searchengine.card.tracker.dto.CardTrackerDTO;
import cards.poketracker.searchengine.pokeuser.dto.PokeUserDto;

import java.util.List;

public record WishlistDTO(Integer id,
                          PokeUserDto pokeUser,
                          List<CardTrackerDTO> cards) {
}
