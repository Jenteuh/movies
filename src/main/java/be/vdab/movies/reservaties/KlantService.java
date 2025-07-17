package be.vdab.movies.reservaties;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KlantService {

    private final KlantRepository klantRepository;

    public KlantService(KlantRepository klantRepository) {
        this.klantRepository = klantRepository;
    }

    List<Klant> findByStukNaam(String stukNaam) {
        return klantRepository.findByStukNaam(stukNaam);
    }

}
