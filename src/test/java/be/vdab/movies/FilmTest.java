package be.vdab.movies;

import be.vdab.movies.films.Film;
import be.vdab.movies.films.OnvoldoendeVoorraadException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class FilmTest {

    @Test
    void reserveerVerhoogtGereserveerd() {
        var film = new Film(0, 1, "test", 1, 0, BigDecimal.valueOf(5));
        film.reserveer();
        assertThat(film.getGereserveerd()).isEqualTo(1);
    }

    @Test
    void reserveerMisluktBijOnvoldoendeVoorraad() {
        var film = new Film(0, 1, "test", 1, 1, BigDecimal.valueOf(5));
        assertThatExceptionOfType(OnvoldoendeVoorraadException.class)
                .isThrownBy(film::reserveer);
    }

}
