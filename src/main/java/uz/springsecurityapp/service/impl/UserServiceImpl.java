package uz.springsecurityapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.springsecurityapp.repo.AuthorityRepo;
import uz.springsecurityapp.repo.UserRepo;
import uz.springsecurityapp.security.AuthoritiesConstants;
import uz.springsecurityapp.domain.Authority;
import uz.springsecurityapp.domain.User;
import uz.springsecurityapp.service.UserService;
import uz.springsecurityapp.service.dto.SignUpAdminPageDto;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepo authorityRepo;

    @Override
    public User create(SignUpAdminPageDto signUpAdminPageDto) {

        Set<Authority> moderAuthorities = new HashSet<>(
                Arrays.asList(authorityRepo.findByName(AuthoritiesConstants.MODERATOR), authorityRepo.findByName(AuthoritiesConstants.USER)));

        return userRepo.save(
                new User(signUpAdminPageDto.getLogin(),
                        passwordEncoder.encode(signUpAdminPageDto.getPassword()),
                        signUpAdminPageDto.getEmail(),
                        true,
                        moderAuthorities
                ));
    }

    @Override
    public User findByLogin(String login) {
        Optional<User> byLogin = userRepo.findByLogin(login);
        return byLogin.orElse(null);
    }

    @Override
    public User findByLoginAndPassword(String login, String password) {
        Optional<User> userByLoginAndPassword = userRepo.findByLogin(login);
        if (userByLoginAndPassword.isPresent()){
            User user = userByLoginAndPassword.get();
            if (passwordEncoder.matches(password, user.getPassword())){
                return user;
            }
        }
        return null;
    }

    @Override
    public Optional<User> getUserWithAuthorities() {
        return Optional.empty();
    }
}
