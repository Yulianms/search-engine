package cards.poketracker.searchengine.card;

import cards.poketracker.searchengine.card.converter.CardToCardDtoConverter;
import cards.poketracker.searchengine.card.dto.CardDto;
import cards.poketracker.searchengine.system.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import cards.poketracker.searchengine.system.Result;

@RestController
public class CardController {

    private final CardService cardService;

    private final CardToCardDtoConverter cardToCardDtoConverter;

    public CardController(CardService cardService, CardToCardDtoConverter cardToCardDtoConverter) {
        this.cardService = cardService;
        this.cardToCardDtoConverter = cardToCardDtoConverter;
    }

    @GetMapping("/api/v1/cards/{cardId}")
    public Result findCardById(@PathVariable String cardId) {
        Card foundCard = this.cardService.findById(cardId);
        CardDto cardDto = this.cardToCardDtoConverter.convert(foundCard);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", cardDto);
    }

}
