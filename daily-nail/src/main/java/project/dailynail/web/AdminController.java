package project.dailynail.web;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.dailynail.models.binding.UserUpdateRoleBindingModel;
import project.dailynail.models.dtos.UserRoleDto;
import project.dailynail.services.*;

import javax.validation.Valid;

import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final AdminService adminService;
    private final StatsService statsService;
    private final ArticleService articleService;

    public AdminController(UserService userService, ModelMapper modelMapper, AdminService adminService, StatsService statsService, ArticleService articleService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.adminService = adminService;
        this.statsService = statsService;
        this.articleService = articleService;
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

    @GetMapping("/data")
    public String data() {
        return "data";
    }

    @GetMapping("/data/backup")
    public String backup() throws IOException {
        adminService.exportData();
        return "redirect:/admin";
    }

    @PostMapping("/data/import")
    public String importConfirm() throws FileNotFoundException {
        adminService.importData();
        return "redirect:/admin";
    }

    @GetMapping("/stats")
    public String stats(Model model) {
        model.addAttribute("authorized", statsService.getStatsViewModel());
        model.addAttribute("categoryViews", articleService.getCategoryViews());
        return "stats";
    }

    @ModelAttribute("userUpdateRoleBindingModel")
    public UserUpdateRoleBindingModel userUpdateRoleBindingModel() {
        return new UserUpdateRoleBindingModel();
    }
}
