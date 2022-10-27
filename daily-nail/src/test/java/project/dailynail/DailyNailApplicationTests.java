package project.dailynail;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import project.dailynail.services.CloudinaryService;

@SpringBootTest
class DailyNailApplicationTests {
    @MockBean
    private CloudinaryService cloudinaryService;

    @Test
    void contextLoads() {
    }

}
