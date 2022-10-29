package project.dailynail.services.impl;

import org.springframework.stereotype.Service;
import project.dailynail.services.ArticleService;

import java.time.LocalDateTime;
import java.util.ArrayDeque;


@Service
public class TopArticlesServiceImpl {
    private final ArticleService articleService;
    private ArrayDeque<String> topArticlesIds;

    public TopArticlesServiceImpl(ArticleService articleService) {
        this.articleService = articleService;
        topArticlesIds = new ArrayDeque<>();
        articleService.getAllTopArticlesIds(LocalDateTime.now())
                .forEach(id -> topArticlesIds.push(id));
    }

    public ArrayDeque<String> getTopArticlesIds() {
        return topArticlesIds;
    }

    public void add(String id) {
        topArticlesIds.add(id);
        if (topArticlesIds.size() > 4) {
            String poppedOutId = topArticlesIds.poll();
            articleService.setTopFalse(poppedOutId);
        }
    }

    public void remove(String id) {
        topArticlesIds.remove(id);
    }

    @Override
    public String toString() {
        return "TopArticlesServiceImpl{}";
    }
}
