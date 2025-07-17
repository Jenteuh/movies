package be.vdab.movies.reservaties;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("klanten")
public class KlantController {

    private final KlantService klantService;

    public KlantController(KlantService klantService) {
        this.klantService = klantService;
    }

    @GetMapping(params={"stukNaam"})
    List<Klant> findByStukNaam(String stukNaam) {
        return klantService.findByStukNaam(stukNaam);
    }

}
