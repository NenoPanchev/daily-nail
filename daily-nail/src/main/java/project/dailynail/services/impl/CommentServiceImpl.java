package project.dailynail.services.impl;

import org.springframework.stereotype.Service;
import project.dailynail.repositories.CommentRepository;
import project.dailynail.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
}
