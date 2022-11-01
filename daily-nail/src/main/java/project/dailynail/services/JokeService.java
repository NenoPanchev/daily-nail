package project.dailynail.services;

import project.dailynail.models.service.JokeServiceModel;
import project.dailynail.models.view.JokeViewModel;

import java.time.LocalDateTime;

public interface JokeService {
    void createJoke(JokeServiceModel jokeServiceModel);
    String getLatestJoke();
}
