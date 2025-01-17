package cards.poketracker.searchengine.card;

import cards.poketracker.searchengine.rarity.Rarity;
import cards.poketracker.searchengine.set.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock
    CardRepository cardRepo;

    @InjectMocks
    CardService cardService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindByIdSuccess() {
        // Given. Prepare inputs and targets. Define behaviour of Mock object.
        Card c = new Card();
        c.setId("swsh-23");
        c.setName("Pikachu");
        c.setCategory("Pokemon");
        c.setHealthPoints(80);
        c.setTypes("Lightning");
        c.setEvolvesFrom("Pichu");
        c.setRetreatCost(Boolean.FALSE);
        c.setPokedexNumber(16);
        c.setSmImg("images/small");
        c.setLgImg("images/large");
        c.setAbilities(Boolean.TRUE);
        c.setCardNumber(24);
        c.setResistances("Fighting, Ground");
        c.setWeaknesses("Water, Fire");
        c.setLevel("X");

        Set s = new Set();
        s.setId("swsh-23");
        s.setName("Surging Sparks");
        s.setSeries("swsh");
        s.setPrintedTotal(145);
        s.setCode("PAF");
        s.setReleaseDate("1999/06/16");
        s.setSymbol("images/symbol");
        s.setLogo("images/logo");


        Rarity r = new Rarity();
        r.setId(323);
        r.setName("HoloRare");
        r.setRarity(8);

        c.setRarity(r);
        c.setSet(s);

        // Defines behaviour of the Mock
        BDDMockito.given(cardRepo.findById("swsh-23")).willReturn(Optional.of(c));

        // When. Act on target behaviour. When steps should cover the method to be tested.
        Card returnedCard = cardService.findById("swsh-23");

        // Then. Assert expected outcomes.
        assertThat(returnedCard.getId()).isEqualTo(c.getId());
        assertThat(returnedCard.getName()).isEqualTo(c.getName());
        assertThat(returnedCard.getSubtype()).isEqualTo(c.getSubtype());
        assertThat(returnedCard.getSet()).isEqualTo(c.getSet());
        assertThat(returnedCard.getRarity()).isEqualTo(c.getRarity());
        assertThat(returnedCard.getLegalities()).isEqualTo(c.getLegalities());
        verify(cardRepo, times(1)).findById("swsh-23");

    }

    @Test
    void testFindByIdNotFound() {
        // Given
        // -Behaviour
        BDDMockito.given(cardRepo.findById(Mockito.any(String.class))).willReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(()->{ Card returnedCard = cardService.findById("swsh-23"); });

        // Then
        assertThat(thrown)
                .isInstanceOf(CardNotFoundException.class)
                .hasMessage("Could not find card with ID: swsh-23");
        verify(cardRepo, times(1)).findById("swsh-23");

    }
}