package project.dailynail.services.impl;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import project.dailynail.DailyNailApplication;
import project.dailynail.services.*;
import project.dailynail.utils.FileIOUtil;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceImplTest {
    private AdminService serviceToTest;

    @Mock
    private ArticleService mockArticleService;
    @Mock
    private UserService mockUserService;
    @Mock
    private CommentService mockCommentService;
    @Mock
    private UserRoleService mockUserRoleService;
    @Mock
    private CategoryService mockCategoryService;
    @Mock
    private SubcategoryService mockSubcategoryService;
    @Mock
    private StatsService mockStatsService;
    @Mock
    private FileIOUtil mockFileIOUtil;
    @Mock
    private DailyNailApplication mockApp;

    @BeforeEach
    void setUp() {
        serviceToTest = new AdminServiceImpl(mockArticleService, mockUserService, mockCommentService,
                new Gson(), mockFileIOUtil, mockUserRoleService, mockSubcategoryService,
                mockCategoryService, mockStatsService);
    }

    @Test
    void exportDataTest() throws IOException {
        serviceToTest.exportData();
        verify(mockFileIOUtil, times(1)).write("[]", "src/main/resources/static/jsons/users.json");

    }

    @Test
    void importDataTest() throws FileNotFoundException {
        serviceToTest.importData();
        verify(mockUserService, times(1)).seedNonInitialUsers();
        verify(mockArticleService, times(1)).seedArticles();
        verify(mockCommentService, times(1)).seedComments();
        verify(mockStatsService, times(1)).seedStats();
    }

}
