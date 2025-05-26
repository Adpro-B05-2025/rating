package id.ac.ui.cs.advprog.rating;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
class RatingApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Test
    void contextLoads() {
        assertNotNull(dataSource);
    }
}