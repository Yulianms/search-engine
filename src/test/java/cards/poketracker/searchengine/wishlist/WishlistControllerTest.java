package cards.poketracker.searchengine.wishlist;

import cards.poketracker.searchengine.card.Card;
import cards.poketracker.searchengine.card.tracker.CardTracker;
import cards.poketracker.searchengine.card.tracker.converter.CardTrackerToCardTrackerDtoConverter;
import cards.poketracker.searchengine.card.tracker.dto.CardTrackerDTO;
import cards.poketracker.searchengine.pokeuser.PokeUser;
import cards.poketracker.searchengine.system.StatusCode;
import cards.poketracker.searchengine.system.exception.ObjectNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class WishlistControllerTest {

    @MockBean
    private WishlistService wishlistService;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper;

    List<CardTracker> cts;

    @Value("${api.endpoint.base-url}")
    String baseUrl;

    @BeforeEach
    void setUp() {

        // Mock authenticated user
        String username = "username";
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                username, "password", Collections.emptyList()
        );
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // Set authentication in SecurityContext
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

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

        cts = new ArrayList<>();
        cts.add(ct);
        cts.add(ct2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @WithMockUser(username = "username")
    void testFindWishlistByUsernameSuccess() throws Exception {
        // Given
        PokeUser user = new PokeUser();
        user.setId(2L);
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email@email.com");

        Wishlist wl = new Wishlist();
        wl.setId(1);
        wl.setUser(user);
        wl.setCards(cts);

        given(this.wishlistService.findByUsername("username")).willReturn(wl);
        // When & Then
        this.mockMvc.perform(get(this.baseUrl + "/wishlist").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.message").value("Wishlist Found"))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.cards[0].id").value(cts.get(0).getId()));
        verify(wishlistService, times(1)).findByUsername("username");
    }

    @Test
    void testFindWishlistByUsernameNotFound() throws Exception {
        // Given
        given(this.wishlistService.findByUsername("username")).willThrow(new ObjectNotFoundException("wishlist"));

        // When & Then
        this.mockMvc.perform(get(this.baseUrl + "/wishlist").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find the expected wishlist"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}