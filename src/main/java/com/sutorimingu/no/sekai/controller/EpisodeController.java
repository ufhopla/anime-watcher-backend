package com.sutorimingu.no.sekai.controller;

import com.sutorimingu.no.sekai.dto.EpisodeDto;
import com.sutorimingu.no.sekai.exceptions.AnimeNotFoundException;
import com.sutorimingu.no.sekai.model.Episode;
import com.sutorimingu.no.sekai.repository.AnimeRepository;
import com.sutorimingu.no.sekai.repository.EpisodeRepository;
import com.sutorimingu.no.sekai.service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sei3
 * on 20/08/2021.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path="/episode")
public class EpisodeController {
    @Autowired
    private EpisodeRepository episodeRepository;
    @Autowired
    private AnimeRepository animeRepository;
    @Autowired
    private AnimeService animeService;

    @GetMapping("/list")
    List<EpisodeDto> listEpispodes(@RequestParam("anime_id") Long animeId) {
        return episodeRepository.findEpisodesByAnime_IdOrderBySeasonAscEpisodeNbAsc(animeId).stream()
                .map(EpisodeDto::new).collect(Collectors.toList());


    }

    @GetMapping("/list/{id}")
    Episode getEpisodesById(@PathVariable Long id) {
        return episodeRepository.findById(id)
                .orElseThrow(() -> new AnimeNotFoundException(id));
    }

}
