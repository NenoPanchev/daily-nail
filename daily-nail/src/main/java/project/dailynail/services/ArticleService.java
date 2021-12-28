package project.dailynail.services;

import org.springframework.data.domain.Page;
import project.dailynail.models.service.ArticleCreateServiceModel;
import project.dailynail.models.view.ArticlesAllViewModel;

import java.io.IOException;
import java.util.List;

public interface ArticleService {

    void createArticle(ArticleCreateServiceModel articleCreateServiceModel) throws IOException;

    Page<ArticlesAllViewModel> getAllArticlesForAdminPanel(Integer page, Integer pageSize);

    List<String> getTimePeriods();

    List<String> getArticleStatuses();
}
