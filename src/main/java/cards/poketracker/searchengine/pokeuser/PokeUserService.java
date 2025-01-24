package cards.poketracker.searchengine.pokeuser;

import cards.poketracker.searchengine.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PokeUserService {

    private final PokeUserRepository pokeUserRepository;

    public PokeUserService(PokeUserRepository pokeUserRepository) {
        this.pokeUserRepository = pokeUserRepository;
    }

    public List<PokeUser> findAll() {
        return this.pokeUserRepository.findAll();
    }

    public PokeUser findById(Long id) {
        return this.pokeUserRepository.findById(id)
                .orElseThrow( () -> new ObjectNotFoundException("user", id));
    }

    public PokeUser create(PokeUser pokeUser) {
        pokeUser.setId(null); // In case an ID is provided, forces Hibernate to threat it as a new entity
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

}
