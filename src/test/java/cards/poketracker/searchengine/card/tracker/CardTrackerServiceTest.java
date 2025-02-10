package cards.poketracker.searchengine.card.tracker;

import cards.poketracker.searchengine.card.Card;
import cards.poketracker.searchengine.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardTrackerServiceTest {

    @Mock
    CardTrackerRepository cardTrackerRepository;

    @InjectMocks
    CardTrackerService cardTrackerService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testSaveSuccess() {
        // Given
        Card card = new Card();
        card.setId("palk-23");
        card.setName("Palkia");
        card.setSubtype("Basic");
        card.setHealthPoints(220);

        CardTracker cardTracker = new CardTracker();
        cardTracker.setCard(card);
        cardTracker.setActive(true);
        cardTracker.setTargetPrice(10.34F);
        cardTracker.setWishlist(null);

        given(this.cardTrackerRepository.save(cardTracker)).willReturn(cardTracker);
        // When
        CardTracker savedCardTracker = this.cardTrackerService.save(cardTracker);

        // Then
        assertThat(savedCardTracker.getCard()).isEqualTo(card);
        assertThat(savedCardTracker.getActive()).isTrue();
        assertThat(savedCardTracker.getTargetPrice()).isEqualTo(10.34F);
        assertThat(savedCardTracker.getWishlist()).isNull();
        verify(this.cardTrackerRepository, times(1)).save(cardTracker);
    }

    @Test
    void testUpdateSuccess() {
        // Given
        Card card = new Card();
        card.setId("palk-23");
        card.setName("Palkia");
        card.setSubtype("Basic");
        card.setHealthPoints(220);

        CardTracker oldTracker = new CardTracker();
        oldTracker.setId(1);
        oldTracker.setCard(card);
        oldTracker.setActive(true);
        oldTracker.setTargetPrice(10.34F);
        oldTracker.setWishlist(null);

        CardTracker update = new CardTracker();
        update.setCard(card);
        update.setActive(false);
        update.setTargetPrice(9F);
        update.setWishlist(null);

        given(this.cardTrackerRepository.findById(1)).willReturn(Optional.of(oldTracker));
        given(this.cardTrackerRepository.save(oldTracker)).willReturn(oldTracker);
        // When
        CardTracker updatedCard = this.cardTrackerService.update(1, update);

        // Then
        assertThat(updatedCard.getCard()).isEqualTo(card);
        assertThat(updatedCard.getActive()).isFalse();
        assertThat(updatedCard.getTargetPrice()).isEqualTo(9F);
        verify(this.cardTrackerRepository, times(1)).findById(1);
    }

    @Test
    void testUpdateIdNotFound() {
        // Given
        Card card = new Card();
        card.setId("palk-23");
        card.setName("Palkia");
        card.setSubtype("Basic");
        card.setHealthPoints(220);

        CardTracker update = new CardTracker();
        update.setCard(card);
        update.setActive(false);
        update.setTargetPrice(9F);
        update.setWishlist(null);

        given(this.cardTrackerRepository.findById(1)).willReturn(Optional.empty());

        // When
        assertThrows(ObjectNotFoundException.class, () -> this.cardTrackerService.update(1, update));

        // Then
        verify(cardTrackerRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteSuccess() {
        // Given
        Card card = new Card();
        card.setId("palk-23");
        card.setName("Palkia");
        card.setSubtype("Basic");
        card.setHealthPoints(220);

        CardTracker cardTracker = new CardTracker();
        cardTracker.setId(1);
        cardTracker.setCard(card);
        cardTracker.setActive(false);
        cardTracker.setTargetPrice(9F);
        cardTracker.setWishlist(null);

        given(this.cardTrackerRepository.findById(1)).willReturn(Optional.of(cardTracker));
        doNothing().when(this.cardTrackerRepository).deleteById(1);
        // When
        this.cardTrackerService.delete(1);

        // Then
        verify(cardTrackerRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteIdNotFound() {
        // Given
        given(this.cardTrackerRepository.findById(1)).willReturn(Optional.empty());
        // When
        Throwable thrown = assertThrows(ObjectNotFoundException.class, () -> this.cardTrackerService.delete(1));

        //Then
        assertThat(thrown).isInstanceOf(ObjectNotFoundException.class);
        assertThat(thrown.getMessage()).isEqualTo("Could not find Card with ID: 1");
        verify(this.cardTrackerRepository, times(1)).findById(1);
    }
}