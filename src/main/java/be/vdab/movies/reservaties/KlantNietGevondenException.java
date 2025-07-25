package be.vdab.movies.reservaties;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class KlantNietGevondenException extends RuntimeException {
    public KlantNietGevondenException(long id) {
        super("Klant niet gevonden. Id: " + id);
    }
}
