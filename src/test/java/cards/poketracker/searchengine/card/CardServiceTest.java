package cards.poketracker.searchengine.card;

import cards.poketracker.searchengine.rarity.Rarity;
import cards.poketracker.searchengine.set.Set;
import cards.poketracker.searchengine.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock
    CardRepository cardRepo;

    @InjectMocks
    CardService cardService;

    List<Card> cards;

    @BeforeEach
    void setUp() {

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

        Card c2 = new Card();
        c2.setId("swsh-25");
        c2.setName("Blastoise");
        c2.setCategory("Pokemon");
        c2.setHealthPoints(100);
        c2.setTypes("Water");
        c2.setEvolvesFrom("Wartortle");
        c2.setRetreatCost(Boolean.TRUE);
        c2.setPokedexNumber(9);
        c2.setSmImg("images/small-blastoise");
        c2.setLgImg("images/large-blastoise");
        c2.setAbilities(Boolean.TRUE);
        c2.setCardNumber(36);
        c2.setResistances("Fire, Ground");
        c2.setWeaknesses("Electric, Grass");
        c2.setLevel("GX");

        this.cards = new ArrayList<>();
        this.cards.add(c);
        this.cards.add(c2);
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
        assertThat(returnedCard.getLegality()).isEqualTo(c.getLegality());
        verify(cardRepo, times(1)).findById("swsh-23");

    }

    @Test
    void testFindByIdNotFound() {
        // Given
        // -Behaviour


        // When


        // Then


    }

    @Test
    void testFindAllSuccess() {
        // Given
        // -Behaviour
        BDDMockito.given(cardRepo.findAll()).willReturn(this.cards);

        // When
        List<Card> actualCards = cardService.findAll();

        // Then
        assertThat(actualCards.size()).isEqualTo(this.cards.size());
        verify(cardRepo, times(1)).findAll();

    }

    @Test
    void testSaveSuccess() {
        // Given
        Card newCard = new Card();
        newCard.setId("swsh-93");
        newCard.setName("Arceus");
        newCard.setCategory("Pokemon");
        newCard.setHealthPoints(80);

        BDDMockito.given(cardRepo.save(newCard)).willReturn(newCard);

        // When
        Card savedCard = cardService.save(newCard);

        // Then
        assertThat(savedCard.getId()).isEqualTo(newCard.getId());
        verify(cardRepo, times(1)).save(newCard);

    }

    @Test
    void testUpdateSuccess() {
        //Given
        Card oldCard = new Card();
        oldCard.setId("swsh-93");
        oldCard.setName("Arceus");
        oldCard.setCategory("Pokemon");
        oldCard.setHealthPoints(80);

        Card update = new Card();
        update.setId("swsh-93");
        update.setName("Bidoff");
        update.setCategory("Pokemon");
        update.setHealthPoints(200);

        given(cardRepo.findById("swsh-93")).willReturn(Optional.of(oldCard));
        given(cardRepo.save(oldCard)).willReturn(oldCard);
        // When
        Card updatedCard = cardService.update("swsh-93", update);

        // Then
        assertThat(updatedCard.getId()).isEqualTo(update.getId());
        assertThat(updatedCard.getName()).isEqualTo(update.getName());
        assertThat(updatedCard.getCategory()).isEqualTo(update.getCategory());
        assertThat(updatedCard.getHealthPoints()).isEqualTo(update.getHealthPoints());
        verify(cardRepo, times(1)).findById("swsh-93");
        verify(cardRepo, times(1)).save(oldCard);

    }

    @Test
    void testUpdateNotFound(){
        // Given
        Card update = new Card();
        update.setName("Bidoff");
        update.setCategory("Pokemon");
        update.setHealthPoints(200);

        given(cardRepo.findById("swsh-93")).willReturn(Optional.empty());
        // When
        assertThrows(ObjectNotFoundException.class, () -> cardService.update("swsh-93", update));

        // Then
        verify(cardRepo, times(1)).findById("swsh-93");
    }

}