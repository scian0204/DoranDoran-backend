package com.daelim.dorandoranbackend.repository;

import com.daelim.dorandoranbackend.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Page<Comment> findAllByPostId(Pageable pageable, Integer postId);
    Page<Comment> findAllByUserId(Pageable pageable, String userId);
}
