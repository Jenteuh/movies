package be.vdab.movies.films;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenreRepository {

    private final JdbcClient jdbcClient;

    public GenreRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    List<Genre> findAll() {
        var sql = """
                select id, naam
                from genres
                order by naam
                """;
        return jdbcClient.sql(sql)
                .query(Genre.class)
                .list();
    }

}
