package es.upm.miw.devops.service;

import es.upm.miw.devops.code.Fraction;
import es.upm.miw.devops.code.User;
import es.upm.miw.devops.repositories.UserRepository;
import es.upm.miw.devops.rest.dtos.PatchActiveUserDto;
import es.upm.miw.devops.rest.dtos.UpdateUserDto;
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

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindById() {
        User user = userService.findById("1");
        assertNotNull(user);
        assertEquals("1", user.getId());
    }

    @Test
    void testFindByIdNotFound() {
        assertThrows(RuntimeException.class, () -> userService.findById("no-existe"));
    }

    @Test
    void testFindAll() {
        List<User> users = userService.findAll();
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }

    @Test
    void testFindFractionsByUserId() {
        List<Fraction> fractions = userService.findFractionsByUserId("1");
        assertNotNull(fractions);
        assertFalse(fractions.isEmpty());
    }

    @Test
    void testFindFractionsByUserIdNotFound() {
        assertThrows(RuntimeException.class, () -> userService.findFractionsByUserId("no-existe"));
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
        User user = userService.findById("2");
        assertTrue(user.isActive());

        User updated = userService.updateActive("2", false);
        assertFalse(updated.isActive());

        User reloaded = userService.findById("2");
        assertFalse(reloaded.isActive());
    }

    @Test
    void testUpdateActiveUserNotFound() {
        assertThrows(RuntimeException.class, () -> userService.updateActive("999", true));
    }

    @Test
    void testUpdateActiveAdminForbidden() {
        User admin = new User(
                "51",
                "Admin",
                "Root",
                "admin@example.com",
                "99999999A",
                "Calle Admin 1",
                "Madrid",
                "Madrid",
                "28001",
                true,
                true,
                List.of(),
                User.Roll.ADMIN
        );
        userRepository.save(admin);

        assertThrows(RuntimeException.class, () -> userService.updateActive("51", false));
    }

    @Test
    void testIsBillableTrue() {
        assertTrue(userService.isBillable("1"));
    }

    @Test
    void testIsBillableFalse() {
        User u = new User(
                "999",
                "Oscar",
                "",
                "oscar@example.com",
                "12345678A",
                "Calle Mayor 1",
                "Madrid",
                "Madrid",
                "28001",
                true,
                false,
                List.of(),
                User.Roll.USER
        );
        userRepository.save(u);

        assertFalse(userService.isBillable("999"));
    }

    @Test
    void testIsBillableUserNotFound() {
        assertThrows(RuntimeException.class, () -> userService.isBillable("no-existe"));
    }

    @Test
    void testUpdateUser() {
        User original = userService.findById("1");
        assertNotNull(original);

        UpdateUserDto dto = new UpdateUserDto(
                "Ana",
                "Blanco",
                "ana@example.com",
                "87654321B",
                "Avenida Sol 22",
                "Madrid",
                "Madrid",
                "28003",
                true
        );

        User updated = userService.updateUser("1", dto);

        assertEquals("Ana", updated.getName());
        assertEquals("Blanco", updated.getFamilyName());
        assertEquals("ana@example.com", updated.getEmail());
        assertEquals("87654321B", updated.getIdentity());
        assertEquals("Avenida Sol 22", updated.getAddress());
        assertEquals("Madrid", updated.getCity());
        assertEquals("Madrid", updated.getProvince());
        assertEquals("28003", updated.getPostalCode());
        assertTrue(updated.isActive());
        assertTrue(updated.isBillable());
    }

    @Test
    void testUpdateUserNotBillable() {
        UpdateUserDto dto = new UpdateUserDto(
                "",
                "Blanco",
                "ana@example.com",
                "87654321B",
                "Avenida Sol 22",
                "Madrid",
                "Madrid",
                "28003",
                true
        );

        User updated = userService.updateUser("2", dto);
        assertFalse(updated.isBillable());
    }

    @Test
    void testUpdateUserNotFound() {
        UpdateUserDto dto = new UpdateUserDto(
                "Ana",
                "Blanco",
                "ana@example.com",
                "87654321B",
                "Avenida Sol 22",
                "Madrid",
                "Madrid",
                "28003",
                true
        );

        assertThrows(RuntimeException.class, () -> userService.updateUser("9999", dto));
    }

    @Test
    void testUpdateUserAdminForbidden() {
        User admin = new User(
                "50",
                "Admin",
                "Root",
                "admin2@example.com",
                "99999999B",
                "Calle Admin 2",
                "Madrid",
                "Madrid",
                "28002",
                true,
                true,
                List.of(),
                User.Roll.ADMIN
        );
        userRepository.save(admin);

        UpdateUserDto dto = new UpdateUserDto(
                "Nuevo",
                "Nombre",
                "nuevo@example.com",
                "11111111C",
                "Nueva Calle",
                "Madrid",
                "Madrid",
                "28003",
                false
        );

        assertThrows(RuntimeException.class, () -> userService.updateUser("50", dto));
    }

    @Test
    void testPatchUsersActive() {
        List<PatchActiveUserDto> dtos = List.of(
                new PatchActiveUserDto("2", false),
                new PatchActiveUserDto("1", true)
        );

        userService.patchUsersActive(dtos);

        assertFalse(userService.findById("2").isActive());
        assertTrue(userService.findById("1").isActive());
    }

    @Test
    void testPatchUsersActiveNotFound() {
        List<PatchActiveUserDto> dtos = List.of(
                new PatchActiveUserDto("9999", true)
        );

        assertThrows(RuntimeException.class, () -> userService.patchUsersActive(dtos));
    }

    @Test
    void testPatchUsersActiveAdminForbidden() {
        User admin = new User(
                "52",
                "Admin",
                "Root",
                "admin3@example.com",
                "99999999C",
                "Calle Admin 3",
                "Madrid",
                "Madrid",
                "28003",
                true,
                true,
                List.of(),
                User.Roll.ADMIN
        );
        userRepository.save(admin);

        List<PatchActiveUserDto> dtos = List.of(
                new PatchActiveUserDto("52", false)
        );

        assertThrows(RuntimeException.class, () -> userService.patchUsersActive(dtos));
    }
}
