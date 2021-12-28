package project.dailynail.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.dailynail.services.ArticleService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ArticleService articleService;
    private final ModelMapper modelMapper;

    public AdminController(ArticleService articleService, ModelMapper modelMapper) {
        this.articleService = articleService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/articles/all")
    public String all() {
        return "all-articles";
    }
}
