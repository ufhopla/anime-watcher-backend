package com.sutorimingu.no.sekai.controller;

import com.sutorimingu.no.sekai.exceptions.AnimeNotFoundException;
import com.sutorimingu.no.sekai.model.Anime;
import com.sutorimingu.no.sekai.repository.AnimeRepository;
import com.sutorimingu.no.sekai.scrapper.FullScrapperToDelete;
import com.sutorimingu.no.sekai.service.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author sei3
 * on 31/07/2021.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path="/anime")
public class AnimeController {

    @Autowired
    private AnimeRepository repository;

    @Autowired
    private FullScrapperToDelete scrapper;

    @Autowired
    private AnimeService animeService;


    @GetMapping("/scrap")
    String scrap() {
        scrapper.scrapp();
        return "OK";
    }

//    @GetMapping("/list")
//    List<Anime> all() {
//        return IterableUtils.toList(repository.findAll());
//    }

    @GetMapping("/list")
    public ResponseEntity<List<Anime>> getAllAnimes(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "1000") Integer pageSize,
            @RequestParam(defaultValue = "rating") String sortBy)
    {
        List<Anime> list = animeService.getAllAnimes(pageNo, pageSize, sortBy);

        return new ResponseEntity<List<Anime>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/add")
    Anime newUser(@RequestBody Anime newAnime) {
        return repository.save(newAnime);
    }


    @GetMapping("/getAnimeById/{id}")
    Anime getAnimeById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AnimeNotFoundException(id));
    }

    @GetMapping("/getAnimeByTitle/{title}")
    List<Anime> getAnimeByTitle(@PathVariable String title) {
        return repository.findByTitleLike("%"+title+"%");
    }

    @PutMapping("/getAnimeById/{id}")
    public ResponseEntity<Anime> updateAnime(@PathVariable("id") long id, @RequestBody Anime anime) {
        Optional<Anime> animeData = repository.findById(id);

        if (animeData.isPresent()) {
            Anime _anime = animeData.get();
            _anime.setTitle(anime.getTitle());
            _anime.setGenre(anime.getGenre());
            _anime.setSynopsis(anime.getSynopsis());
            _anime.setAired(anime.getAired());
            _anime.setEnded(anime.getEnded());
            _anime.setRating(anime.getRating());
            _anime.setFileDB(anime.getFileDB());
            return new ResponseEntity<>(repository.save(_anime), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/getAnimeById/{id}")
    public ResponseEntity<HttpStatus> deleteAnime(@PathVariable("id") long id) {
        try {
            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
