package be.vdab.movies.films;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("genres/{id}/films")
    public List<Film> findByGenreId(@PathVariable int id) {
        return filmService.findbyGenreId(id);
    }

}
