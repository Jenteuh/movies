package be.vdab.movies.reservaties;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReservatieController {

    private final ReservatieService reservatieService;

    ReservatieController(ReservatieService reservatieService) {
        this.reservatieService = reservatieService;
    }

    @PostMapping("reservaties")
    void reserveer(@RequestBody @Valid List<NieuweReservatie> nieuweReservaties) {
        for ( NieuweReservatie nieuweReservatie : nieuweReservaties)
            reservatieService.create(nieuweReservatie);
    }

}
