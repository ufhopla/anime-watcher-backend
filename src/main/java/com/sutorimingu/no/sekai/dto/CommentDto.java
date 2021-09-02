package com.sutorimingu.no.sekai.dto;

import com.sutorimingu.no.sekai.model.Comment;
import lombok.Data;

import java.sql.Date;

/**
 * @author sei3
 * on 20/08/2021.
 */
@Data
public class CommentDto {

    String username;
    String message;
    Date post_date;


    public CommentDto(Comment comment){
        if(comment.getCreator()!=null){
            this.username = comment.getCreator().getUsername();
        }
        this.message=comment.getMessage();
        this.post_date=comment.getPost_date();
    }
}
