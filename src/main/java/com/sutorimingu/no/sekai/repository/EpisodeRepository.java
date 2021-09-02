package com.sutorimingu.no.sekai.repository;

import com.sutorimingu.no.sekai.model.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author sei3
 * on 20/08/2021.
 */
public interface EpisodeRepository extends JpaRepository<Episode, Long> {


    List<Episode> findEpisodesByAnime_IdOrderBySeasonAscEpisodeNbAsc(Long Anime_id);
}
