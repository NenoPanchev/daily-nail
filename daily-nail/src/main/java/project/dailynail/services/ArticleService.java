package project.dailynail.services;

import project.dailynail.models.binding.ArticleEditBindingModel;
import project.dailynail.models.binding.ArticleSearchBindingModel;
import project.dailynail.models.dtos.json.ArticleEntityExportDto;
import project.dailynail.models.entities.enums.CategoryNameEnum;
import project.dailynail.models.service.ArticleCreateServiceModel;
import project.dailynail.models.service.ArticleServiceModel;
import project.dailynail.models.view.ArticlePageVModel;
import project.dailynail.models.view.ArticlePreViewModel;
import project.dailynail.models.view.ArticleViewModel;
import project.dailynail.models.view.ArticlesPageViewModel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface ArticleService {

    void createArticle(ArticleCreateServiceModel articleCreateServiceModel) throws IOException;

    ArticlesPageViewModel getAllArticlesForAdminPanel();

    ArticlesPageViewModel getAllArticlesForAdminPanel(Integer page);
    ArticlePageVModel getAllArticlesByCategory(String category, LocalDateTime now);
    ArticlePageVModel getAllArticlesByCategory(String category, LocalDateTime now, Integer page);

    List<String> getTimePeriods();

    List<String> getArticleStatuses();

    ArticlesPageViewModel getFilteredArticles(ArticleSearchBindingModel articleSearchBindingModel);

    ArticleEditBindingModel getArticleEditBindingModelById(String id);

    void deleteArticle(String id);

    void editArticle(ArticleEditBindingModel articleEditBindingModel) throws IOException;

    ArticlePreViewModel getNewestArticleByCategoryName(CategoryNameEnum categoryNameEnum, LocalDateTime now);

    List<ArticlePreViewModel> getFourArticlesByCategoryName(CategoryNameEnum categoryNameEnum, LocalDateTime now);

    List<ArticlePreViewModel> getLatestFiveArticles(LocalDateTime now);

    List<ArticlePreViewModel> getLatestNineArticles(LocalDateTime now);

    void setTopFalse(String poppedOutId);

    void setTopTrue(String id);

    List<ArticlePreViewModel> getTopArticles(LocalDateTime now);

    List<String> getAllTopArticlesIds(LocalDateTime now);

    ArticleViewModel getArticleViewModelByUrl(String url);

    ArticleServiceModel getArticleById(String id);
    String getArticleUrlById(String id);

    String getArticleUrlByCommentId(String id);

    List<ArticleEntityExportDto> exportArticles();
    void seedArticles() throws FileNotFoundException;
    ArticleServiceModel getArticleByUrl(String url);
    LocalDateTime getLocalDateTimeFromString(String time);
    boolean hasArticles();
}
