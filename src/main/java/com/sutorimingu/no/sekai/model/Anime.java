package com.sutorimingu.no.sekai.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

/**
 * @author sei3
 * on 31/07/2021.
 */
@Entity
@Data
public class Anime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String genre;
    @Column(columnDefinition="TEXT")
    private String synopsis;
    private Date aired;
    private Date ended;
    private Float rating;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "files_id")
    public FileDB fileDB;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Anime)) return false;
        final Anime anime = (Anime) o;
        return Objects.equals(getId(), anime.getId()) && Objects.equals(getTitle(), anime.getTitle()) && Objects.equals(getGenre(), anime.getGenre()) && Objects.equals(getSynopsis(), anime.getSynopsis()) && Objects.equals(getAired(), anime.getAired()) && Objects.equals(getEnded(), anime.getEnded()) && Objects.equals(getRating(), anime.getRating()) && Objects.equals(getFileDB(), anime.getFileDB());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getGenre(), getSynopsis(), getAired(), getEnded(), getRating(), getFileDB());
    }

    @Override
    public String toString() {
        return "Anime{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", aired=" + aired +
                ", ended=" + ended +
                ", rating=" + rating +
                ", fileDB=" + fileDB +
                '}';
    }
}
