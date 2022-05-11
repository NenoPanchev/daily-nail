package project.dailynail.models.view;

import java.util.List;

public class ArticlePageVModel {
    private List<ArticleViewModel> content;
    private Long totalElements;
    private Integer totalPages;

    public ArticlePageVModel() {
    }

    public List<ArticleViewModel> getContent() {
        return content;
    }

    public ArticlePageVModel setContent(List<ArticleViewModel> content) {
        this.content = content;
        return this;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public ArticlePageVModel setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
        return this;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public ArticlePageVModel setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
        return this;
    }
}
