package be.vdab.movies.reservaties;

import be.vdab.movies.films.FilmNietGevondenException;
import be.vdab.movies.films.FilmRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
public class ReservatieService {

    private final ReservatieRepository reservatieRepository;
    private final FilmRepository filmRepository;
    private final KlantRepository klantRepository;

    ReservatieService(ReservatieRepository reservatieRepository, FilmRepository filmRepository, KlantRepository klantRepository) {
        this.reservatieRepository = reservatieRepository;
        this.filmRepository = filmRepository;
        this.klantRepository = klantRepository;
    }

    @Transactional
    void create(NieuweReservatie nieuweReservatie) {
        var film = filmRepository.findAndLockById(nieuweReservatie.filmId())
                .orElseThrow(() -> new FilmNietGevondenException(nieuweReservatie.filmId()));
        var reservatie = new Reservatie(nieuweReservatie.klantId(),
                nieuweReservatie.filmId(), LocalDateTime.now());
        klantRepository.findById(nieuweReservatie.klantId())
                        .orElseThrow(() -> new KlantNietGevondenException(nieuweReservatie.klantId()));
        reservatieRepository.create(reservatie);
        film.reserveer();
        filmRepository.updateGereserveerd(nieuweReservatie.filmId());
    }

}
