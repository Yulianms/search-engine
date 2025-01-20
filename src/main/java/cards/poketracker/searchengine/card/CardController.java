package cards.poketracker.searchengine.card;

import cards.poketracker.searchengine.card.converter.CardToCardDtoConverter;
import cards.poketracker.searchengine.card.dto.CardDto;
import cards.poketracker.searchengine.legality.Legality;
import cards.poketracker.searchengine.legality.LegalityService;
import cards.poketracker.searchengine.system.StatusCode;
import org.springframework.web.bind.annotation.*;

import cards.poketracker.searchengine.system.Result;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CardController {

    private final CardService cardService;

    private final LegalityService legalityService;

    private final CardToCardDtoConverter cardToCardDtoConverter;

    public CardController(CardService cardService, LegalityService legalityService, CardToCardDtoConverter cardToCardDtoConverter) {
        this.cardService = cardService;
        this.legalityService = legalityService;
        this.cardToCardDtoConverter = cardToCardDtoConverter;
    }

    @GetMapping("/api/v1/cards/{cardId}")
    public Result findCardById(@PathVariable String cardId) {
        Card foundCard = this.cardService.findById(cardId);
        CardDto cardDto = this.cardToCardDtoConverter.convert(foundCard);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", cardDto);
    }

    @GetMapping("/api/v1/cards")
    public Result findAllCards() {
        List<Card> foundCards = this.cardService.findAll();
        // Convert list of cards to cardsDtos
        List<CardDto> cardDtos = foundCards.stream()
                .map(this.cardToCardDtoConverter::convert)
                .collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Find All Success", cardDtos);
    }

    @PostMapping("/api/v1/cards")
    public Result addCard(@RequestBody Card card) {
        // Is not recognizing the request body of legality
        if (card.getLegality() != null) {
            card.getLegality().setCard(card);
        }

        cardService.save(card);
        return new Result(true, StatusCode.SUCCESS, "Add Success", this.cardToCardDtoConverter.convert(card));
    }

    @PutMapping("/api/v1/cards/{cardId}")
    public Result updateCard(@PathVariable String cardId, @RequestBody Card card) {
        Card updatedCard = this.cardService.update(cardId, card);
        return new Result(true, StatusCode.SUCCESS, "Update Success", this.cardToCardDtoConverter.convert(updatedCard));
    }

}
