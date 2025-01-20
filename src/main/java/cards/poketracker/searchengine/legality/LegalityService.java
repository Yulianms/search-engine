package cards.poketracker.searchengine.legality;

import org.springframework.stereotype.Service;

@Service
public class LegalityService {

    private final LegalityRepository legalityRepository;

    public LegalityService(LegalityRepository legalityRepository) {
        this.legalityRepository = legalityRepository;
    }

    public Legality save(Legality legality) {
        return legalityRepository.save(legality);
    }

}
