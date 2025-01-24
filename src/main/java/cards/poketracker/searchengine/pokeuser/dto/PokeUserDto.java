package cards.poketracker.searchengine.pokeuser.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.Date;

public record PokeUserDto(Long id,
                          @NotEmpty(message = "Username is required.")
                          String username,
                          @NotEmpty(message = "Email is required.")
                          String email,
                          boolean active,
                          String roles,
                          Date memberSince) {
}
