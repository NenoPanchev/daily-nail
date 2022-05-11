package project.dailynail.services;

import project.dailynail.models.dtos.UserFullNameAndEmailDto;
import project.dailynail.models.dtos.UserNewPasswordDto;
import project.dailynail.models.dtos.UserRoleDto;
import project.dailynail.models.service.UserServiceModel;
import project.dailynail.models.view.UserViewModel;

import java.util.List;

public interface UserService {

    void seedUsers();
    UserServiceModel findByEmail(String email);
    boolean existsByEmail(String email);

    void registerAndLoginUser(UserServiceModel userServiceModel);

    String getUserNameByEmail(String email);

    boolean updateFullNameAndEmailIfNeeded(UserFullNameAndEmailDto userFullNameAndEmailDto, String principalEmail);

    void loadPrincipal(String email);

    boolean passwordMatches(String principalEmail, String oldPassword);

    void updatePassword(UserNewPasswordDto userNewPasswordDto, String principalEmail);
    UserServiceModel getPrincipal();

    List<String> getAllAuthorNames();

    List<UserViewModel> getAllUsersOrderedByRoles();

    UserViewModel getUserViewModelById(String id);

    void updateRole(UserRoleDto userRoleDto);
}
