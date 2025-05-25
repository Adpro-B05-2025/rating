package id.ac.ui.cs.advprog.rating;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
@ActiveProfiles("test")
class RatingApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void mainMethodShouldCallSpringApplicationRun() {
        try (MockedStatic<SpringApplication> mocked = Mockito.mockStatic(SpringApplication.class)) {
            mocked.when(() -> SpringApplication.run(eq(RatingApplication.class), any(String[].class)))
                    .thenReturn(null);

            RatingApplication.main(new String[]{});

            mocked.verify(() -> SpringApplication.run(eq(RatingApplication.class), any(String[].class)), Mockito.times(1));
        }
    }
}
