package project.dailynail.services;

import org.springframework.data.domain.Page;
import project.dailynail.models.binding.ArticleSearchBindingModel;
import project.dailynail.models.entities.ArticleEntity;
import project.dailynail.models.service.ArticleCreateServiceModel;
import project.dailynail.models.view.ArticlesAllViewModel;
import project.dailynail.models.view.ArticlesPageViewModel;

import java.io.IOException;
import java.util.List;

public interface ArticleService {

    void createArticle(ArticleCreateServiceModel articleCreateServiceModel) throws IOException;

    ArticlesPageViewModel getAllArticlesForAdminPanel();
    ArticlesPageViewModel getAllArticlesForAdminPanel(Integer page);

    List<String> getTimePeriods();

    List<String> getArticleStatuses();

    ArticlesPageViewModel getFilteredArticles(ArticleSearchBindingModel articleSearchBindingModel);

    Page<ArticleEntity> test();
}
