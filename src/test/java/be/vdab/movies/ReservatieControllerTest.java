package be.vdab.movies;

import be.vdab.movies.reservaties.NieuweReservatie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Sql({"/klanten.sql", "/films.sql"})
@AutoConfigureMockMvc
public class ReservatieControllerTest {

    private final static String RESERVATIES_TABLE = "reservaties";
    private final static String FILMS_TABLE = "films";
    private final MockMvcTester mockMvcTester;
    private final JdbcClient jdbcClient;

    public ReservatieControllerTest(MockMvcTester mockMvcTester, JdbcClient jdbcClient) {
        this.mockMvcTester = mockMvcTester;
        this.jdbcClient = jdbcClient;
    }

    private int idVanTestFilm() {
        return jdbcClient.sql("select Id from films where titel = 'test1'")
                .query(Integer.class)
                .single();
    }

    private int idVanTestKlant() {
        return jdbcClient.sql("select Id from klanten where voornaam = 'testVoornaam1'")
                .query(Integer.class)
                .single();
    }

    @Test
    void reserveerMaaktEenReservatieEnVerhoogtGereserveerd() {
        var response = mockMvcTester.post()
                .uri("/reservaties")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                          {
                            "klantId":""" + idVanTestKlant() + """
                            ,
                            "filmId":""" + idVanTestFilm() + """
                          }
                        """
                );
        assertThat(response).hasStatusOk();
        assertThat(JdbcTestUtils.countRowsInTableWhere(jdbcClient, RESERVATIES_TABLE,
                "klantId = " + idVanTestKlant() + " and filmId = " + idVanTestFilm())).isOne();
        assertThat(JdbcTestUtils.countRowsInTableWhere(jdbcClient, FILMS_TABLE,
                "id = " + idVanTestFilm() + " and gereserveerd = 1")).isOne();
    }

    @ParameterizedTest
    @ValueSource(strings = {"reservatieZonderFilmId.json", "reservatieZonderKlantId.json",
    "reservatieMetNegatieveFilmId.json", "reservatieMetNegatieveKlantId.json"})
    void reserveerMetVerkeerdeDataMislukt(String bestandsnaam) throws Exception {
        var jsonData = new ClassPathResource(bestandsnaam)
                .getContentAsString(StandardCharsets.UTF_8);
        var response = mockMvcTester.post()
                .uri("/reservaties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonData);
        assertThat(response).hasStatus(HttpStatus.BAD_REQUEST);
    }

    @Test
    void reserveerMetOnbestaandeFilmGeeftNotFound() {
        var response = mockMvcTester.post()
                .uri("/reservaties")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                          {
                            "klantId":""" + idVanTestKlant() + """
                            ,
                            "filmId":""" + Long.MAX_VALUE + """
                          }
                        """
                );
        assertThat(response).hasStatus(HttpStatus.NOT_FOUND);
    }

    @Test
    void reserveerMetOnbestaandeKlantGeeftNotFound() {
        var response = mockMvcTester.post()
                .uri("/reservaties")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                          {
                            "klantId":""" + Long.MAX_VALUE + """
                            ,
                            "filmId":""" + idVanTestFilm() + """
                          }
                        """
                );
        assertThat(response).hasStatus(HttpStatus.NOT_FOUND);
}
}
