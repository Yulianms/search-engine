package cards.poketracker.searchengine.pokeuser;

import cards.poketracker.searchengine.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PokeUserService implements UserDetailsService {

    private final PokeUserRepository pokeUserRepository;

    private final PasswordEncoder passwordEncoder;

    public PokeUserService(PokeUserRepository pokeUserRepository, PasswordEncoder passwordEncoder) {
        this.pokeUserRepository = pokeUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<PokeUser> findAll() {
        return this.pokeUserRepository.findAll();
    }

    public PokeUser findById(Long id) {
        return this.pokeUserRepository.findById(id)
                .orElseThrow( () -> new ObjectNotFoundException("user", id));
    }

    public PokeUser findByUsername(String username) {
        return this.pokeUserRepository.findByUsername(username)
                .orElseThrow( () -> new ObjectNotFoundException("user", username));
    }

    public PokeUser create(PokeUser pokeUser) {
        pokeUser.setId(null); // In case an ID is provided, forces Hibernate to threat it as a new entity
        pokeUser.setPassword(passwordEncoder.encode(pokeUser.getPassword()));
        return this.pokeUserRepository.save(pokeUser);
    }

    public PokeUser update(Long userId, PokeUser update) {
        PokeUser oldUser = this.pokeUserRepository.findById(userId)
                .orElseThrow( () -> new ObjectNotFoundException("user", userId));
        oldUser.setUsername(update.getUsername());
        oldUser.setEmail(update.getEmail());
        return this.pokeUserRepository.save(oldUser);
    }

    public void delete(Long userId) {
        this.pokeUserRepository.findById(userId)
                .orElseThrow( () -> new ObjectNotFoundException("user", userId));
        this.pokeUserRepository.deleteById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.pokeUserRepository.findByUsername(username)
                .map(pokeUser -> new UserPrincipal(pokeUser))
                .orElseThrow( () -> new UsernameNotFoundException("Username " + username + " not found."));
    }
}
