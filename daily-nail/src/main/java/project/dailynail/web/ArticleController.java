package project.dailynail.web;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.dailynail.models.binding.ArticleCreateBindingModel;
import project.dailynail.models.entities.enums.CategoryNameEnum;
import project.dailynail.models.service.CategoryServiceModel;
import project.dailynail.services.ArticleService;
import project.dailynail.services.CategoryService;
import project.dailynail.services.SubcategoryService;

import javax.validation.Valid;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;
    private final CategoryService categoryService;
    private final SubcategoryService subcategoryService;
    private final ModelMapper modelMapper;

    public ArticleController(ArticleService articleService, CategoryService categoryService, SubcategoryService subcategoryService, ModelMapper modelMapper) {
        this.articleService = articleService;
        this.categoryService = categoryService;
        this.subcategoryService = subcategoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());


        return "article-create";
    }

    @PostMapping("/create")
    public String createConfirm(@Valid ArticleCreateBindingModel articleCreateBindingModel,
            BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("articleCreateBindingModel", articleCreateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.articleCreateBindingModel", bindingResult);
            return "redirect:create";
        }

        return "redirect:/";
    }

    @ModelAttribute
    public ArticleCreateBindingModel articleCreateBindingModel() {
        return new ArticleCreateBindingModel();
    }
}
