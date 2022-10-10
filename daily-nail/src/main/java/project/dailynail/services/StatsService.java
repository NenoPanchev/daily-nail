package project.dailynail.services;

import project.dailynail.models.view.StatsViewModel;

public interface StatsService {
    void seedInitialStatsByCategory();
    void onRequest();
    StatsViewModel getStatsViewModel();
}
