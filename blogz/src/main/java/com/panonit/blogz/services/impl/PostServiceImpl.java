package com.panonit.blogz.services.impl;

import com.panonit.blogz.domain.PostStatus;
import com.panonit.blogz.domain.dtos.CreatePostRequest;
import com.panonit.blogz.domain.dtos.PostDto;
import com.panonit.blogz.domain.dtos.UpdatePostRequest;
import com.panonit.blogz.domain.entities.Category;
import com.panonit.blogz.domain.entities.Post;
import com.panonit.blogz.domain.entities.Tag;
import com.panonit.blogz.domain.entities.User;
import com.panonit.blogz.mappers.PostMapper;
import com.panonit.blogz.repositories.CategoryRepository;
import com.panonit.blogz.repositories.PostRepository;
import com.panonit.blogz.repositories.TagRepository;
import com.panonit.blogz.repositories.UserRepository;
import com.panonit.blogz.services.PostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private static final int WORDS_PER_MINUTE = 100;

    private final PostMapper mapper;
    private final PostRepository postRepository;

    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PostDto> getAllPosts(UUID categoryId, UUID tagId) {
        List<Post> posts = new ArrayList<>();

        if (categoryId != null && tagId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with id " + categoryId));
            Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new EntityNotFoundException("Tag not found with id " + tagId));

            posts.addAll(postRepository.findAllByStatusAndCategoryAndTagsContaining(PostStatus.PUBLISHED, category, tag));
        }

        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with id " + categoryId));

            posts.addAll(postRepository.findAllByStatusAndCategoryContaining(PostStatus.PUBLISHED, category));
        }

        if (tagId != null) {
            Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new EntityNotFoundException("Tag not found with id " + tagId));

            posts.addAll(postRepository.findAllByStatusAndTagsContaining(PostStatus.PUBLISHED, tag));
        }

        if (categoryId == null && tagId == null) {
            posts.addAll(postRepository.findAllByStatus(PostStatus.PUBLISHED));
        }

        return posts.stream().map(mapper::toDto).toList();
    }

    @Override
    public List<PostDto> getDrafts(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));

        return postRepository.findAllByAuthorAndStatus(user, PostStatus.DRAFT).stream().map(mapper::toDto).toList();
    }

    @Transactional
    @Override
    public PostDto createPost(UUID userId, CreatePostRequest createPostRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));

        Post post = new Post();

        post.setTitle(createPostRequest.getTitle());
        post.setContent(createPostRequest.getContent());
        post.setStatus(createPostRequest.getStatus());
        post.setReadingTime(calculateReadingTime(createPostRequest.getContent()));
        post.setAuthor(user);

        Category category = categoryRepository.findById(createPostRequest.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id " + createPostRequest.getCategoryId()));

        post.setCategory(category);

        List<Tag> tags = tagRepository.findAllById(createPostRequest.getTagIds());
        if (tags.size() != createPostRequest.getTagIds().size()) {
            throw new EntityNotFoundException("Not all specified tag IDs exist");
        }
        post.setTags(new HashSet<>(tags));

        return mapper.toDto(postRepository.save(post));
    }

    @Transactional
    @Override
    public PostDto updatePost(UUID userId, UUID postId, UpdatePostRequest updatePostRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id " + postId));

        if (user.getId() != post.getAuthor().getId()) {
            throw new IllegalStateException("User not authorized to update post");
        }

        String content = updatePostRequest.getContent();
        post.setTitle(updatePostRequest.getTitle());
        post.setContent(content);
        post.setStatus(updatePostRequest.getStatus());
        post.setReadingTime(calculateReadingTime(content));

        if (post.getCategory().getId() != updatePostRequest.getCategoryId()) {
            Category category = categoryRepository.findById(updatePostRequest.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with id " + updatePostRequest.getCategoryId()));
            post.setCategory(category);
        }

        Set<UUID> updateTagIds = updatePostRequest.getTagIds();
        Set<UUID> existingTagIds = post.getTags().stream().map(Tag::getId).collect(Collectors.toSet());

        if (!existingTagIds.equals(updateTagIds)) {
            List<Tag> tags = tagRepository.findAllById(updateTagIds);
            post.setTags(new HashSet<>(tags));
        }

        return mapper.toDto(postRepository.save(post));
    }

    @Override
    public PostDto getPost(UUID postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id " + postId));

        return mapper.toDto(post);
    }

    @Override
    public void deletePost(UUID postId) {
        if (postRepository.existsById(postId)) {
            postRepository.deleteById(postId);
        }
    }

    private Integer calculateReadingTime(String content) {
        if (content == null || content.isEmpty()) return 0;

        int wordCount = content.trim().split("\\s+").length;

        return (int) Math.ceil((double) wordCount / WORDS_PER_MINUTE);
    }
}
