package es.upm.miw.devops.service;

import es.upm.miw.devops.code.User;
import es.upm.miw.devops.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User findById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public java.util.List<User> findAll() {
        return repo.findAll();
    }
}
