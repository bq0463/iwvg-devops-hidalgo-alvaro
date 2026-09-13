package es.upm.miw.devops.service;

import es.upm.miw.devops.code.Fraction;
import es.upm.miw.devops.code.User;
import es.upm.miw.devops.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public User findById(String id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.getFractions().size();
        return user;
    }

    public List<User> findAll() {
        return repo.findAll();
    }

    @Transactional
    public List<Fraction> findFractionsByUserId(String id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.getFractions().size();
        return user.getFractions();
    }

    @Transactional
    public void deleteById(String id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        repo.deleteById(id);
    }

    @Transactional
    public User updateActive(String id, boolean active) {
        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.getFractions().size();

        user.setActive(active);
        return repo.save(user);
    }

}
