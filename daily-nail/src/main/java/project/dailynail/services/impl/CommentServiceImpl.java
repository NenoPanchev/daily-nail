package project.dailynail.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.dailynail.models.entities.CommentEntity;
import project.dailynail.models.service.CommentServiceModel;
import project.dailynail.models.validators.ServiceLayerValidationUtil;
import project.dailynail.repositories.CommentRepository;
import project.dailynail.services.ArticleService;
import project.dailynail.services.CommentService;
import project.dailynail.services.UserService;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final ArticleService articleService;
    private final UserService userService;
    private final ServiceLayerValidationUtil serviceLayerValidationUtil;

    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper, ArticleService articleService, UserService userService, ServiceLayerValidationUtil serviceLayerValidationUtil) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.articleService = articleService;
        this.userService = userService;
        this.serviceLayerValidationUtil = serviceLayerValidationUtil;
    }

    @Override
    public void add(CommentServiceModel commentServiceModel, String id) {
        serviceLayerValidationUtil.validate(commentServiceModel);

        commentServiceModel
                .setAuthor(userService.getPrincipal())
                .setArticle(articleService.getArticleById(id))
                .setLikes(0)
                .setDislikes(0)
                .setTimePosted(LocalDateTime.now());
        commentRepository.save(modelMapper.map(commentServiceModel, CommentEntity.class));
    }

    @Override
    public void delete(String id) {
        commentRepository.deleteById(id);
    }
}
