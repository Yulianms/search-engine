package cards.poketracker.searchengine.card;

import cards.poketracker.searchengine.system.StatusCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class CardControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CardService cardService;

    List<Card> cards;

    @BeforeEach
    void setUp() {
        this.cards = new ArrayList<>();

        Card c1 = new Card();
        c1.setId("scvl-we");
        c1.setName("Geodude");
        c1.setCategory("Pokemon");
        this.cards.add(c1);

        Card c2 = new Card();
        c2.setId("scvl-dw");
        c2.setName("Mewtwo");
        c2.setCategory("Pokemon");
        this.cards.add(c2);

        Card c3 = new Card();
        c3.setId("scvl-itm");
        c3.setName("Masterball");
        c3.setCategory("Item");
        this.cards.add(c3);

        Card c4 = new Card();
        c4.setId("scvl-gg");
        c4.setName("Lugia");
        c4.setCategory("Pokemon");
        this.cards.add(c4);

        Card c5 = new Card();
        c5.setId("scvl-ffg");
        c5.setName("Ponyta");
        c5.setCategory("Pokemon");
        this.cards.add(c5);

        Card c6 = new Card();
        c6.setId("scvl-trn");
        c6.setName("Misty");
        c6.setCategory("Trainer");
        this.cards.add(c6);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindCardByIdSuccess() throws Exception {
        // Given
        given(this.cardService.findById("scvl-gg")).willReturn(this.cards.get(3));

        // When and then
        this.mockMvc.perform(get("/api/v1/cards/scvl-gg").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value("scvl-gg"))
                .andExpect(jsonPath("$.data.name").value("Lugia"));
    }

    @Test
    void testFindCardByIdNotFound() throws Exception {
        // Given
        given(this.cardService.findById("scvl-gg")).willThrow(new CardNotFoundException("scvl-gg"));

        // When and then
        this.mockMvc.perform(get("/api/v1/cards/scvl-gg").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find card with ID: scvl-gg"))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}