package be.vdab.movies.reservaties;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class ReservatieRepository {

    private final JdbcClient jdbcClient;

    public ReservatieRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    void create(Reservatie reservatie) {
        var sql = """
                insert into reservaties(klantId, filmId, reservatie)
                values (?, ?, ?)
                """;
        jdbcClient.sql(sql)
                .params(reservatie.getKlantId(), reservatie.getFilmId(), reservatie.getReservatie())
                .update();
    }
}
