package andrewkassab.jsf_login_api.dao;

import andrewkassab.jsf_login_api.model.User;

import java.util.Optional;

public interface UserDAO {
    Optional<User> findUserByUsername(String username);
    void saveUser(User user);
}
