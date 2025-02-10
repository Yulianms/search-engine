package cards.poketracker.searchengine.card.tracker;

import cards.poketracker.searchengine.card.Card;
import cards.poketracker.searchengine.system.StatusCode;
import cards.poketracker.searchengine.system.exception.ObjectNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.eq;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
class CardTrackerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CardTrackerService cardTrackerService;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${api.endpoint.base-url}")
    String baseUrl;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testSaveCardTrackerSuccess() throws Exception {
        // Given
        Card card = new Card();
        card.setId("lg1");
        card.setName("Lugia");
        card.setSubtype("Basic");
        card.setHealthPoints(300);

        CardTracker cardTracker = new CardTracker();
        cardTracker.setCard(card);
        cardTracker.setTargetPrice(10F);
        cardTracker.setActive(true);
        cardTracker.setWishlist(null);

        String json = objectMapper.writeValueAsString(cardTracker);

        given(this.cardTrackerService.save(Mockito.any(CardTracker.class))).willReturn(cardTracker);
        // When & Then
        this.mockMvc.perform(post(baseUrl + "/wishlist/cards").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.targetPrice").value(10F))
                .andExpect(jsonPath("$.data.active").value(true));
    }

    @Test
    void testUpdateCardTrackerSuccess() throws Exception {
        // Given
        Card card = new Card();
        card.setId("lg1");
        card.setName("Lugia");
        card.setSubtype("Basic");
        card.setHealthPoints(300);

        CardTracker update = new CardTracker();
        update.setCard(card);
        update.setTargetPrice(10F);
        update.setActive(true);
        update.setWishlist(null);

        given(this.cardTrackerService.update(eq(10), Mockito.any(CardTracker.class))).willReturn(update);
        // When & Then
        this.mockMvc.perform(put(baseUrl + "/wishlist/cards/10").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(update)).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Update Success"))
                .andExpect(jsonPath("$.data.targetPrice").value(10F))
                .andExpect(jsonPath("$.data.active").value(true));
    }

    @Test
    void testUpdateCardTrackerNotFound() throws Exception {
        // Given
        Card card = new Card();
        card.setId("lg1");
        card.setName("Lugia");
        card.setSubtype("Basic");
        card.setHealthPoints(300);

        CardTracker update = new CardTracker();
        update.setId(10);
        update.setCard(card);
        update.setTargetPrice(10F);
        update.setActive(true);
        update.setWishlist(null);

        given(this.cardTrackerService.update(eq(10), Mockito.any(CardTracker.class))).willThrow(new ObjectNotFoundException("Tracker", 10));
        // When & Then
        this.mockMvc.perform(put(baseUrl + "/wishlist/cards/10").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(update)).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find Tracker with ID: 10"));

    }

    @Test
    void testDeleteCardTrackerSuccess() throws Exception {
        // Given
        doNothing().when(this.cardTrackerService).delete(1);
        // When & Then
        this.mockMvc.perform(delete(baseUrl + "/wishlist/cards/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Delete Success"));
    }

    @Test
    void testDeleteCardTrackerIdNotFound() throws Exception {
        // Given
        doThrow(new ObjectNotFoundException("Tracker", 1)).when(this.cardTrackerService).delete(1);

        // When & Then
        this.mockMvc.perform(delete(baseUrl + "/wishlist/cards/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find Tracker with ID: 1"))
                .andExpect(jsonPath("$.data").doesNotExist());

    }
}