package be.vdab.movies.films;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService {

    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    List<Film> findbyGenreId(int id) {
        return filmRepository.findByGenreId(id);
    }

}
