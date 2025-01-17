package cards.poketracker.searchengine.card.dto;

import cards.poketracker.searchengine.rarity.Rarity;
import cards.poketracker.searchengine.set.dto.SetDto;

public record CardDto(String id,
                      String name,
                      String category,
                      String subtype,
                      String types,
                      String evolvesFrom,
                      Rarity rarity,
                      Integer pokedexNumber,
                      String smImg,
                      String lgImg,
                      Integer cardNumber,
                      SetDto set) {
}
