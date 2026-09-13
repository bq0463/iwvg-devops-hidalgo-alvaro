package es.upm.miw.devops.rest;

import es.upm.miw.devops.code.Fraction;
import es.upm.miw.devops.code.User;
import es.upm.miw.devops.rest.dtos.ActiveUserDto;
import es.upm.miw.devops.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserResource {

    private final UserService service;

    public UserResource(UserService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable String id) {
        return service.findById(id);
    }

    @GetMapping
    public List<User> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}/fractions")
    public List<Fraction> findFractionsByUserId(@PathVariable String id) {
        User user = service.findById(id);
        return user.getFractions();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        service.deleteById(id);
    }

    @PutMapping("/{id}/active")
    public User updateActive(@PathVariable String id, @RequestBody ActiveUserDto dto) {
        return service.updateActive(id, dto.active());
    }


}
