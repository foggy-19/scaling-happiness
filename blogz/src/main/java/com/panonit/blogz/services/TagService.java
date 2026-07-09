package com.panonit.blogz.services;

import com.panonit.blogz.domain.dtos.TagDto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TagService {

    List<TagDto> getAllTags();

    List<TagDto> createTags(Set<String> tags);

    void deleteTag(UUID id);
}
