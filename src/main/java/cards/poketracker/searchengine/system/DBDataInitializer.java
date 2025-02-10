package cards.poketracker.searchengine.system;

import cards.poketracker.searchengine.card.Card;
import cards.poketracker.searchengine.card.CardRepository;
import cards.poketracker.searchengine.card.tracker.CardTracker;
import cards.poketracker.searchengine.card.tracker.CardTrackerRepository;
import cards.poketracker.searchengine.legality.Legality;
import cards.poketracker.searchengine.legality.LegalityRepository;
import cards.poketracker.searchengine.pokeuser.PokeUser;
import cards.poketracker.searchengine.pokeuser.PokeUserRepository;
import cards.poketracker.searchengine.pokeuser.PokeUserService;
import cards.poketracker.searchengine.rarity.Rarity;
import cards.poketracker.searchengine.rarity.RarityRepository;
import cards.poketracker.searchengine.set.Set;
import cards.poketracker.searchengine.set.SetRepository;
import cards.poketracker.searchengine.wishlist.Wishlist;
import cards.poketracker.searchengine.wishlist.WishlistRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class DBDataInitializer implements CommandLineRunner {

    private final LegalityRepository legalityRepository;

    private final RarityRepository rarityRepository;

    private final SetRepository setRepository;

    private final PokeUserService pokeUserService;

    private final CardTrackerRepository cardTrackerRepository;

    private final WishlistRepository wishlistRepository;

    private List<CardTracker> cardTrackers;

    public DBDataInitializer(LegalityRepository legalityRepository, RarityRepository rarityRepository, SetRepository setRepository, PokeUserService pokeUserService, CardTrackerRepository cardTrackerRepository, WishlistRepository wishlistRepository) {
        this.legalityRepository = legalityRepository;
        this.rarityRepository = rarityRepository;
        this.setRepository = setRepository;
        this.pokeUserService = pokeUserService;
        this.cardTrackerRepository = cardTrackerRepository;
        this.wishlistRepository = wishlistRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Rarity r = new Rarity();
        r.setId(323);
        r.setName("HoloRare");
        r.setRarity(8);

        Rarity r1 = new Rarity();
        r1.setId(324);
        r1.setName("Rare Holo");
        r1.setRarity(9);

        Rarity r2 = new Rarity();
        r2.setId(325);
        r2.setName("Ultra Rare");
        r2.setRarity(10);

        Rarity r3 = new Rarity();
        r3.setId(326);
        r3.setName("Rare Shiny");
        r3.setRarity(11);

        Rarity r4 = new Rarity();
        r4.setId(327);
        r4.setName("Secret Rare");
        r4.setRarity(12);

        Card c = new Card();
        c.setId("swsh-23");
        c.setName("Pikachu");
        c.setCategory("Pokemon");
        c.setHealthPoints(80);
        c.setTypes("Lightning");
        c.setEvolvesFrom("Pichu");
        c.setRetreatCost(Boolean.FALSE);
        c.setPokedexNumber(16);
        c.setSmImg("images/small");
        c.setLgImg("images/large");
        c.setAbilities(Boolean.TRUE);
        c.setCardNumber(24);
        c.setResistances("Fighting, Ground");
        c.setWeaknesses("Water, Fire");
        c.setLevel("X");
        c.setRarity(r);

        Card c1 = new Card();
        c1.setId("swsh-24");
        c1.setName("Charizard");
        c1.setCategory("Pokemon");
        c1.setHealthPoints(120);
        c1.setTypes("Fire");
        c1.setEvolvesFrom("Charmeleon");
        c1.setRetreatCost(Boolean.TRUE);
        c1.setPokedexNumber(6);
        c1.setSmImg("images/small-charizard");
        c1.setLgImg("images/large-charizard");
        c1.setAbilities(Boolean.FALSE);
        c1.setCardNumber(4);
        c1.setResistances("Grass, Bug");
        c1.setWeaknesses("Water, Rock");
        c1.setLevel("EX");
        c1.setRarity(r1);

        Card c2 = new Card();
        c2.setId("swsh-25");
        c2.setName("Blastoise");
        c2.setCategory("Pokemon");
        c2.setHealthPoints(100);
        c2.setTypes("Water");
        c2.setEvolvesFrom("Wartortle");
        c2.setRetreatCost(Boolean.TRUE);
        c2.setPokedexNumber(9);
        c2.setSmImg("images/small-blastoise");
        c2.setLgImg("images/large-blastoise");
        c2.setAbilities(Boolean.TRUE);
        c2.setCardNumber(36);
        c2.setResistances("Fire, Ground");
        c2.setWeaknesses("Electric, Grass");
        c2.setLevel("GX");
        c2.setRarity(r2);

        Card c3 = new Card();
        c3.setId("swsh-26");
        c3.setName("Venusaur");
        c3.setCategory("Pokemon");
        c3.setHealthPoints(90);
        c3.setTypes("Grass");
        c3.setEvolvesFrom("Ivysaur");
        c3.setRetreatCost(Boolean.FALSE);
        c3.setPokedexNumber(3);
        c3.setSmImg("images/small-venusaur");
        c3.setLgImg("images/large-venusaur");
        c3.setAbilities(Boolean.FALSE);
        c3.setCardNumber(15);
        c3.setResistances("Water, Rock");
        c3.setWeaknesses("Fire, Bug");
        c3.setLevel("Mega");
        c3.setRarity(r3);

        Card c4 = new Card();
        c4.setId("swsh-27");
        c4.setName("Mewtwo");
        c4.setCategory("Pokemon");
        c4.setSubtype("Stage 2");
        c4.setHealthPoints(110);
        c4.setTypes("Psychic");
        c4.setEvolvesFrom(null);
        c4.setRetreatCost(Boolean.TRUE);
        c4.setPokedexNumber(150);
        c4.setSmImg("images/small-mewtwo");
        c4.setLgImg("images/large-mewtwo");
        c4.setAbilities(Boolean.TRUE);
        c4.setCardNumber(99);
        c4.setResistances("Fighting");
        c4.setWeaknesses("Dark, Bug");
        c4.setLevel("X");
        c4.setRarity(r4);

        Set s = new Set();
        s.setId("swsh-23");
        s.setName("Surging Sparks");
        s.setSeries("swsh");
        s.setPrintedTotal(145);
        s.setCode("PAF");
        s.setReleaseDate("1999/06/16");
        s.setSymbol("images/symbol");
        s.setLogo("images/logo");
        s.addCard(c);
        s.addCard(c3);

        Set s1 = new Set();
        s1.setId("swsh-30");
        s1.setName("Blazing Battles");
        s1.setSeries("swsh");
        s1.setPrintedTotal(130);
        s1.setCode("BBL");
        s1.setReleaseDate("2020/03/01");
        s1.setSymbol("images/symbol-bb");
        s1.setLogo("images/logo-bb");
        s1.addCard(c2);

        Set s2 = new Set();
        s2.setId("swsh-31");
        s2.setName("Shining Legends");
        s2.setSeries("sunmoon");
        s2.setPrintedTotal(180);
        s2.setCode("SLG");
        s2.setReleaseDate("2018/11/22");
        s2.setSymbol("images/symbol-sl");
        s2.setLogo("images/logo-sl");

        Set s3 = new Set();
        s3.setId("swsh-32");
        s3.setName("Fusion Fury");
        s3.setSeries("fusion");
        s3.setPrintedTotal(150);
        s3.setCode("FFU");
        s3.setReleaseDate("2021/06/12");
        s3.setSymbol("images/symbol-ff");
        s3.setLogo("images/logo-ff");
        s3.addCard(c1);

        Set s4 = new Set();
        s4.setId("swsh-33");
        s4.setName("Hidden Realms");
        s4.setSeries("hiddenfates");
        s4.setPrintedTotal(190);
        s4.setCode("HRM");
        s4.setReleaseDate("2019/05/10");
        s4.setSymbol("images/symbol-hr");
        s4.setLogo("images/logo-hr");
        s4.addCard(c4);

        Legality l1 = new Legality();
        l1.setId(1);
        l1.setCard(c1);
        l1.setExpanded(true);
        l1.setStandard(true);
        l1.setUnlimited(true);

        Legality l2 = new Legality();
        l2.setId(2);
        l2.setCard(c2);
        l2.setExpanded(true);
        l2.setStandard(true);
        l2.setUnlimited(true);

        Legality l3 = new Legality();
        l3.setId(3);
        l3.setCard(c3);
        l3.setExpanded(true);
        l3.setStandard(true);
        l3.setUnlimited(true);

        Legality l4 = new Legality();
        l4.setId(4);
        l4.setCard(c);
        l4.setExpanded(true);
        l4.setStandard(true);
        l4.setUnlimited(true);

        rarityRepository.save(r);
        rarityRepository.save(r1);
        rarityRepository.save(r2);
        rarityRepository.save(r3);
        rarityRepository.save(r4);
        setRepository.save(s);
        setRepository.save(s1);
        setRepository.save(s2);
        setRepository.save(s3);
        setRepository.save(s4);
        legalityRepository.save(l1);
        legalityRepository.save(l2);
        legalityRepository.save(l3);
        legalityRepository.save(l4);

        PokeUser u1 = new PokeUser();
        u1.setUsername("pedropascal");
        u1.setPassword("Asdas@123");
        u1.setEnabled(true);
        u1.setEmail("pedropascal@gmail.com");
        u1.setRoles("user admin");
        u1.setMemberSince(new Date());

        PokeUser u2 = new PokeUser();
        u2.setUsername("yulianms");
        u2.setPassword("Qwerty321@");
        u2.setEnabled(true);
        u2.setEmail("yulianms@gmail.com");
        u2.setRoles("user");
        u2.setMemberSince(new Date());

        PokeUser u3 = new PokeUser();
        u3.setUsername("harry");
        u3.setPassword("123456asdaS@");
        u3.setEnabled(true);
        u3.setEmail("harry@gmail.com");
        u3.setRoles("user");
        u3.setMemberSince(new Date());

        pokeUserService.create(u1);
        pokeUserService.create(u2);
        pokeUserService.create(u3);

        Wishlist wl1 = new Wishlist();
        wl1.setUser(u1);
        wl1.setId(1);
        wl1.setCards(cardTrackers);

        Wishlist wl2 = new Wishlist();
        wl2.setUser(u2);
        wl2.setId(2);
        wl2.setCards(null);

        CardTracker ct = new CardTracker();
        ct.setCard(c1);
        ct.setActive(true);
        ct.setTargetPrice(40f);
        ct.setWishlist(wl1);

        CardTracker ct2 = new CardTracker();
        ct2.setCard(c2);
        ct2.setActive(false);
        ct2.setTargetPrice(14f);
        ct2.setWishlist(wl1);

        CardTracker ct3 = new CardTracker();
        ct3.setCard(c3);
        ct3.setActive(true);
        ct3.setTargetPrice(56f);
        ct3.setWishlist(wl1);

        // cardTrackers = new ArrayList<>();
        // cardTrackers.add(ct);

        // wishlistRepository.save(wl1);
        // wishlistRepository.save(wl2);

        // cardTrackerRepository.save(ct);
        // cardTrackerRepository.save(ct2);
        // cardTrackerRepository.save(ct3);

    }
}
