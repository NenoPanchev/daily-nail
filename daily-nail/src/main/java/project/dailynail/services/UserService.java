package project.dailynail.services;

import project.dailynail.models.service.UserServiceModel;

public interface UserService {

    void seedUsers();
    UserServiceModel findByEmail(String email);
    boolean existsByEmail(String email);

    void registerAndLoginUser(UserServiceModel userServiceModel);
}
