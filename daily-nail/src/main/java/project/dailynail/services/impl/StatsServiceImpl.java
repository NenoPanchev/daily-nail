package project.dailynail.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import project.dailynail.models.entities.StatsEntity;
import project.dailynail.models.view.StatsViewModel;
import project.dailynail.repositories.StatsRepository;
import project.dailynail.services.StatsService;

@Service
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;
    private final ModelMapper modelMapper;

    public StatsServiceImpl(StatsRepository statsRepository, ModelMapper modelMapper) {
        this.statsRepository = statsRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedInitialStatsByCategory() {
        if (statsRepository.count() > 0) {
            return;
        }
        StatsEntity statsEntity = new StatsEntity()
                .setAuthorizedRequests(0)
                .setUnauthorizedRequests(0);
        statsRepository.save(statsEntity);
    }

    @Override
    public void onRequest() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        StatsEntity statsEntity = statsRepository.findAll().get(0);
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            statsEntity.setAuthorizedRequests(statsEntity.getAuthorizedRequests() + 1);
        } else {
            statsEntity.setUnauthorizedRequests(statsEntity.getUnauthorizedRequests() + 1);
        }
        statsRepository.save(statsEntity);
    }

    @Override
    public StatsViewModel getStatsViewModel() {
        return modelMapper.map(statsRepository.findAll().get(0), StatsViewModel.class);
    }
}
