package com.sutorimingu.no.sekai.exceptions;

/**
 * @author sei3
 * on 31/07/2021.
 */
public class AnimeNotFoundException extends RuntimeException {

    public AnimeNotFoundException(Long id) {
        super("Could not find anime " + id);
    }

    public AnimeNotFoundException(String name) {
        super("Could not find anime " + name);
    }
}
