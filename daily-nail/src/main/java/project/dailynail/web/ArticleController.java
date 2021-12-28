package project.dailynail.web;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.dailynail.models.binding.ArticleCreateBindingModel;

import project.dailynail.models.binding.ArticleSearchBindingModel;
import project.dailynail.models.service.ArticleCreateServiceModel;
import project.dailynail.services.ArticleService;
import project.dailynail.services.CategoryService;
import project.dailynail.services.SubcategoryService;
import project.dailynail.services.UserService;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    public static final Integer ARTICLES_PER_PAGE = 20;
    private final ArticleService articleService;
    private final CategoryService categoryService;
    private final SubcategoryService subcategoryService;
    private final ModelMapper modelMapper;
    private final UserService userService;

    public ArticleController(ArticleService articleService, CategoryService categoryService, SubcategoryService subcategoryService, ModelMapper modelMapper, UserService userService) {
        this.articleService = articleService;
        this.categoryService = categoryService;
        this.subcategoryService = subcategoryService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("allArticles", articleService.getAllArticlesForAdminPanel(0, ARTICLES_PER_PAGE));
        model.addAttribute("authorNames", userService.getAllAuthorNames());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("timePeriods", articleService.getTimePeriods());
        model.addAttribute("articleStatus", "All statuses");

        return "all-articles";
    }

    @GetMapping(value = "/all", params = {"authorName", "category", "timePeriod", "keyWord", "articleStatus"})
    public ModelAndView allPageSearch(ModelAndView modelAndView, BindingResult bindingResult, RedirectAttributes redirectAttributes, @RequestParam String authorName, @RequestParam String category,
                                      @RequestParam String timePeriod, @RequestParam String keyWord, @RequestParam String articleStatus) {
        modelAndView.addObject("keyWord", keyWord);
        modelAndView.addObject("authorName", authorName);
        modelAndView.addObject("authorNames", userService.getAllAuthorNames());
        modelAndView.addObject("timePeriods", articleService.getTimePeriods());;
        modelAndView.addObject("timePeriod", timePeriod);
        modelAndView.addObject("category", category);
        modelAndView.addObject("categories", categoryService.getAllCategories());
        modelAndView.addObject("articleStatuses", articleService.getArticleStatuses());
        modelAndView.addObject("articleStatus", articleStatus);

//        redirectAttributes.addAttribute("categories", categoryService.getAllCategories());      redirectAttributes.addAttribute(authorName);
//        redirectAttributes.addAttribute(category);
//        redirectAttributes.addAttribute(timePeriod);
//        redirectAttributes.addAttribute(keyWord);
//        redirectAttributes.addFlashAttribute("articleStatus", articleStatus);
//        redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.articleStatus", articleStatus);
        modelAndView.setViewName("all-articles");
        return modelAndView;
    }

    @GetMapping(value = "/all", params = "page")
    public String allPage(@RequestParam Integer page, Model model) {
        model.addAttribute("allArticles", articleService.getAllArticlesForAdminPanel(page - 1, ARTICLES_PER_PAGE));
        return "all-articles";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());


        return "article-create";
    }

    @PostMapping("/create")
    public String createConfirm(@Valid ArticleCreateBindingModel articleCreateBindingModel,
            BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("articleCreateBindingModel", articleCreateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.articleCreateBindingModel", bindingResult);
            return "redirect:create";
        }


        articleService.createArticle(modelMapper.map(articleCreateBindingModel, ArticleCreateServiceModel.class));
        return "redirect:/";
    }

    @ModelAttribute
    public ArticleCreateBindingModel articleCreateBindingModel() {
        return new ArticleCreateBindingModel();
    }

    @ModelAttribute
    public ArticleSearchBindingModel articleSearchBindingModel() {
        return new ArticleSearchBindingModel();
    }
}
