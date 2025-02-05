package cards.poketracker.searchengine.pokeuser;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;

public class UserPrincipal implements UserDetails {

    private PokeUser pokeUser;

    public UserPrincipal(PokeUser pokeUser) {
        this.pokeUser = pokeUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Gets user's roles and maps them into a list of SimpleGrantedAuthority instances.
        System.out.println(Arrays.stream(StringUtils.tokenizeToStringArray(this.pokeUser.getRoles(), " "))
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList());
        return Arrays.stream(StringUtils.tokenizeToStringArray(this.pokeUser.getRoles(), " "))
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .toList();
    }

    @Override
    public String getPassword() {
        return this.pokeUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.pokeUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.pokeUser.isEnabled();
    }

    public PokeUser getPokeUser() {
        return pokeUser;
    }
}
