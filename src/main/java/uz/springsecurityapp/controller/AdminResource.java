package uz.springsecurityapp.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import uz.springsecurityapp.config.Constants;
import uz.springsecurityapp.security.jwt.JWTFilter;
import uz.springsecurityapp.security.jwt.JwtProvider;
import uz.springsecurityapp.domain.ApiResponse;
import uz.springsecurityapp.domain.User;
import uz.springsecurityapp.service.UserService;
import uz.springsecurityapp.service.dto.SignUpAdminPageDto;
import uz.springsecurityapp.service.dto.vm.LoginVM;

import javax.validation.Valid;

@RestController
@RequestMapping("/manag")
@RequiredArgsConstructor
public class AdminResource {

    private final Logger log = LoggerFactory.getLogger(AdminResource.class);
    private final UserService userService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtProvider jwtProvider;

    /**
     * {@code POST  /register} : register the manager user for admin page.
     *
     * @param signUpAdminPageDto the admin page user View Model.
//     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
//     * @throws EmailAlreadyUsedException {@code 400 (Bad Request)} if the email is already used.
//     * @throws LoginAlreadyUsedException {@code 400 (Bad Request)} if the login is already used.
     */
    @PostMapping("/register")

    public ResponseEntity<ApiResponse> createManager(@Valid @RequestBody SignUpAdminPageDto signUpAdminPageDto) {
        log.debug("REST request to create user by admin page {}", signUpAdminPageDto);

        if (isPasswordLengthInvalid(signUpAdminPageDto.getPassword())) {
//            throw new InvalidPasswordException(); TODO: Обработать и задействовать данный exception.
            throw new BadCredentialsException("Incorrect password!");
        }
        User user = userService.create(signUpAdminPageDto);
        ApiResponse apiResponse = new ApiResponse(true, "User successfully created!", user);
        return ResponseEntity.created(null).body(apiResponse);
    }

    @PostMapping("/auth")
    public ResponseEntity<JWTToken> signInManager(@Valid @RequestBody LoginVM loginVM) {
        log.debug("REST request to create user by admin page by the login: {}", loginVM.getUsername());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginVM.getUsername(),
                loginVM.getPassword()
        );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/uzr")
    public String abc(@Valid String login){
        return "Hello abc";
    }


    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }











    private static boolean isPasswordLengthInvalid(String password) {
        return (
                (!StringUtils.hasLength(password)) ||
                        password.length() < Constants.PASSWORD_MIN_LENGTH ||
                        password.length() > Constants.PASSWORD_MAX_LENGTH
        );
    }
}
