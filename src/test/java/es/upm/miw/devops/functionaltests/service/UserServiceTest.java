package es.upm.miw.devops.functionaltests.service;

import es.upm.miw.devops.code.User;
import es.upm.miw.devops.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void testFindByIdSeederUser() {
        User user = userService.findById("1"); // ID del seeder

        assertNotNull(user);
        assertEquals("1", user.getId());
        assertNotNull(user.getFractions());
    }
}

