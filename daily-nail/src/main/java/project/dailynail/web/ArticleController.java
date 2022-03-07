package project.dailynail.web;


import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.dailynail.models.binding.ArticleCreateBindingModel;

import project.dailynail.models.binding.ArticleEditBindingModel;
import project.dailynail.models.binding.ArticleSearchBindingModel;
import project.dailynail.models.service.ArticleCreateServiceModel;
import project.dailynail.models.view.ArticlesPageViewModel;
import project.dailynail.services.ArticleService;
import project.dailynail.services.CategoryService;
import project.dailynail.services.SubcategoryService;
import project.dailynail.services.UserService;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/articles")
public class ArticleController {
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

    @GetMapping("/{url}")
    public String viewArticle(@PathVariable("url") String url) {


        return "article";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, Model model) {
        ArticleEditBindingModel articleEditBindingModel = articleService.getArticleEditBindingModelById(id);
        model.addAttribute("articleEditBindingModel", articleEditBindingModel);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("id", id);
        return "article-edit";
    }

    @PostMapping("/edit/{id}")
    public String editConfirm(@PathVariable("id") String id, @Valid ArticleEditBindingModel articleEditBindingModel,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("articleEditBindingModel", articleEditBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.articleEditBindingModel", bindingResult);
            return "redirect:edit";
        }

        articleService.editArticle(articleEditBindingModel);

        return "redirect:/articles/all";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        articleService.deleteArticle(id);
        return "redirect:/articles/all";
    }

    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("allArticles", articleService.getAllArticlesForAdminPanel());
        model.addAttribute("authorNames", userService.getAllAuthorNames());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("timePeriods", articleService.getTimePeriods());
        model.addAttribute("articleStatuses", articleService.getArticleStatuses());
        ArticlesPageViewModel allArticles = articleService.getAllArticlesForAdminPanel();
        model.addAttribute("totalElements", allArticles.getTotalElements());
        model.addAttribute("totalPages", allArticles.getTotalPages());
        model.addAttribute("page", 1);
        model.addAttribute("allArticles", allArticles.getContent());


        return "all-articles";
    }

    @GetMapping(value = "/all", params = {"authorName", "category", "timePeriod", "keyWord", "articleStatus"})
    public ModelAndView allPageSearch(ModelAndView modelAndView, @RequestParam String authorName, @RequestParam String category,
                                      @RequestParam String timePeriod, @RequestParam String keyWord, @RequestParam String articleStatus,
                                      @RequestParam (value = "page", required = false, defaultValue = "1") Integer page) {
        modelAndView.addObject("keyWord", keyWord);
        modelAndView.addObject("authorName", authorName);
        modelAndView.addObject("authorNames", userService.getAllAuthorNames());
        modelAndView.addObject("timePeriods", articleService.getTimePeriods());;
        modelAndView.addObject("timePeriod", timePeriod);
        modelAndView.addObject("category", category);
        modelAndView.addObject("categories", categoryService.getAllCategories());
        modelAndView.addObject("articleStatuses", articleService.getArticleStatuses());
        modelAndView.addObject("articleStatus", articleStatus);
        modelAndView.addObject("page", page);

        ArticleSearchBindingModel articleSearchBindingModel =
                new ArticleSearchBindingModel(keyWord, category, authorName, timePeriod, articleStatus, page);
        ArticlesPageViewModel filteredArticles = articleService.getFilteredArticles(articleSearchBindingModel);
        modelAndView.addObject("totalElements", filteredArticles.getTotalElements());
        modelAndView.addObject("totalPages", filteredArticles.getTotalPages());
        modelAndView.addObject("allArticles", filteredArticles.getContent());
        modelAndView.setViewName("all-articles");

        return modelAndView;
    }

    @GetMapping(value = "/all", params = "page")
    public String allPage(@RequestParam Integer page, Model model) {
        model.addAttribute("allArticles", articleService.getAllArticlesForAdminPanel(page));
        model.addAttribute("authorNames", userService.getAllAuthorNames());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("timePeriods", articleService.getTimePeriods());
        model.addAttribute("articleStatuses", articleService.getArticleStatuses());
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

    @ModelAttribute
    public ArticleEditBindingModel articleEditBindingModel() {
        return new ArticleEditBindingModel();
    }
}
