package cards.poketracker.searchengine.system.exception;

import cards.poketracker.searchengine.card.CardNotFoundException;
import cards.poketracker.searchengine.system.Result;
import cards.poketracker.searchengine.system.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(CardNotFoundException.class)
    // Redundant status code in the header, useful in case there is a custom code in the request body.
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Result handleCardNotFoundException(CardNotFoundException ex) {
        return new Result(false, StatusCode.NOT_FOUND, ex.getMessage());
    }

}
