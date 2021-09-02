package com.sutorimingu.no.sekai.dto;

import com.sutorimingu.no.sekai.model.Anime;
import lombok.Data;

import javax.persistence.Column;
import java.sql.Date;

/**
 * @author ufhopla
 * on 26/08/2021.
 */

@Data
public class AnimeDto {
    private Long id;
    private String title;
    private String genre;
    @Column(columnDefinition="TEXT")
    private String synopsis;
    private Date aired;
    private Date ended;
    private Float rating;

    public AnimeDto(Anime anime) {
        this.id = anime.getId();
        this.title = anime.getTitle();
        this.genre = anime.getGenre();
        this.synopsis = anime.getSynopsis();
        this.aired = anime.getAired();
        this.ended = anime.getEnded();
        this.rating = anime.getRating();
    }
}
