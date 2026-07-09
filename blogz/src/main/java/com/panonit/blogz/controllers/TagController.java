package com.panonit.blogz.controllers;

import com.panonit.blogz.domain.dtos.CreateTagsRequest;
import com.panonit.blogz.domain.dtos.TagDto;
import com.panonit.blogz.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService service;

    @GetMapping
    public ResponseEntity<List<TagDto>> getAllTags() {
        return ResponseEntity.ok(service.getAllTags());
    }

    @PostMapping
    public ResponseEntity<List<TagDto>> createTags(@RequestBody CreateTagsRequest createTagsRequest) {
        return new ResponseEntity<>(service.createTags(createTagsRequest.getNames()), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{tag_id}")
    public ResponseEntity<Void> deleteTags(@PathVariable("tag_id") UUID id) {
        service.deleteTag(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
