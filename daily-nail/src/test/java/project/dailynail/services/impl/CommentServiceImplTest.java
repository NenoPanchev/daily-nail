package project.dailynail.services.impl;

import com.google.gson.Gson;
import org.apache.http.util.Asserts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import project.dailynail.models.dtos.json.CommentEntityExportDto;
import project.dailynail.models.entities.ArticleEntity;
import project.dailynail.models.entities.CommentEntity;
import project.dailynail.models.entities.UserEntity;
import project.dailynail.models.service.CommentServiceModel;
import project.dailynail.models.validators.ServiceLayerValidationUtil;
import project.dailynail.models.validators.ServiceLayerValidationUtilImpl;
import project.dailynail.repositories.CommentRepository;
import project.dailynail.services.ArticleService;
import project.dailynail.services.CommentService;
import project.dailynail.services.UserService;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {
    private CommentEntity first, second;
    private UserEntity testUser;
    private ArticleEntity testArticle = (ArticleEntity) new ArticleEntity().setId("1");
    private CommentService serviceToTest;

    @Mock
    CommentRepository mockCommentRepository;
    @Mock
    UserService mockUserService;
    @Mock
    ArticleService mockArticleService;

    @BeforeEach
    void setUp() {
        testArticle = (ArticleEntity) new ArticleEntity().setId("1");
        testArticle.setUrl("111");
        testUser = new UserEntity()
                .setFullName("testUser")
                .setEmail("test@user.com");
        first = (CommentEntity) new CommentEntity()
                .setId("1");
        first.setText("111")
        .setTimePosted(LocalDateTime.now())
        .setLikes(1)
        .setDislikes(1)
        .setAuthor(testUser)
        .setArticle(testArticle);
        second = (CommentEntity) new CommentEntity()
                .setId("2");
        second.setText("222")
                .setTimePosted(LocalDateTime.now())
                .setLikes(1)
                .setDislikes(1)
                .setAuthor(testUser)
                .setArticle(testArticle);

        serviceToTest = new CommentServiceImpl(mockCommentRepository, new ModelMapper(),
                mockArticleService, mockUserService,
                new ServiceLayerValidationUtilImpl(), new Gson());
    }

    @Test
    void testAdd() {
        CommentServiceModel commentServiceModel = new CommentServiceModel();
        when(mockCommentRepository.save(first))
                .thenReturn(first);
        CommentEntity actual = mockCommentRepository.save(first);
        serviceToTest.add(commentServiceModel, "1");
        assertEquals(actual.getId(), first.getId());

    }

    @Test
    void testDelete() {
        serviceToTest.delete("3");
        verify(mockCommentRepository, times(1)).deleteById(isA(String.class));
    }

    @Test
    void exportCommentsTest() {
        when(mockCommentRepository.findAll())
                .thenReturn(List.of(first, second));
        List<CommentEntityExportDto> actual = serviceToTest.exportComments();
        assertEquals(actual.get(0).getText(), first.getText());
        assertEquals(actual.get(1).getText(), second.getText());
    }

}
