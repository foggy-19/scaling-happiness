package com.panonit.blogz.controllers;

import com.panonit.blogz.domain.dtos.CreatePostRequest;
import com.panonit.blogz.domain.dtos.PostDto;
import com.panonit.blogz.domain.dtos.UpdatePostRequest;
import com.panonit.blogz.services.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService service;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID tagId
    ) {
        return new ResponseEntity<>(service.getAllPosts(categoryId, tagId), HttpStatus.OK);
    }

    @GetMapping(path = "/{post_id}")
    public ResponseEntity<PostDto> getPost(@PathVariable("post_id") UUID postId) {
        return new ResponseEntity<>(service.getPost(postId), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{post_id}")
    public ResponseEntity<Void> deletePost(@PathVariable("post_id") UUID postId) {
        service.deletePost(postId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/drafts")
    public ResponseEntity<List<PostDto>> getDrafts(@RequestAttribute UUID userId) {
        return new ResponseEntity<>(service.getDrafts(userId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(
            @RequestAttribute UUID userId,
            @Valid @RequestBody CreatePostRequest createPostRequest
    ) {
        return new ResponseEntity<>(service.createPost(userId, createPostRequest), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{post_id}")
    public ResponseEntity<PostDto> updatePost(
            @RequestAttribute UUID userId,
            @PathVariable("post_id") UUID postId,
            @Valid @RequestBody UpdatePostRequest updatePostRequest
    ) {
        return new ResponseEntity<>(service.updatePost(userId, postId, updatePostRequest), HttpStatus.OK);
    }
}
