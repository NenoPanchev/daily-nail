package project.dailynail.services;

import project.dailynail.models.dtos.json.StatsEntityExportDto;
import project.dailynail.models.view.StatsViewModel;

import java.io.FileNotFoundException;
import java.util.List;

public interface StatsService {
    void seedInitialStatsByCategory();
    void onRequest();
    StatsViewModel getStatsViewModel();
    StatsEntityExportDto exportStats();

    void seedStats() throws FileNotFoundException;
}
