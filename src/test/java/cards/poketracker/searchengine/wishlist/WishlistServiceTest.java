package cards.poketracker.searchengine.wishlist;

import cards.poketracker.searchengine.card.Card;
import cards.poketracker.searchengine.card.tracker.CardTracker;
import cards.poketracker.searchengine.pokeuser.PokeUser;
import cards.poketracker.searchengine.pokeuser.PokeUserService;
import cards.poketracker.searchengine.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WishlistServiceTest {

    @Mock
    WishlistRepository wishlistRepository;

    @Mock
    PokeUserService pokeUserService;

    @InjectMocks
    WishlistService wishlistService;

    List<CardTracker> cards;

    @BeforeEach
    void setUp() {

        Card card = new Card();
        card.setId("arc-23");
        card.setName("Arceus");
        card.setSubtype("Basic");

        Card card2 = new Card();
        card2.setId("dia-23");
        card2.setName("Dialga");
        card2.setSubtype("Basic");

        CardTracker ct = new CardTracker();
        ct.setId(1);
        ct.setCard(card);
        ct.setActive(true);
        ct.setTargetPrice(20.21F);

        CardTracker ct2 = new CardTracker();
        ct2.setId(2);
        ct2.setCard(card2);
        ct2.setActive(true);
        ct2.setTargetPrice(14.21F);

        cards = new ArrayList<>();
        cards.add(ct);
        cards.add(ct2);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findByUsernameSuccess() {
        // Given
        PokeUser user = new PokeUser();
        user.setId(1L);
        user.setUsername("yulianms");
        user.setEmail("yulianms@gmail.com");
        user.setPassword("qwerty");

        Wishlist wl = new Wishlist();
        wl.setId(50);
        wl.setUser(user);
        wl.setCards(cards);

        given(this.wishlistRepository.findByUser(user)).willReturn(Optional.of(wl));
        given(this.pokeUserService.findByUsername("yulianms")).willReturn(user);
        // When
        Wishlist foundWishlist = this.wishlistService.findByUsername("yulianms");

        // Then
        assertThat(foundWishlist.getId()).isEqualTo(50);
        assertThat(foundWishlist.getUser()).isEqualTo(user);
        assertThat(foundWishlist.getCards()).isEqualTo(cards);
        verify(wishlistRepository, times(1)).findByUser(user);
    }

    @Test
    void findByUserNotFound() {
        PokeUser user = new PokeUser();
        user.setId(1L);
        user.setUsername("yulianms");
        user.setEmail("yulianms@gmail.com");
        user.setPassword("qwerty");

        // Given
        given(this.wishlistRepository.findByUser(Mockito.any(PokeUser.class))).willReturn(Optional.empty());
        given(this.pokeUserService.findByUsername("yulianms")).willReturn(user);
        // When
        assertThrows(ObjectNotFoundException.class, () -> this.wishlistService.findByUsername("yulianms"));

        // Then
        verify(this.wishlistRepository, times(1)).findByUser(Mockito.any(PokeUser.class));

    }
}