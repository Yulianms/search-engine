package cards.poketracker.searchengine.pokeuser;

import cards.poketracker.searchengine.pokeuser.converter.UserToUserDtoConverter;
import cards.poketracker.searchengine.pokeuser.dto.PokeUserDto;
import cards.poketracker.searchengine.system.StatusCode;
import cards.poketracker.searchengine.system.exception.ObjectNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.print.attribute.standard.Media;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class PokeUserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PokeUserService pokeUserService;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${api.endpoint.base-url}")
    String baseUrl;

    List<PokeUser> users;

    @BeforeEach
    void setUp() {

        PokeUser u1 = new PokeUser();
        u1.setUsername("yulianms");
        u1.setPassword("34dyr3");
        u1.setEmail("yulianms@gmail.com");
        u1.setEnabled(true);
        u1.setRoles("admin");

        PokeUser u2 = new PokeUser();
        u2.setUsername("barbara");
        u2.setPassword("asdas");
        u2.setEmail("barbara@gmail.com");
        u2.setEnabled(false);
        u2.setRoles("user");

        PokeUser u3 = new PokeUser();
        u3.setUsername("Luis");
        u3.setPassword("qwerty");
        u3.setEmail("luisj@gmail.com");
        u3.setEnabled(true);
        u3.setRoles("user");

        this.users = new ArrayList<>();
        this.users.add(u1);
        this.users.add(u2);
        this.users.add(u3);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindAllUsersSuccess() throws Exception {

        // Given
        given(this.pokeUserService.findAll()).willReturn(this.users);


        // When & Then
        this.mockMvc.perform(get(this.baseUrl + "/users").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find All Success"))
                .andExpect(jsonPath("$.data[0].username").value("yulianms"));
        verify(this.pokeUserService, times(1)).findAll();

    }

    @Test
    void testFindUserByIdSuccess() throws Exception {
        // Given
        PokeUser u = new PokeUser();
        u.setId(67L);
        u.setUsername("yulianms");
        u.setPassword("34dyr3");
        u.setEmail("yulianms@gmail.com");
        u.setEnabled(true);
        u.setRoles("admin");

        given(this.pokeUserService.findById(67L)).willReturn(u);
        // When & Then
        this.mockMvc.perform(get(baseUrl + "/users/67").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Find One Success"))
                .andExpect(jsonPath("$.data.username").value("yulianms"))
                .andExpect(jsonPath("$.data.email").value("yulianms@gmail.com"));
        verify(pokeUserService, times(1)).findById(67L);

    }

    @Test
    void testFindUserByIdNotFound() throws Exception {
        //Given
        given(this.pokeUserService.findById(67L)).willThrow(new ObjectNotFoundException("user", 67L));

        // When & Then
        this.mockMvc.perform(get(baseUrl + "/users/67").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find user with ID: 67"))
                .andExpect(jsonPath("$.data").isEmpty());

    }

    @Test
    void testCreateUserSuccess() throws Exception {
        // Given
        PokeUser u = new PokeUser();
        u.setUsername("yulianms");
        u.setPassword("34dyr3");
        u.setEmail("yulianms@gmail.com");
        u.setEnabled(true);
        u.setRoles("admin");

        String json = this.objectMapper.writeValueAsString(u);

        PokeUser savedUser = new PokeUser();
        savedUser.setId(1L);
        savedUser.setUsername("yulianms");
        savedUser.setPassword("34dyr3");
        savedUser.setEmail("yulianms@gmail.com");
        savedUser.setEnabled(true);
        savedUser.setRoles("admin");

        given(this.pokeUserService.create(Mockito.any(PokeUser.class))).willReturn(savedUser);

        // When & Then
        this.mockMvc.perform(post(baseUrl + "/users").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Create Success"))
                .andExpect(jsonPath("$.data.username").value("yulianms"))
                .andExpect(jsonPath("$.data.email").value("yulianms@gmail.com"));
    }

    @Test
    void testUpdateUserSuccess() throws Exception {
        // Given
        PokeUserDto update = new PokeUserDto(1L,
                "yulianms",
                "yulianms06@gmail.com",
                true, "admin",
                null);

        String json = this.objectMapper.writeValueAsString(update);

        PokeUser updatedUser = new PokeUser();
        updatedUser.setId(1L);
        updatedUser.setUsername("yulianms");
        updatedUser.setPassword("34dyr3");
        updatedUser.setEmail("yulianms06@gmail.com");
        updatedUser.setEnabled(true);
        updatedUser.setRoles("admin");

        given(this.pokeUserService.update(eq(1L), Mockito.any(PokeUser.class))).willReturn(updatedUser);

        // When & Then
        this.mockMvc.perform(put(baseUrl + "/users/1").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Update Success"))
                .andExpect(jsonPath("$.data.username").value("yulianms"))
                .andExpect(jsonPath("$.data.email").value("yulianms06@gmail.com"));
    }

    @Test
    void testUpdateUserNotFound() throws Exception {
        // Given
        PokeUserDto update = new PokeUserDto(1L,
                "yulianms",
                "yulianms06@gmail.com",
                true, "admin",
                null);

        String json = this.objectMapper.writeValueAsString(update);

        given(this.pokeUserService.update(eq(1L), Mockito.any(PokeUser.class))).willThrow(new ObjectNotFoundException("user", 1L));
        // When & Then
        this.mockMvc.perform(put(baseUrl + "/users/1").contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find user with ID: 1"))
                .andExpect(jsonPath("$.data").isEmpty());

    }

    @Test
    void testDeleteUserSuccess() throws Exception {
        // Given
        doNothing().when(this.pokeUserService).delete(1L);

        // When & Then
        this.mockMvc.perform(delete(baseUrl + "/users/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
                .andExpect(jsonPath("$.message").value("Delete Success"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    void testDeleteUserNotFound () throws Exception {
        // Given
        doThrow(new ObjectNotFoundException("user", 1L)).when(this.pokeUserService).delete(1L);

        // When & Then
        this.mockMvc.perform(delete(baseUrl + "/users/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
                .andExpect(jsonPath("$.message").value("Could not find user with ID: 1"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}