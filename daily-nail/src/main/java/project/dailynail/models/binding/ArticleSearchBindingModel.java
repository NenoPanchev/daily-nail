package project.dailynail.models.binding;

public class ArticleSearchBindingModel {
    private String keyWord;
    private String category;
    private String author;
    private String timePeriod;
    private String status;
    private Integer page;

    public ArticleSearchBindingModel() {
    }

    public String getKeyWord() {
        return keyWord;
    }

    public ArticleSearchBindingModel setKeyWord(String keyWord) {
        this.keyWord = keyWord;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public ArticleSearchBindingModel setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public ArticleSearchBindingModel setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public ArticleSearchBindingModel setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public ArticleSearchBindingModel setStatus(String status) {
        this.status = status;
        return this;
    }

    public Integer getPage() {
        return page;
    }

    public ArticleSearchBindingModel setPage(Integer page) {
        this.page = page;
        return this;
    }
}
