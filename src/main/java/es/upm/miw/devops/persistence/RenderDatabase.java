package es.upm.miw.devops.persistence;

import es.upm.miw.devops.code.Fraction;
import es.upm.miw.devops.code.User;
import es.upm.miw.devops.repositories.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Profile("pre")
@Configuration
public class RenderDatabase {

    @Bean
    public ApplicationRunner init(UserRepository userRepository) {
        return args -> {

            long userCount = userRepository.count();

            if (userCount > 0) {
                System.out.println(">>> Render DB already contains users (" + userCount + "). No initial data loaded.");
                return;
            }

            System.out.println(">>> Render DB empty. Loading initial data...");
            loadData(userRepository);
        };
    }

    private void loadData(UserRepository userRepository) {

        User u1 = new User(
                "1",
                "Oscar",
                "Fernandez",
                "oscar@example.com",
                "12345678A",
                "Calle Mayor 1",
                "Madrid",
                "Madrid",
                "28001",
                true,
                false,
                List.of(new Fraction(0, 1), new Fraction(1, 1), new Fraction(2, 1)),
                User.Roll.ADMIN
        );
        u1.getFractions().forEach(f -> f.setUser(u1));
        u1.setBillable(u1.calculateBillable());

        User u2 = new User(
                "2",
                "Ana",
                "Blanco",
                "ana@example.com",
                "87654321B",
                "Avenida Sol 22",
                "Madrid",
                "Madrid",
                "28002",
                true,
                false,
                List.of(new Fraction(2, 1), new Fraction(-1, 5), new Fraction(2, 4), new Fraction(4, 3)),
                User.Roll.USER
        );
        u2.getFractions().forEach(f -> f.setUser(u2));
        u2.setBillable(u2.calculateBillable());

        User u3 = new User(
                "3",
                "Oscar",
                "López",
                "oscar.lopez@example.com",
                "11223344C",
                "Calle Luna 5",
                "Madrid",
                "Madrid",
                "28003",
                true,
                false,
                List.of(new Fraction(1, 5), new Fraction(3, -6), new Fraction(1, 2), new Fraction(4, 4)),
                User.Roll.USER
        );
        u3.getFractions().forEach(f -> f.setUser(u3));
        u3.setBillable(u3.calculateBillable());

        User u4 = new User(
                "4",
                "Paula",
                "Torres",
                "paula@example.com",
                "55667788D",
                "Calle Verde 10",
                "Madrid",
                "Madrid",
                "28004",
                true,
                false,
                List.of(new Fraction(2, 2), new Fraction(4, 4)),
                User.Roll.USER
        );
        u4.getFractions().forEach(f -> f.setUser(u4));
        u4.setBillable(u4.calculateBillable());

        User u5 = new User(
                "5",
                "Antonio",
                "Blanco",
                "antonio@example.com",
                "99887766E",
                "Calle Azul 3",
                "Madrid",
                "Madrid",
                "",
                true,
                false,
                List.of(new Fraction(0, 1), new Fraction(0, -2), new Fraction(0, 3)),
                User.Roll.USER
        );
        u5.getFractions().forEach(f -> f.setUser(u5));
        u5.setBillable(u5.calculateBillable());

        User u6 = new User(
                "6",
                "Paula",
                "Torres",
                "paula.torres@example.com",
                "44332211F",
                "Calle Roja 7",
                "Madrid",
                "Madrid",
                "28006",
                true,
                false,
                List.of(new Fraction(0, 0), new Fraction(1, 0), new Fraction(1, 1)),
                User.Roll.USER
        );
        u6.getFractions().forEach(f -> f.setUser(u6));
        u6.setBillable(u6.calculateBillable());

        userRepository.saveAll(List.of(u1, u2, u3, u4, u5, u6));

        System.out.println(">>> Render initial data loaded");
    }
}

