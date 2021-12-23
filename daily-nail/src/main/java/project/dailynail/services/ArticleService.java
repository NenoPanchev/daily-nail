package project.dailynail.services;

import project.dailynail.models.service.ArticleCreateServiceModel;

import java.io.IOException;

public interface ArticleService {

    void createArticle(ArticleCreateServiceModel articleCreateServiceModel) throws IOException;
}
