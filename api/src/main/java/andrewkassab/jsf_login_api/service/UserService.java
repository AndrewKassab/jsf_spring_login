package andrewkassab.jsf_login_api.service;


import andrewkassab.jsf_login_api.model.User;

import java.util.Optional;

public interface UserService {

    Optional<User> login(String username, String password);

    Optional<User> findByUsername(String username);

    void saveUser(User user);

}
