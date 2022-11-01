package project.dailynail.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import project.dailynail.models.entities.JokeEntity;
import project.dailynail.models.service.JokeServiceModel;
import project.dailynail.models.service.UserServiceModel;
import project.dailynail.models.validators.ServiceLayerValidationUtil;
import project.dailynail.models.view.JokeViewModel;
import project.dailynail.repositories.JokeRepository;
import project.dailynail.services.JokeService;
import project.dailynail.services.UserService;

import java.time.LocalDateTime;

@Service
public class JokeServiceImpl implements JokeService {
    private final JokeRepository jokeRepository;
    private final ModelMapper modelMapper;
    private final ServiceLayerValidationUtil serviceLayerValidationUtil;
    private final UserService userService;

    public JokeServiceImpl(JokeRepository jokeRepository, ModelMapper modelMapper, ServiceLayerValidationUtil serviceLayerValidationUtil, UserService userService) {
        this.jokeRepository = jokeRepository;
        this.modelMapper = modelMapper;
        this.serviceLayerValidationUtil = serviceLayerValidationUtil;
        this.userService = userService;
    }

    @Override
    public void createJoke(JokeServiceModel jokeServiceModel) {
        serviceLayerValidationUtil.validate(jokeServiceModel);

        UserServiceModel principal = userService.getPrincipal();
        jokeServiceModel
                .setCreated(LocalDateTime.now())
                .setAuthor(principal);
        jokeRepository.save(modelMapper.map(jokeServiceModel, JokeEntity.class));
    }

    @Override
    public String getLatestJoke() {
        String latestJoke = jokeRepository.findLatestJoke();
        return null;
    }
}
