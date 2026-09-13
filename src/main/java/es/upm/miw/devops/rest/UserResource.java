package es.upm.miw.devops.rest;

import es.upm.miw.devops.code.Fraction;
import es.upm.miw.devops.code.User;
import es.upm.miw.devops.rest.dtos.ActiveUserDto;
import es.upm.miw.devops.rest.dtos.PatchActiveUserDto;
import es.upm.miw.devops.rest.dtos.UpdateUserDto;
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

    @GetMapping("/{id}/billable")
    public boolean isBillable(@PathVariable String id) {
        return service.isBillable(id);
    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        service.deleteById(id);
    }

    @PutMapping("/{id}/active")
    public User updateActive(@PathVariable String id, @RequestBody ActiveUserDto dto) {
        return service.updateActive(id, dto.active());
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody UpdateUserDto dto) {
        return service.updateUser(id, dto);
    }

    @PatchMapping
    public void patchUsersActive(@RequestBody List<PatchActiveUserDto> dtos) {
        service.patchUsersActive(dtos);
    }

}
