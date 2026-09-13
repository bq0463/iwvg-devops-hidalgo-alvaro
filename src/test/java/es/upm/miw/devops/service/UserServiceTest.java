package es.upm.miw.devops.service;

import es.upm.miw.devops.code.User;
import es.upm.miw.devops.code.Fraction;
import es.upm.miw.devops.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import es.upm.miw.devops.repositories.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired UserRepository userRepository;

    @Test
    void testFindById() {
        User user = userService.findById("1");
        assertNotNull(user);
        assertEquals("1", user.getId());
    }

    @Test
    void testFindFractionsByUserId() {
        List<Fraction> fractions = userService.findFractionsByUserId("1");
        assertNotNull(fractions);
        assertFalse(fractions.isEmpty());
    }

    @Test
    void testUserHasFractions() {
        User user = userService.findById("2");
        assertNotNull(user);
        assertFalse(user.getFractions().isEmpty());
    }

    @Test
    void testDeleteUser() {
        assertNotNull(userService.findById("3"));

        userService.deleteById("3");
        assertThrows(RuntimeException.class, () -> userService.findById("3"));
    }

    @Test
    void testDeleteUserNotFound() {
        assertThrows(RuntimeException.class, () -> userService.deleteById("no-existe"));
    }

    @Test
    void testUpdateActive() {
        // Usuario existe y está activo por defecto
        User user = userService.findById("1");
        assertTrue(user.isActive());

        // Lo desactivamos
        User updated = userService.updateActive("1", false);
        assertFalse(updated.isActive());

        // Comprobamos que persiste el cambio
        User reloaded = userService.findById("1");
        assertFalse(reloaded.isActive());
    }

    @Test
    void testUpdateActiveUserNotFound() {
        assertThrows(RuntimeException.class, () -> userService.updateActive("999", true));
    }

    @Test
    void testIsBillableTrue() {
        // Usuario del seeder con todos los campos completos
        boolean billable = userService.isBillable("1");
        assertTrue(billable);
    }

    @Test
    void testIsBillableFalse() {
        // Creamos un usuario NO billable manualmente
        User u = new User(
                "999",
                "Oscar",
                "", // familyName vacío → NO billable
                "oscar@example.com",
                "12345678A",
                "Calle Mayor 1",
                "Madrid",
                "Madrid",
                "28001",
                true,
                false,
                List.of()
        );
        userRepository.save(u);

        boolean billable = userService.isBillable("999");
        assertFalse(billable);
    }

    @Test
    void testIsBillableUserNotFound() {
        assertThrows(RuntimeException.class, () -> userService.isBillable("no-existe"));
    }

}
