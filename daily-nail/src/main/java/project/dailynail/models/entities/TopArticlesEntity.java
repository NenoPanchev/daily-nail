package project.dailynail.models.entities;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.LinkedHashSet;
import java.util.Set;
@Entity
public class TopArticlesEntity extends BaseEntity{
    @Transient
    private Set<ArticleEntity> topArticles = new LinkedHashSet<>();

    public TopArticlesEntity() {
    }

    @Transient
    public Set<ArticleEntity> getTopArticles() {
        return topArticles;
    }

    public TopArticlesEntity setTopArticles(Set<ArticleEntity> topArticles) {
        this.topArticles = topArticles;
        return this;
    }
}
