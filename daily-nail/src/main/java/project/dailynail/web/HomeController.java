package project.dailynail.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.dailynail.models.entities.enums.CategoryNameEnum;
import project.dailynail.services.ArticleService;

@Controller
public class HomeController {
    private final ArticleService articleService;

    public HomeController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("topSport", articleService.getNewestArticleByCategoryName(CategoryNameEnum.SPORTS));
        model.addAttribute("sports", articleService.getFourArticlesByCategoryName(CategoryNameEnum.SPORTS));
        model.addAttribute("topEntertainment", articleService.getNewestArticleByCategoryName(CategoryNameEnum.ENTERTAINMENT));
        model.addAttribute("entertainments", articleService.getFourArticlesByCategoryName(CategoryNameEnum.ENTERTAINMENT));
//        model.addAttribute("topWorld", articleService.getNewestArticleByCategoryName(CategoryNameEnum.WORLD));
//        model.addAttribute("world", articleService.getFourArticlesByCategoryName(CategoryNameEnum.WORLD));
//        model.addAttribute("topCovid", articleService.getNewestArticleByCategoryName(CategoryNameEnum.COVID_19));
//        model.addAttribute("covid", articleService.getFourArticlesByCategoryName(CategoryNameEnum.COVID_19));
        model.addAttribute("latestFive", articleService.getLatestFiveArticles());
        model.addAttribute("latestNine", articleService.getLatestNineArticles());

        return "index";
    }
}
