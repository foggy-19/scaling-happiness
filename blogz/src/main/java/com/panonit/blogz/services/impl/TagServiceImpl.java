package com.panonit.blogz.services.impl;

import com.panonit.blogz.domain.dtos.TagDto;
import com.panonit.blogz.domain.entities.Tag;
import com.panonit.blogz.mappers.TagMapper;
import com.panonit.blogz.repositories.TagRepository;
import com.panonit.blogz.services.TagService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagMapper mapper;
    private final TagRepository repository;

    @Override
    public List<TagDto> getAllTags() {
        return repository.findAllWithPostCount().stream().map(mapper::toDto).toList();
    }

    @Transactional
    @Override
    public List<TagDto> createTags(Set<String> tags) {
        List<Tag> existingTags = repository.findByNameIn(tags);
        Set<String> existingTagNames = existingTags.stream().map(Tag::getName).collect(Collectors.toSet());

        List<Tag> newTags = tags.stream()
                .filter(name -> !existingTagNames.contains(name))
                .map(name -> Tag.builder().name(name).build())
                .toList();

        List<Tag> savedTags = new ArrayList<>();
        if (!newTags.isEmpty()) {
            savedTags = repository.saveAll(newTags);
        }

        savedTags.addAll(existingTags);

        return savedTags.stream().map(mapper::toDto).toList();
    }

    @Transactional
    @Override
    public void deleteTag(UUID id) {
        if (id == null) {
            return;
        }

        Optional<Tag> optional = repository.findById(id);
        if (optional.isEmpty()) {
            return;
        }

        if (!optional.get().getPosts().isEmpty()) {
            throw new IllegalStateException("Cannot delete tag with posts");
        }

        repository.deleteById(id);
    }
}
