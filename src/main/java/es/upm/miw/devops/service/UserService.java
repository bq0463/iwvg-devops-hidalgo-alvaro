package es.upm.miw.devops.service;

import es.upm.miw.devops.code.Fraction;
import es.upm.miw.devops.code.User;
import es.upm.miw.devops.repositories.UserRepository;
import es.upm.miw.devops.rest.dtos.PatchActiveUserDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import es.upm.miw.devops.rest.dtos.UpdateUserDto;
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

    @Transactional
    public boolean isBillable(String id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.calculateBillable();
    }

    @Transactional
    public User updateUser(String id, UpdateUserDto dto) {
        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(dto.name());
        user.setFamilyName(dto.familyName());
        user.setEmail(dto.email());
        user.setIdentity(dto.identity());
        user.setAddress(dto.address());
        user.setCity(dto.city());
        user.setProvince(dto.province());
        user.setPostalCode(dto.postalCode());
        user.setActive(dto.active());
        user.setBillable(user.calculateBillable());

        return repo.save(user);
    }

    @Transactional
    public void patchUsersActive(List<PatchActiveUserDto> dtos) {
        for (PatchActiveUserDto dto : dtos) {
            User user = repo.findById(dto.id())
                    .orElseThrow(() -> new RuntimeException("User not found: " + dto.id()));

            user.setActive(dto.active());
            user.setBillable(user.calculateBillable());

            repo.save(user);
        }
    }


}
