package be.vdab.movies.films;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

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

}
