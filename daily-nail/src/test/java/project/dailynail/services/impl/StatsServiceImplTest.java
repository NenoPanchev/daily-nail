package project.dailynail.services.impl;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.test.context.support.WithMockUser;
import project.dailynail.exceptions.ObjectNotFoundException;
import project.dailynail.models.dtos.json.StatsEntityExportDto;
import project.dailynail.models.entities.StatsEntity;
import project.dailynail.models.entities.enums.Role;
import project.dailynail.models.view.StatsViewModel;
import project.dailynail.repositories.StatsRepository;
import project.dailynail.services.StatsService;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatsServiceImplTest {
    private StatsService serviceToTest;
    private StatsEntity statsEntity;

    @Mock
    private StatsRepository mockStatsRepository;

    @BeforeEach
    void setUp() {
        serviceToTest = new StatsServiceImpl(mockStatsRepository, new ModelMapper(), new Gson());
        statsEntity = new StatsEntity()
        .setAuthorizedRequests(0)
        .setUnauthorizedRequests(0);
    }

    @Test
    void seedInitialStatsByCategoryTest() {
        when(mockStatsRepository.save(statsEntity))
                .thenReturn(statsEntity);
        StatsEntity expected = mockStatsRepository.save(statsEntity);

        assertEquals(expected.getAuthorizedRequests(), statsEntity.getAuthorizedRequests());
    }

    @Test
    @WithMockUser(value = "pesho", roles = {"USER"})
    void onRequestTest() {
        when(mockStatsRepository.findAll())
                .thenReturn(List.of(statsEntity));
        StatsEntity expected = mockStatsRepository.findAll().get(0);
        serviceToTest.onRequest();
        assertEquals(expected.getAuthorizedRequests(), statsEntity.getAuthorizedRequests());
        assertEquals(expected.getUnauthorizedRequests(), statsEntity.getUnauthorizedRequests());
    }

    @Test
    void getStatsViewModelTest() {
        when(mockStatsRepository.findAll())
                .thenReturn(List.of(statsEntity));
        StatsEntity expected = mockStatsRepository.findAll().get(0);
        StatsViewModel actual = serviceToTest.getStatsViewModel();
        assertEquals(expected.getAuthorizedRequests(), actual.getAuthorizedRequests());
        assertEquals(expected.getUnauthorizedRequests(), actual.getUnauthorizedRequests());
    }

    @Test
    void exportStatsTest() {
        when(mockStatsRepository.findAll())
                .thenReturn(List.of(statsEntity));
        StatsEntity expected = mockStatsRepository.findAll().get(0);
        StatsEntityExportDto actual = serviceToTest.exportStats();
        assertEquals(expected.getAuthorizedRequests(), actual.getAuthorizedRequests());
        assertEquals(expected.getUnauthorizedRequests(), actual.getUnauthorizedRequests());
    }

}