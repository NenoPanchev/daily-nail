package project.dailynail.models.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import project.dailynail.models.entities.enums.Role;

@Data
@NoArgsConstructor
public class UserRoleServiceModel extends BaseServiceModel{
    private Role role;
}
