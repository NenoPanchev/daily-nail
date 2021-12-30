package project.dailynail.models.view;

import java.util.List;

public class ArticlesPageViewModel {
    private List<ArticlesAllViewModel> content;
    private Long totalElements;
    private Integer totalPages;

    public ArticlesPageViewModel() {
    }

    public List<ArticlesAllViewModel> getContent() {
        return content;
    }

    public ArticlesPageViewModel setContent(List<ArticlesAllViewModel> content) {
        this.content = content;
        return this;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public ArticlesPageViewModel setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
        return this;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public ArticlesPageViewModel setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
        return this;
    }
}
