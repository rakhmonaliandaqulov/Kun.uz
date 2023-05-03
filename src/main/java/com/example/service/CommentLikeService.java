package com.example.service;

import com.example.dto.comment.CommentLikeDto;
import com.example.repository.CommentLikeRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentLikeService {
    @Autowired
    private CommentLikeRepository commentLikeRepository;
    public Boolean like(Integer id, CommentLikeDto dto) {
        return null;
    }

    public Boolean disLike(Integer id, CommentLikeDto dto) {
        return null;
    }

    public Boolean remove(Integer id) {
        return null;
    }
}
