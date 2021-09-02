package com.sutorimingu.no.sekai.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

/**
 * @author sei3
 * on 20/08/2021.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer season;
    private Integer episodeNb;
    private Date aired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "anime_id")
    public Anime anime;


    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Episode)) return false;
        final Episode episode = (Episode) o;
        return Objects.equals(getId(), episode.getId()) && Objects.equals(getSeason(), episode.getSeason()) && Objects.equals(getEpisodeNb(), episode.getEpisodeNb()) && Objects.equals(getAired(), episode.getAired()) && Objects.equals(getAnime(), episode.getAnime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSeason(), getEpisodeNb(), getAired(), getAnime());
    }

    @Override
    public String toString() {
        return "Episode{" +
                "id=" + id +
                ", season=" + season +
                ", episodeNb=" + episodeNb +
                ", aired=" + aired +
                ", anime=" + anime +
                '}';
    }
}
