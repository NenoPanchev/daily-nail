package project.dailynail.services.impl;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import project.dailynail.services.AdminService;
import project.dailynail.services.ArticleService;
import project.dailynail.services.CommentService;
import project.dailynail.services.UserService;
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

    public AdminServiceImpl(ArticleService articleService, UserService userService, CommentService commentService, Gson gson, FileIOUtil fileIOUtil) {
        this.articleService = articleService;
        this.userService = userService;
        this.commentService = commentService;
        this.gson = gson;
        this.fileIOUtil = fileIOUtil;
    }

    public void exportData() throws IOException {
        fileIOUtil.write(gson.toJson(userService.exportUsers()), USERS_FILE_PATH);
        fileIOUtil.write(gson.toJson(articleService.exportArticles()), ARTICLES_FILE_PATH);
        fileIOUtil.write(gson.toJson(commentService.exportComments()), COMMENTS_FILE_PATH);
    }

    public void importData() throws FileNotFoundException {
        userService.seedNonInitialUsers();
        articleService.seedArticles();
        commentService.seedComments();
    }
}
