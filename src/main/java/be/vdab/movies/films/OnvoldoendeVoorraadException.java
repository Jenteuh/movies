package be.vdab.movies.films;

public class OnvoldoendeVoorraadException extends RuntimeException {
    OnvoldoendeVoorraadException() {
        super("Onvoldoende voorraad van de film");
    }
}
