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
@Sql("/klanten.sql")
@AutoConfigureMockMvc
public class KlantControllerTest {

    private final static String KLANTEN_TABLE = "klanten";
    private final MockMvcTester mockMvcTester;
    private final JdbcClient jdbcClient;

    public KlantControllerTest(MockMvcTester mockMvcTester, JdbcClient jdbcClient) {
        this.mockMvcTester = mockMvcTester;
        this.jdbcClient = jdbcClient;
    }

    @Test
    void findByStukNaamVindtDeJuisteKlanten() {
        var response = mockMvcTester.get().uri("/klanten")
                .queryParam("stukNaam", "testFamilienaam1");
        assertThat(response).hasStatusOk()
                .bodyJson()
                .extractingPath("length()")
                .isEqualTo(JdbcTestUtils.countRowsInTableWhere(jdbcClient, KLANTEN_TABLE,
                        """
                                voornaam or familienaam like '%testFamilienaam1%'
                                """));
    }
}
