package com.anonymous.algacomments.comment.service.api.controller;

import com.anonymous.algacomments.comment.service.api.client.ModerationServiceClient;
import com.anonymous.algacomments.comment.service.api.model.CommentInput;
import com.anonymous.algacomments.comment.service.api.model.CommentOutput;
import com.anonymous.algacomments.comment.service.api.model.ModerationInput;
import com.anonymous.algacomments.comment.service.common.IdGenerator;
import com.anonymous.algacomments.comment.service.domain.model.Comment;
import com.anonymous.algacomments.comment.service.domain.model.CommentId;
import com.anonymous.algacomments.comment.service.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;
    private final ModerationServiceClient moderationServiceClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentOutput create(@RequestBody CommentInput commentInput) {
        UUID commentId = IdGenerator.generateTimeBasedUUID();
        Comment comment = Comment.builder()
                .id(new CommentId(commentId))
                .author(commentInput.getAuthor())
                .text(commentInput.getText())
                .build();
        Boolean approved = moderationServiceClient.getModeration(ModerationInput.builder()
                .commentId(commentId)
                .text(commentInput.getText())
                .build()).getApproved();

        if (approved.equals(Boolean.TRUE)) {
            comment = commentRepository.save(comment);
            return convertToRepresentationModel(comment);
        } else {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }

    @GetMapping("{commentId}")
    public CommentOutput getOne(@PathVariable UUID commentId) {
        Comment comment = commentRepository.findById(new CommentId(commentId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return convertToRepresentationModel(comment);
    }

    @GetMapping
    public Page<CommentOutput> search(@PageableDefault Pageable pageable) {
        Page<Comment> comments = commentRepository.findAll(pageable);
        return comments.map(this::convertToRepresentationModel);
    }

    private CommentOutput convertToRepresentationModel(Comment comment) {
        return CommentOutput.builder()
                .id(comment.getId().getValue())
                .author(comment.getAuthor())
                .text(comment.getText())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
