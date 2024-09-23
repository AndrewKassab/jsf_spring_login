package andrewkassab.jsf_login_api.controller;

import andrewkassab.jsf_login_api.model.User;
import andrewkassab.jsf_login_api.service.UserService;
import andrewkassab.jsf_login_common.dto.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController()
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        var username = loginRequest.getUsername();
        var password = loginRequest.getPassword();

        Optional<User> user = userService.login(username, password);

        if (user.isPresent()) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(401).body("Bad Credentials");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        var username = loginRequest.getUsername();
        var password = loginRequest.getPassword();

        var user = User.builder()
                .username(username)
                .password(password)
                .build();

        userService.saveUser(user);

        return ResponseEntity.status(201).build();
    }


}
