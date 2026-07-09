package com.panonit.blogz.services;

import com.panonit.blogz.domain.dtos.CreatePostRequest;
import com.panonit.blogz.domain.dtos.PostDto;
import com.panonit.blogz.domain.dtos.UpdatePostRequest;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface PostService {

    List<PostDto> getAllPosts(UUID categoryId, UUID tagId);

    List<PostDto> getDrafts(UUID userId);

    PostDto createPost(UUID userId, CreatePostRequest createPostRequest);

    PostDto updatePost(UUID userId, UUID postId, UpdatePostRequest updatePostRequest);

    PostDto getPost(UUID postId);

    void deletePost(UUID postId);
}
