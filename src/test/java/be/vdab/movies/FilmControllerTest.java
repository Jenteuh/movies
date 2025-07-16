package be.vdab.movies;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Sql("/films.sql")
@AutoConfigureMockMvc
public class FilmControllerTest {

    private final static String FILMS_TABLE = "films";
    private final MockMvcTester mockMvcTester;
    private final JdbcClient jdbcClient;

    public FilmControllerTest(MockMvcTester mockMvcTester, JdbcClient jdbcClient) {
        this.mockMvcTester = mockMvcTester;
        this.jdbcClient = jdbcClient;
    }

    private int genreIdVanTestFilm() {
        return jdbcClient.sql("select genreId from films where titel = 'test1'")
                .query(Integer.class)
                .single();
    }

    @Test
    void findByGenreIdVindtAlleFilmsVanEenGenre() {
        var id = genreIdVanTestFilm();
        var aantalFilms = JdbcTestUtils.countRowsInTableWhere(
                jdbcClient, FILMS_TABLE, "genreId = " + id);
        var response = mockMvcTester.get().uri("/genres/{id}/films", id);
        assertThat(response).hasStatusOk()
                .bodyJson()
                .extractingPath("length()")
                .isEqualTo(aantalFilms);
    }
}
