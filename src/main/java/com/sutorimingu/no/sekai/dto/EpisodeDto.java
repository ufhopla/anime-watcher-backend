package com.sutorimingu.no.sekai.dto;

import com.sutorimingu.no.sekai.model.Anime;
import com.sutorimingu.no.sekai.model.Episode;
import lombok.Data;

import java.sql.Date;

/**
 * @author sei3
 * on 20/08/2021.
 */
@Data
public class EpisodeDto {
    private Long id;
    private Integer season;
    private Integer episodeNb;
    private Date aired;
    private Long anime_id;

    public EpisodeDto(Episode episode) {
        this.id = episode.getId();
        this.season = episode.getSeason();
        this.episodeNb = episode.getSeason();
        this.aired = episode.getAired();
        final Anime anime = episode.getAnime();
        if (anime != null) {
            this.anime_id = anime.getId();
        }
    }

}
