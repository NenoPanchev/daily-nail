package project.dailynail.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.dailynail.models.service.ImageServiceModel;
import project.dailynail.repositories.ImageRepository;
import project.dailynail.services.ImageService;

import java.io.IOException;


@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;
    private final CloudinaryServiceImpl cloudinaryService;

    public ImageServiceImpl(ImageRepository imageRepository, ModelMapper modelMapper, CloudinaryServiceImpl cloudinaryService) {
        this.imageRepository = imageRepository;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
    }


    @Override
    public ImageServiceModel save(MultipartFile multipartFile) throws IOException {
        String cloudinaryUrl = cloudinaryService.uploadImage(multipartFile);

        return null;
    }
}
