package cards.poketracker.searchengine.card;

import cards.poketracker.searchengine.card.dto.CardDto;
import cards.poketracker.searchengine.rarity.Rarity;
import cards.poketracker.searchengine.system.StatusCode;
import cards.poketracker.searchengine.system.exception.ObjectNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    ObjectMapper objectMapper;

    List<Card> cards;

    @Value("${api.endpoint.base-url}")
    String baseUrl;

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
        this.mockMvc.perform(get(this.baseUrl + "/cards/scvl-gg").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.id").value("scvl-gg"))
                .andExpect(jsonPath("$.data.name").value("Lugia"));
    }

    @Test
    void testFindCardByIdNotFound() throws Exception {
        // Given
        given(this.cardService.findById("scvl-gg")).willThrow(new ObjectNotFoundException("card", "scvl-gg"));

        // When and then
        this.mockMvc.perform(get(this.baseUrl + "/cards/scvl-gg").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find card with ID: scvl-gg"))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testFindAllCardsSuccess() throws Exception {
        // Given
        given(this.cardService.findAll()).willReturn(this.cards);

        // When and then
        this.mockMvc.perform(get(this.baseUrl + "/cards").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success"))
                .andExpect(jsonPath("$.data", Matchers.hasSize(this.cards.size())))
                .andExpect(jsonPath("$.data[0].id").value("scvl-we"))
                .andExpect(jsonPath("$.data[1].id").value("scvl-dw"));
    }

    @Test
    void testAddCardSuccess() throws Exception {
        // Given
        Rarity r = new Rarity();
        r.setName("Holo Rare");

        CardDto cardDto = new CardDto("dmpl-99",
                "Charizard",
                "Pokemon",
                "Stage 2",
                "Fire, Flying",
                "Charmeleon",
                r,
                3,
                "images/small",
                "images/large",
                34,
                null,
                null);
        String json = this.objectMapper.writeValueAsString(cardDto);

        Card savedCard = new Card();
        savedCard.setId("dmpl-99");
        savedCard.setName("Charizard");
        savedCard.setCategory("Pokemon");
        savedCard.setRarity(r);
        savedCard.setTypes("Fire, Flying");
        savedCard.setSubtype("Stage 2");
        savedCard.setEvolvesFrom("Charmeleon");
        savedCard.setPokedexNumber(3);
        savedCard.setSmImg("images/small");
        savedCard.setLgImg("images/large");
        savedCard.setCardNumber(34);
        savedCard.setSet(null);

        given(this.cardService.save(Mockito.any(Card.class))).willReturn(savedCard);

        // When and then
        this.mockMvc.perform(post(this.baseUrl + "/cards").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.id").isNotEmpty())
                .andExpect(jsonPath("$.data.name").value("Charizard"));
    }

    @Test
    void testUpdateCardSuccess() throws Exception {
        // Given
        Rarity r = new Rarity();
        r.setName("Holo Rare");

        CardDto cardDto = new CardDto("dmpl-99",
                "Charizard",
                "Pokemon",
                "Stage 2",
                "Fire, Flying",
                "Charmeleon",
                r,
                3,
                "images/small",
                "images/large",
                34,
                null,
                null);
        String json = this.objectMapper.writeValueAsString(cardDto);

        Card updatedCard = new Card();
        updatedCard.setId("dmpl-99");
        updatedCard.setName("Charizard");
        updatedCard.setCategory("Pokemon");
        updatedCard.setRarity(r);
        updatedCard.setTypes("Fire, Flying");
        updatedCard.setSubtype("Stage 2");
        updatedCard.setEvolvesFrom("Charmeleon");
        updatedCard.setPokedexNumber(3);
        updatedCard.setSmImg("images/small");
        updatedCard.setLgImg("images/large");
        updatedCard.setCardNumber(34);
        updatedCard.setSet(null);

        given(this.cardService.update(eq("dmpl-99"), Mockito.any(Card.class))).willReturn(updatedCard);

        // When and then
        this.mockMvc.perform(put(this.baseUrl + "/cards/dmpl-99").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Update Success"))
                .andExpect(jsonPath("$.data.id").value("dmpl-99"))
                .andExpect(jsonPath("$.data.name").value(updatedCard.getName()));
    }

    @Test
    void testUpdateCardErrorWithNoExistingId() throws Exception {
        // Given
        Rarity r = new Rarity();
        r.setName("Holo Rare");

        CardDto cardDto = new CardDto("dmpl-99",
                "Charizard",
                "Pokemon",
                "Stage 2",
                "Fire, Flying",
                "Charmeleon",
                r,
                3,
                "images/small",
                "images/large",
                34,
                null,
                null);
        String json = this.objectMapper.writeValueAsString(cardDto);

        given(this.cardService.update(eq("dmpl-99"), Mockito.any(Card.class))).willThrow(new ObjectNotFoundException("card", "dmpl-99"));

        // When and then
        this.mockMvc.perform(put(this.baseUrl + "/cards/dmpl-99").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find card with ID: dmpl-99"))
                .andExpect(jsonPath("$.data").isEmpty());

    }
}