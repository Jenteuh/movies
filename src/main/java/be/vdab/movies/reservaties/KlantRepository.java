package be.vdab.movies.reservaties;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KlantRepository {

    private final JdbcClient jdbcClient;

    public KlantRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    List<Klant> findByStukNaam(String stukNaam) {
        var sql = """
                select id, familienaam, voornaam, straatnummer, postcode, gemeente
                from klanten
                where voornaam or familienaam like ?
                order by familienaam, voornaam
                """;
        return jdbcClient.sql(sql)
                .params("%" + stukNaam + "%")
                .query(Klant.class)
                .list();
    }
}
