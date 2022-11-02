package project.dailynail.services.impl;

import com.google.gson.Gson;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import project.dailynail.services.*;
import project.dailynail.utils.FileIOUtil;

import java.io.FileNotFoundException;
import java.io.IOException;

import static project.dailynail.constants.GlobalConstants.*;

@Service
public class AdminServiceImpl implements AdminService {
    private final ArticleService articleService;
    private final UserService userService;
    private final CommentService commentService;
    private final Gson gson;
    private final FileIOUtil fileIOUtil;
    private final UserRoleService userRoleService;
    private final SubcategoryService subcategoryService;
    private final CategoryService categoryService;
    private final StatsService statsService;
    private final JokeService jokeService;

    public AdminServiceImpl(ArticleService articleService, UserService userService, CommentService commentService, Gson gson, FileIOUtil fileIOUtil, UserRoleService userRoleService, SubcategoryService subcategoryService, CategoryService categoryService, StatsService statsService, JokeService jokeService) {
        this.articleService = articleService;
        this.userService = userService;
        this.commentService = commentService;
        this.gson = gson;
        this.fileIOUtil = fileIOUtil;
        this.userRoleService = userRoleService;
        this.subcategoryService = subcategoryService;
        this.categoryService = categoryService;
        this.statsService = statsService;
        this.jokeService = jokeService;
    }

    public void exportData() throws IOException {
        fileIOUtil.write(gson.toJson(userService.exportUsers()), USERS_FILE_PATH);
        fileIOUtil.write(gson.toJson(articleService.exportArticles()), ARTICLES_FILE_PATH);
        fileIOUtil.write(gson.toJson(commentService.exportComments()), COMMENTS_FILE_PATH);
        fileIOUtil.write(gson.toJson(statsService.exportStats()), STATS_FILE_PATH);
        fileIOUtil.write(gson.toJson(jokeService.exportJokes()), JOKES_FILE_PATH);
    }

    public void importData() throws FileNotFoundException {
        this.userService.seedNonInitialUsers();
        this.articleService.seedArticles();
        this.commentService.seedComments();
        this.statsService.seedStats();
        this.jokeService.seedJokes();
    }

    @EventListener(ApplicationStartedEvent.class)
    public void onStartInit() throws FileNotFoundException {
        this.userRoleService.seedUserRoles();
        this.userService.seedUsers();
        this.categoryService.seedCategories();
        this.subcategoryService.seedSubcategories();
        this.statsService.seedInitialStatsByCategory();
        this.jokeService.seedJokes();

        if (!articleService.hasArticles()) {
            this.articleService.seedArticles();
            this.commentService.seedComments();
        }
    }
}
