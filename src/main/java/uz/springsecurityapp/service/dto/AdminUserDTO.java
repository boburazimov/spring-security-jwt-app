package uz.springsecurityapp.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.springsecurityapp.config.Constants;
import uz.springsecurityapp.domain.Authority;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * A DTO representing a user, with his authorities.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserDTO {

    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    @Size(min = Constants.PASSWORD_MIN_LENGTH, max = Constants.PASSWORD_MAX_LENGTH)
    private String password;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    private Set<Authority> authorities;
}
