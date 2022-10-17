package project.dailynail.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.dailynail.services.ArticleService;

@Controller
public class MaintenanceController {
    private final ArticleService articleService;

    public MaintenanceController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/maintenance")
    public String maintenance(Model model) {
        model.addAttribute("popular", articleService.getFiveMostPopular());
        return "maintenance";
    }


}
