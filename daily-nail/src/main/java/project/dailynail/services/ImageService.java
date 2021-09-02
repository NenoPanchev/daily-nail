package project.dailynail.services;

import org.springframework.web.multipart.MultipartFile;
import project.dailynail.models.service.ImageServiceModel;

import java.io.IOException;

public interface ImageService {
    ImageServiceModel save(MultipartFile multipartFile) throws IOException;
}
