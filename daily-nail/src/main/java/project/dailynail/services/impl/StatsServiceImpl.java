package project.dailynail.services.impl;

import org.springframework.stereotype.Service;
import project.dailynail.repositories.StatsRepository;
import project.dailynail.services.StatsService;

@Service
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;

    public StatsServiceImpl(StatsRepository statsRepository) {
        this.statsRepository = statsRepository;
    }

    @Override
    public void seedStatsByCategory() {

    }
}
