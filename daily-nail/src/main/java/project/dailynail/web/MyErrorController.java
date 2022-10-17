package project.dailynail.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import project.dailynail.exceptions.ObjectNotFoundException;
import project.dailynail.services.ArticleService;

@Controller
public class MyErrorController implements ErrorController {
    private final ArticleService articleService;

    public MyErrorController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping("/error")
    public String error(Model model) {
        model.addAttribute("popular", articleService.getFiveMostPopular());
        return "error";
    }

    @RequestMapping("/access-denied")
    public String error403(Model model) {
        model.addAttribute("popular", articleService.getFiveMostPopular());
        return "403";
    }

    @RequestMapping("/404")
    public String error404(Model model) {
        model.addAttribute("popular", articleService.getFiveMostPopular());
        return "404";
    }

    @RequestMapping("/throw")
    public String throwing() {
        throw new ObjectNotFoundException();
    }

    @RequestMapping("/throw-err")
    public String throwingErr() {
        throw new RuntimeException();
    }
}
