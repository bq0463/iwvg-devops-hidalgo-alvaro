package es.upm.miw.devops.service;

import es.upm.miw.devops.code.User;
import es.upm.miw.devops.code.Fraction;
import es.upm.miw.devops.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    private UserService userService;

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
        assertThrows(RuntimeException.class, () -> userService.deleteById("999"));
    }
}
