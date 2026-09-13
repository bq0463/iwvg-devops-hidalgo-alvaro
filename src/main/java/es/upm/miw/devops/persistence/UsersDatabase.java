package es.upm.miw.devops.persistence;

import es.upm.miw.devops.code.Fraction;
import es.upm.miw.devops.code.User;
import es.upm.miw.devops.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Profile({"dev","test"})
@Configuration
public class UsersDatabase {

    @Bean
    public CommandLineRunner init(UserRepository userRepository) {
        return args -> {

            long userCount = userRepository.count();


            if (userCount > 0) {
                System.out.println(">>> La base de datos ya contiene usuarios (" + userCount + "). No se cargan datos iniciales.");
                return;
            }


            System.out.println(">>> BD vacía. Cargando datos iniciales...");
            cargarDatos(userRepository);
        };
    }

    private void cargarDatos(UserRepository userRepository) {

        User u1 = new User("1", "Oscar", "Fernandez",
                List.of(new Fraction(0, 1), new Fraction(1, 1), new Fraction(2, 1)));
        u1.getFractions().forEach(f -> f.setUser(u1));

        User u2 = new User("2", "Ana", "Blanco",
                List.of(new Fraction(2, 1), new Fraction(-1, 5), new Fraction(2, 4), new Fraction(4, 3)));
        u2.getFractions().forEach(f -> f.setUser(u2));

        User u3 = new User("3", "Oscar", "López",
                List.of(new Fraction(1, 5), new Fraction(3, -6), new Fraction(1, 2), new Fraction(4, 4)));
        u3.getFractions().forEach(f -> f.setUser(u3));

        User u4 = new User("4", "Paula", "Torres",
                List.of(new Fraction(2, 2), new Fraction(4, 4)));
        u4.getFractions().forEach(f -> f.setUser(u4));

        User u5 = new User("5", "Antonio", "Blanco",
                List.of(new Fraction(0, 1), new Fraction(0, -2), new Fraction(0, 3)));
        u5.getFractions().forEach(f -> f.setUser(u5));

        User u6 = new User("6", "Paula", "Torres",
                List.of(new Fraction(0, 0), new Fraction(1, 0), new Fraction(1, 1)));
        u6.getFractions().forEach(f -> f.setUser(u6));

        userRepository.saveAll(List.of(u1, u2, u3, u4, u5, u6));

        System.out.println(">>> Datos iniciales cargados correctamente.");
    }
}
