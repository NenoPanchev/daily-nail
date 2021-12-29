package project.dailynail.models.binding;

public class ArticleSearchBindingModel {
    private String keyWord;
    private String category;
    private String authorName;
    private String timePeriod;
    private String articleStatus;
    private Integer page;

    public ArticleSearchBindingModel() {
    }

    public ArticleSearchBindingModel(String keyWord, String category, String authorName, String timePeriod, String articleStatus, Integer page) {
        this.keyWord = keyWord;
        this.category = category;
        this.authorName = authorName;
        this.timePeriod = timePeriod;
        this.articleStatus = articleStatus;
        this.page = page;
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

    public String getAuthorName() {
        return authorName;
    }

    public ArticleSearchBindingModel setAuthorName(String authorName) {
        this.authorName = authorName;
        return this;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public ArticleSearchBindingModel setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
        return this;
    }

    public String getArticleStatus() {
        return articleStatus;
    }

    public ArticleSearchBindingModel setArticleStatus(String articleStatus) {
        this.articleStatus = articleStatus;
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
