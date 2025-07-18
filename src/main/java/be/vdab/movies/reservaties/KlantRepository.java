package be.vdab.movies.reservaties;

import be.vdab.movies.films.Film;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class KlantRepository {

    private final JdbcClient jdbcClient;

    public KlantRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    List<Klant> findByStukNaam(String stukNaam) {
        var sql = """
                select id, familienaam, voornaam, straatNummer, postcode, gemeente
                from klanten
                where familienaam like ?
                order by familienaam, voornaam
                """;
        return jdbcClient.sql(sql)
                .params("%" + stukNaam + "%")
                .query(Klant.class)
                .list();
    }

    public Optional<Klant>findById(long id) {
        var sql = """
                select id, familienaam, voornaam, straatNummer, postcode, gemeente
                from klanten
                where id = ?
                """;
        return jdbcClient.sql(sql)
                .param(id)
                .query(Klant.class)
                .optional();
    }
}
