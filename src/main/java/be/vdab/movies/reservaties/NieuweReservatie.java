package be.vdab.movies.reservaties;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record NieuweReservatie(@NotNull @Positive long klantId, @NotNull @Positive long filmId) {}
