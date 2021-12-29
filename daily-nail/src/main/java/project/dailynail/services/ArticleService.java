package project.dailynail.services;

import org.springframework.data.domain.Page;
import project.dailynail.models.binding.ArticleSearchBindingModel;
import project.dailynail.models.service.ArticleCreateServiceModel;
import project.dailynail.models.view.ArticlesAllViewModel;

import java.io.IOException;
import java.util.List;

public interface ArticleService {

    void createArticle(ArticleCreateServiceModel articleCreateServiceModel) throws IOException;

    Page<ArticlesAllViewModel> getAllArticlesForAdminPanel();
    Page<ArticlesAllViewModel> getAllArticlesForAdminPanel(Integer page);

    List<String> getTimePeriods();

    List<String> getArticleStatuses();

    Page<ArticlesAllViewModel> getFilteredArticles(ArticleSearchBindingModel articleSearchBindingModel);

    void test();
}
