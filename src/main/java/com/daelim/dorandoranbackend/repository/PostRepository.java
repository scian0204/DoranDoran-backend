package com.daelim.dorandoranbackend.repository;

import com.daelim.dorandoranbackend.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository  extends JpaRepository<Post, Integer> {
    Page<Post> findAllByApartId(Pageable pageable, Integer boardId);
    Page<Post> findAllByTitleLikeIgnoreCaseOrContentLikeIgnoreCaseOrUserIdLike(Pageable pageable, String title, String content, String userId);

    Page<Post> findAllByUserId(Pageable pageable, String userId);
}
