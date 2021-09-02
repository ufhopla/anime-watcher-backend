package com.sutorimingu.no.sekai.controller;

import com.sutorimingu.no.sekai.dto.CommentDto;
import com.sutorimingu.no.sekai.model.Anime;
import com.sutorimingu.no.sekai.model.Comment;
import com.sutorimingu.no.sekai.model.User;
import com.sutorimingu.no.sekai.payload.response.MessageResponse;
import com.sutorimingu.no.sekai.repository.CommentRepository;
import com.sutorimingu.no.sekai.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sei3
 * on 31/07/2021.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path="/comment")
public class CommentController {

    @Autowired
    private CommentRepository repository;


    @PostMapping("/add")
    public ResponseEntity<MessageResponse> newComment(Authentication authentication, @RequestParam("message") String message, @RequestParam("anime_id") Long animeId) {
        final Comment newComment = new Comment();
        final Anime anime = new Anime();
        anime.setId(animeId);
        newComment.setAnime(anime);
        final UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        if(user != null){
            final User creator = new User();
            creator.setId(user.getId());
            newComment.setCreator(creator);
            final Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            newComment.setPost_date(currentDate);
            newComment.setMessage(message);
            repository.save(newComment);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("SUCCESS"));
        }
        else{
            final String errorMsg = "Could not user for  " + authentication.getName()+ "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MessageResponse(errorMsg));

        }
    }


    @GetMapping("/getCommentByAnime/{id}")
    List<CommentDto> getUserById(@PathVariable Long id) {
        return repository.findByAnime_Id(id).stream().map(CommentDto::new).collect(Collectors.toList());
    }
}
