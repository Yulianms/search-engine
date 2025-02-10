package cards.poketracker.searchengine.card.tracker.dto;

import cards.poketracker.searchengine.card.dto.CardDto;

public record CardTrackerDTO(Integer id,
                             Float targetPrice,
                             boolean active,
                             CardDto card) {
}
