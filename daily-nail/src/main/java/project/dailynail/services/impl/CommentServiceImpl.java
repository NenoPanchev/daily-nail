package project.dailynail.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.dailynail.constants.GlobalConstants;
import project.dailynail.models.dtos.json.CommentEntityExportDto;
import project.dailynail.models.dtos.json.UserEntityExportDto;
import project.dailynail.models.entities.ArticleEntity;
import project.dailynail.models.entities.CommentEntity;
import project.dailynail.models.entities.UserEntity;
import project.dailynail.models.service.CommentServiceModel;
import project.dailynail.models.validators.ServiceLayerValidationUtil;
import project.dailynail.repositories.CommentRepository;
import project.dailynail.services.ArticleService;
import project.dailynail.services.CommentService;
import project.dailynail.services.UserService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final ArticleService articleService;
    private final UserService userService;
    private final ServiceLayerValidationUtil serviceLayerValidationUtil;
    private final Gson gson;

    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper, ArticleService articleService, UserService userService, ServiceLayerValidationUtil serviceLayerValidationUtil, Gson gson) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.articleService = articleService;
        this.userService = userService;
        this.serviceLayerValidationUtil = serviceLayerValidationUtil;
        this.gson = gson;
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

    @Override
    public List<CommentEntityExportDto> exportComments() {
        return commentRepository.findAll()
                .stream()
                .map(entity -> modelMapper.map(entity, CommentEntityExportDto.class)
                .setTimePosted(entity.getTimePosted().toString().substring(0, 16)))
                .collect(Collectors.toList());

    }

    @Override
    public void seedComments() throws FileNotFoundException {
        CommentEntityExportDto[] commentEntityExportDtos = gson
                .fromJson(new FileReader(GlobalConstants.COMMENTS_FILE_PATH), CommentEntityExportDto[].class);

        List<CommentEntity> commentEntities = Arrays.stream(commentEntityExportDtos)
                .map(dto -> modelMapper.map(dto, CommentEntity.class)
                        .setAuthor(modelMapper.map(userService.findByEmail(dto.getAuthorEmail()), UserEntity.class))
                        .setTimePosted(articleService.getLocalDateTimeFromString(dto.getTimePosted()))
                        .setArticle(modelMapper.map(articleService.getArticleByUrl(dto.getArticleUrl()), ArticleEntity.class)))
                .collect(Collectors.toList());

        commentRepository.saveAllAndFlush(commentEntities);
    }
}
