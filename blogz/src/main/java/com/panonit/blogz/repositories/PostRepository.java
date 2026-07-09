package com.panonit.blogz.repositories;

import com.panonit.blogz.domain.PostStatus;
import com.panonit.blogz.domain.entities.Category;
import com.panonit.blogz.domain.entities.Post;
import com.panonit.blogz.domain.entities.Tag;
import com.panonit.blogz.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

    List<Post> findAllByStatus(PostStatus status);

    List<Post> findAllByStatusAndTagsContaining(PostStatus status, Tag tag);

    List<Post> findAllByStatusAndCategoryContaining(PostStatus status, Category category);

    List<Post> findAllByStatusAndCategoryAndTagsContaining(PostStatus status, Category category, Tag tag);

    List<Post> findAllByAuthorAndStatus(User author, PostStatus status);
}
