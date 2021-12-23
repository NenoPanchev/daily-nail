package project.dailynail.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.dailynail.models.service.ArticleCreateServiceModel;
import project.dailynail.models.validators.ServiceLayerValidationUtil;
import project.dailynail.repositories.ArticleRepository;
import project.dailynail.services.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;
    private final ServiceLayerValidationUtil serviceLayerValidationUtil;
    private final Gson gson;

    public ArticleServiceImpl(ArticleRepository articleRepository, ModelMapper modelMapper, ServiceLayerValidationUtil serviceLayerValidationUtil, Gson gson) {
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
        this.serviceLayerValidationUtil = serviceLayerValidationUtil;
        this.gson = gson;
    }

    @Override
    public void createArticle(ArticleCreateServiceModel articleCreateServiceModel) {
        serviceLayerValidationUtil.validate(articleCreateServiceModel);

    }
}
