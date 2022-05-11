package project.dailynail.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.dailynail.models.binding.ArticleEditBindingModel;
import project.dailynail.models.binding.UserUpdateRoleBindingModel;
import project.dailynail.models.dtos.UserRoleDto;
import project.dailynail.services.ArticleService;
import project.dailynail.services.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ArticleService articleService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public AdminController(ArticleService articleService, UserService userService, ModelMapper modelMapper) {
        this.articleService = articleService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @GetMapping
    public String admin() {
        return "admin";
    }

    @GetMapping("/accounts")
    public String manageRoles(Model model) {
        model.addAttribute("users", userService.getAllUsersOrderedByRoles());
        return "accounts-management";
    }

    @GetMapping("/accounts/{id}")
    public String editRole(@PathVariable("id") String id, Model model) {
        model.addAttribute("user", userService.getUserViewModelById(id));
        return "account-role";
    }

    @PostMapping("/accounts/edit")
    public String editRoleConfirm(@Valid UserUpdateRoleBindingModel userUpdateRoleBindingModel,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userUpdateRoleBindingModel", userUpdateRoleBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userUpdateRoleBindingModel", bindingResult);
            return "redirect:/admin/accounts";
        }
        userService.updateRole(modelMapper.map(userUpdateRoleBindingModel, UserRoleDto.class));

        return "redirect:/admin/accounts";
    }

    @ModelAttribute("userUpdateRoleBindingModel")
    public UserUpdateRoleBindingModel userUpdateRoleBindingModel() {
        return new UserUpdateRoleBindingModel();
    }
}
