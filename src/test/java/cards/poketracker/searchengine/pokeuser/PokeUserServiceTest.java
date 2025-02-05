package cards.poketracker.searchengine.pokeuser;

import cards.poketracker.searchengine.system.exception.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PokeUserServiceTest {

    @Mock
    PokeUserRepository pokeUserRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    PokeUserService pokeUserService;

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
    void testFindAllSuccess() {
        // Given
        given(this.pokeUserRepository.findAll()).willReturn(this.users);

        // When
        List<PokeUser> returnedUsers = this.pokeUserService.findAll();

        // Then
        assertThat(returnedUsers.size()).isEqualTo(this.users.size());
        verify(this.pokeUserRepository, times(1)).findAll();

    }

    @Test
    void testFindByIdSuccess() {
        // Given
        PokeUser u = new PokeUser();
        u.setId(67L);
        u.setUsername("yulianms");
        u.setPassword("34dyr3");
        u.setEmail("yulianms@gmail.com");
        u.setEnabled(true);
        u.setRoles("admin");

        given(this.pokeUserRepository.findById(67L)).willReturn(Optional.of(u));
        // When
        PokeUser foundUser = pokeUserService.findById(67L);

        // Then
        assertThat(foundUser.getId()).isEqualTo(u.getId());
        assertThat(foundUser.getEmail()).isEqualTo(u.getEmail());
        assertThat(foundUser.getUsername()).isEqualTo(u.getUsername());
        assertThat(foundUser.getRoles()).isEqualTo(u.getRoles());
        verify(pokeUserRepository, times(1)).findById(67L);

    }

    @Test
    void testFindByIdNotFound() {
        // Given
        given(this.pokeUserRepository.findById(67L)).willReturn(Optional.empty());

        // When
        assertThrows(ObjectNotFoundException.class, () -> pokeUserService.findById(67L));

        // Then
        verify(pokeUserRepository, times(1)).findById(67L);
    }

    @Test
    void testCreateSuccess() {
        // Given
        PokeUser u = new PokeUser();
        u.setId(1L);
        u.setUsername("yulianms");
        u.setPassword("34dyr3");
        u.setEmail("yulianms@gmail.com");
        u.setEnabled(true);
        u.setRoles("admin");

        given(this.passwordEncoder.encode(u.getPassword())).willReturn("Encoded Password");
        given(this.pokeUserRepository.save(u)).willReturn(u);
        // When
        PokeUser savedUser = pokeUserService.create(u);

        // Then
        assertThat(savedUser.getId()).isEqualTo(u.getId());
        assertThat(savedUser.getUsername()).isEqualTo(u.getUsername());
        assertThat(savedUser.getPassword()).isEqualTo(u.getPassword());
        verify(this.pokeUserRepository, times(1)).save(u);
    }

    @Test
    void testUpdateSuccess() {
        // Given
        PokeUser oldUser = new PokeUser();
        oldUser.setId(1L);
        oldUser.setUsername("yulianms");
        oldUser.setPassword("34dyr3");
        oldUser.setEmail("yulianms@gmail.com");
        oldUser.setEnabled(true);
        oldUser.setRoles("admin");

        PokeUser update = new PokeUser();
        update.setId(1L);
        update.setUsername("yulianms06");
        update.setEmail("yulianms06@gmail.com");
        update.setEnabled(true);
        update.setRoles("admin");

        given(this.pokeUserRepository.findById(1L)).willReturn(Optional.of(oldUser));
        given(this.pokeUserRepository.save(oldUser)).willReturn(oldUser);

        // When
        PokeUser updatedUser = this.pokeUserService.update(1L, update);

        // Then
        assertThat(updatedUser.getId()).isEqualTo(update.getId());
        assertThat(updatedUser.getUsername()).isEqualTo(update.getUsername());
        assertThat(updatedUser.getEmail()).isEqualTo(update.getEmail());
        verify(pokeUserRepository, times(1)).save(oldUser);
        verify(pokeUserRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateIdNotFound() {
        // Given
        given(this.pokeUserRepository.findById(1L)).willReturn(Optional.empty());

        // When
        Throwable thrown = assertThrows(ObjectNotFoundException.class, () -> pokeUserService.update(1L, new PokeUser()));

        // Then
        assertThat(thrown).isInstanceOf(ObjectNotFoundException.class)
                        .hasMessage("Could not find user with ID: 1");
        verify(pokeUserRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteSuccess() {
        // Given
        PokeUser u = new PokeUser();
        u.setId(1L);
        u.setUsername("yulianms");
        u.setPassword("34dyr3");
        u.setEmail("yulianms@gmail.com");
        u.setEnabled(true);
        u.setRoles("admin");

        given(this.pokeUserRepository.findById(1L)).willReturn(Optional.of(u));
        doNothing().when(this.pokeUserRepository).deleteById(1L);

        // When
        this.pokeUserService.delete(1L);

        // Then
        verify(this.pokeUserRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteNotFound() {
        // Given
        given(this.pokeUserRepository.findById(1L)).willReturn(Optional.empty());

        // When
        Throwable thrown = assertThrows(ObjectNotFoundException.class, () -> this.pokeUserService.delete(1L));

        // Then
        assertThat(thrown).isInstanceOf(ObjectNotFoundException.class).hasMessage("Could not find user with ID: 1");
        verify(this.pokeUserRepository, times(1)).findById(1L);
    }

}