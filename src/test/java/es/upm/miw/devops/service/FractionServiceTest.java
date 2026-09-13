package es.upm.miw.devops.service;

import es.upm.miw.devops.code.Fraction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class FractionServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void testFractionsLoadedFromSeeder() {
        List<Fraction> fractions = userService.findFractionsByUserId("1");
        assertNotNull(fractions);
        assertFalse(fractions.isEmpty());
    }

    @Test
    void testDecimalCalculation() {
        List<Fraction> fractions = userService.findFractionsByUserId("1");
        Fraction f = fractions.getFirst();

        double expected = (double) f.getNumerator() / f.getDenominator();
        assertEquals(expected, f.decimal());
    }

    @Test
    void testNoZeroDenominatorExceptSeeder() {
        List<Fraction> fractions = userService.findFractionsByUserId("1");

        for (Fraction f : fractions) {
            assertNotEquals(0, f.getDenominator());
        }
    }
}
