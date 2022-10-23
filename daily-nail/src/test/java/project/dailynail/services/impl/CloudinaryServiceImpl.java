package project.dailynail.services.impl;

import com.cloudinary.Cloudinary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;
import project.dailynail.services.CloudinaryService;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CloudinaryServiceImpl {
    private CloudinaryService serviceToTest;

    @BeforeEach
    void setUp() {
        serviceToTest = spy(CloudinaryService.class);
    }

    @Test
    void uploadImageTest() throws IOException {
        MultipartFile file = mock(MultipartFile.class);
        when(serviceToTest.uploadImage(file))
                .thenReturn("123");
        String expected = serviceToTest.uploadImage(file);
        Assertions.assertEquals(expected, "123");
    }
}
