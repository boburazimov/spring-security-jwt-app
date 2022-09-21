package uz.springsecurityapp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.springsecurityapp.repo.AuthorityRepo;
import uz.springsecurityapp.repo.UserRepo;
import uz.springsecurityapp.security.AuthoritiesConstants;
import uz.springsecurityapp.domain.Authority;
import uz.springsecurityapp.domain.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class InitialDataLoader implements CommandLineRunner {

    private final AuthorityRepo authorityRepo;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (authorityRepo.count() == 0) {
            authorityRepo.save(new Authority(AuthoritiesConstants.USER));
            authorityRepo.save(new Authority(AuthoritiesConstants.MODERATOR));
            authorityRepo.save(new Authority(AuthoritiesConstants.ADMIN));
        }

        if (userRepo.count() == 0) {
            Set<Authority> adminAuthorities = new HashSet<>(authorityRepo.findAll());
            Set<Authority> moderAuthorities = new HashSet<>(
                    Arrays.asList(authorityRepo.findByName(AuthoritiesConstants.MODERATOR), authorityRepo.findByName(AuthoritiesConstants.USER)));
            userRepo.save(new User("admin", passwordEncoder.encode("admin"), "admin@mail.ru", true, adminAuthorities));
            userRepo.save(new User("moder", passwordEncoder.encode("moder"), "moder@mail.ru", true, moderAuthorities));
        }
    }
}
