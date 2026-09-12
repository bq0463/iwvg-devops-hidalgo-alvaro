package es.upm.miw.devops.rest;

import es.upm.miw.devops.code.User;
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
}
