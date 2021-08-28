package project.dailynail.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserFullNameAndEmailDto {
    private String fullName;
    private String email;
}
