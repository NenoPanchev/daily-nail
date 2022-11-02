package project.dailynail.services.impl;

import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import project.dailynail.models.dtos.json.JokeEntityExportDto;
import project.dailynail.models.entities.JokeEntity;
import project.dailynail.models.entities.StatsEntity;
import project.dailynail.models.entities.UserEntity;
import project.dailynail.models.service.ArticleCreateServiceModel;
import project.dailynail.models.service.JokeServiceModel;
import project.dailynail.models.service.UserServiceModel;
import project.dailynail.models.validators.ServiceLayerValidationUtil;
import project.dailynail.repositories.JokeRepository;
import project.dailynail.services.JokeService;
import project.dailynail.services.UserService;
import project.dailynail.services.impl.JokeServiceImpl;
import project.dailynail.services.impl.StatsServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class JokeServiceImplTest {
    private JokeEntity jokeEntity;
    private JokeService serviceToTest;

    @Mock
    private JokeRepository mockJokeRepository;
    @Mock
    private ServiceLayerValidationUtil mockServiceLayerValidationUtil;
    @Mock
    private UserService mockUserService;

    @BeforeEach
    void setUp() {
        serviceToTest = new JokeServiceImpl(mockJokeRepository, new ModelMapper(), mockServiceLayerValidationUtil, mockUserService, new Gson());
        jokeEntity = new JokeEntity()
                .setAuthor(new UserEntity()
                .setEmail("test@user.com")
                .setFullName("Test User")
                .setPassword("1234"))
                .setCreated(LocalDateTime.now())
                .setText("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
    }

    @Test
    void testCreateJoke() {
        lenient().doNothing().when(mockServiceLayerValidationUtil).validate(isA(JokeServiceModel.class));
        UserServiceModel principal = new UserServiceModel()
                .setEmail("test@user.com")
                .setFullName("Test User")
                .setPassword("1234");
        when(mockUserService.getPrincipal())
                .thenReturn(principal);
        JokeServiceModel jokeServiceModel = new JokeServiceModel()
                .setAuthor(principal)
                .setCreated(jokeEntity.getCreated())
                .setText(jokeEntity.getText());
        when(mockJokeRepository.save(jokeEntity))
                .thenReturn(jokeEntity);
        JokeEntity expected = mockJokeRepository.save(jokeEntity);
        serviceToTest.createJoke(jokeServiceModel);
        verify(mockServiceLayerValidationUtil, times(1)).validate(jokeServiceModel);
        assertEquals(expected.getText(), jokeEntity.getText());
    }

    @Test
    void testGetLatestJoke() {
        when(mockJokeRepository.findLatestJoke())
                .thenReturn(jokeEntity.getText());
        String expected = serviceToTest.getLatestJoke();
        assertEquals(expected, jokeEntity.getText());
    }

    @Test
    void testExportJokes() {
        when(mockJokeRepository.findAll())
                .thenReturn(List.of(jokeEntity));
        List<JokeEntityExportDto> expected = serviceToTest.exportJokes();
        assertEquals(expected.get(0).getAuthorEmail(), jokeEntity.getAuthor().getEmail());
        assertEquals(expected.get(0).getText(), jokeEntity.getText());
        assertEquals(expected.get(0).getCreated(), jokeEntity.getCreated().toString().substring(0, 16));
    }


}
