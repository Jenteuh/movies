package be.vdab.movies.films;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public class FilmRepository {

    private final JdbcClient jdbcClient;

    public FilmRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    List<Film> findByGenreId(int id) {
        var sql = """
                select id, genreId, titel, voorraad, gereserveerd, prijs
                from films
                where genreId = ?
                order by titel
                """;
        return jdbcClient.sql(sql)
                .param(id)
                .query(Film.class)
                .list();
    }

    public Optional<Film> findAndLockById(long id) {
        var sql = """
                select id, genreId, titel, voorraad, gereserveerd, prijs
                from films
                where id = ?
                for update
                """;
        return jdbcClient.sql(sql)
                .param(id)
                .query(Film.class)
                .optional();
    }

    public void updateGereserveerd(long id) {
        var sql = """
                update films
                set gereserveerd = gereserveerd + 1
                where id = ?
                """;
        if (jdbcClient.sql(sql).param(id).update() == 0) {
            throw new FilmNietGevondenException(id);
        }
    }

}
