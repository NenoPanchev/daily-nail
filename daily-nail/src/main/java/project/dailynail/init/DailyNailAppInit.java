package project.dailynail.init;

import com.google.gson.Gson;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import project.dailynail.constants.GlobalConstants;
import project.dailynail.services.*;
import project.dailynail.utils.FileIOUtil;

@Component
public class DailyNailAppInit implements CommandLineRunner {
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final SubcategoryService subcategoryService;
    private final CategoryService categoryService;
    private final Gson gson;
    private final FileIOUtil fileIOUtil;
    private final ArticleService articleService;
    private final CommentService commentService;

    public DailyNailAppInit(UserService userService, UserRoleService userRoleService, SubcategoryService subcategoryService, CategoryService categoryService, Gson gson, FileIOUtil fileIOUtil, ArticleService articleService, CommentService commentService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.subcategoryService = subcategoryService;
        this.categoryService = categoryService;
        this.gson = gson;
        this.fileIOUtil = fileIOUtil;
        this.articleService = articleService;
        this.commentService = commentService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userRoleService.seedUserRoles();
        this.userService.seedUsers();
        this.categoryService.seedCategories();
        this.subcategoryService.seedSubcategories();

        // Back up the data
//        fileIOUtil.write(gson.toJson(userService.exportUsers()), USERS_FILE_PATH);
//        fileIOUtil.write(gson.toJson(articleService.exportArticles()), ARTICLES_FILE_PATH);
//        fileIOUtil.write(gson.toJson(commentService.exportComments()), GlobalConstants.COMMENTS_FILE_PATH);

        // Update data from backups
//        userService.seedNonInitialUsers();
//        articleService.seedArticles();
//        commentService.seedComments();
    }
}
