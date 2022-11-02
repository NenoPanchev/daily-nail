package project.dailynail.services;

import project.dailynail.models.dtos.json.JokeEntityExportDto;
import project.dailynail.models.service.JokeServiceModel;
import project.dailynail.models.view.JokeViewModel;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

public interface JokeService {
    void createJoke(JokeServiceModel jokeServiceModel);
    String getLatestJoke();
    List<JokeEntityExportDto> exportJokes();

    void seedJokes() throws FileNotFoundException;
}
