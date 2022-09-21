package uz.springsecurityapp.service;

import org.springframework.transaction.annotation.Transactional;
import uz.springsecurityapp.domain.User;
import uz.springsecurityapp.service.dto.SignUpAdminPageDto;

import java.util.Optional;

public interface UserService {

    /**
     * Create new User in DataBase with privilege simple user.
     * @param signUpAdminPageDto DTO from request
     * @return registered new User from DB.
     */
    User create(SignUpAdminPageDto signUpAdminPageDto);

    /**
     * Find User by login.
     * @param login String type of value.
     * @return User from DB.
     */
    User findByLogin(String login);

    /**
     * Find User by login and password.
     * @param login String type of value.
     * @param password String type of value.
     * @return User from DB.
     */
    User findByLoginAndPassword(String login, String password);

    /**
     * Get current user with authorities.
     *
     * @return User
     */
    @Transactional(readOnly = true)
    Optional<User> getUserWithAuthorities();
}
