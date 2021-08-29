package project.dailynail.services;

import project.dailynail.models.dtos.UserFullNameAndEmailDto;
import project.dailynail.models.service.UserServiceModel;

public interface UserService {

    void seedUsers();
    UserServiceModel findByEmail(String email);
    boolean existsByEmail(String email);

    void registerAndLoginUser(UserServiceModel userServiceModel);

    String getUserNameByEmail(String email);

    boolean updateFullNameAndEmailIfNeeded(UserFullNameAndEmailDto userFullNameAndEmailDto, String principalEmail);

    void loadPrincipal(String email);

    boolean passwordMatches(String principalEmail, String oldPassword);

    void updatePassword(String newPassword, String principalEmail);
}
