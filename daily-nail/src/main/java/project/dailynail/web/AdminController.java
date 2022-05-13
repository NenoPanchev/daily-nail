package project.dailynail.web;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.dailynail.constants.GlobalConstants;
import project.dailynail.models.binding.UserUpdateRoleBindingModel;
import project.dailynail.models.dtos.UserRoleDto;
import project.dailynail.services.ArticleService;
import project.dailynail.services.CommentService;
import project.dailynail.services.UserService;
import project.dailynail.utils.FileIOUtil;

import javax.validation.Valid;

import java.io.FileNotFoundException;
import java.io.IOException;

import static project.dailynail.constants.GlobalConstants.*;
import static project.dailynail.constants.GlobalConstants.ARTICLES_FILE_PATH;
import static project.dailynail.constants.GlobalConstants.USERS_FILE_PATH;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ArticleService articleService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final FileIOUtil fileIOUtil;
    private final Gson gson;
    private final CommentService commentService;

    public AdminController(ArticleService articleService, UserService userService, ModelMapper modelMapper, FileIOUtil fileIOUtil, Gson gson, CommentService commentService) {
        this.articleService = articleService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.fileIOUtil = fileIOUtil;
        this.gson = gson;
        this.commentService = commentService;
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
        fileIOUtil.write(gson.toJson(userService.exportUsers()), USERS_FILE_PATH);
        fileIOUtil.write(gson.toJson(articleService.exportArticles()), ARTICLES_FILE_PATH);
        fileIOUtil.write(gson.toJson(commentService.exportComments()), COMMENTS_FILE_PATH);

        return "redirect:/admin";
    }

    @PostMapping("/data/import")
    public String importConfirm() throws FileNotFoundException {
        userService.seedNonInitialUsers();
        articleService.seedArticles();
        commentService.seedComments();
        return "redirect:/admin";
    }

    @ModelAttribute("userUpdateRoleBindingModel")
    public UserUpdateRoleBindingModel userUpdateRoleBindingModel() {
        return new UserUpdateRoleBindingModel();
    }
}
