package project.dailynail.services.impl;

import com.google.gson.Gson;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.dailynail.models.entities.ArticleEntity;
import project.dailynail.models.entities.CategoryEntity;
import project.dailynail.models.entities.SubcategoryEntity;
import project.dailynail.models.entities.enums.CategoryNameEnum;
import project.dailynail.models.entities.enums.SubcategoryNameEnum;
import project.dailynail.models.service.ArticleCreateServiceModel;
import project.dailynail.models.service.ArticleServiceModel;
import project.dailynail.models.service.UserServiceModel;
import project.dailynail.models.validators.ServiceLayerValidationUtil;
import project.dailynail.repositories.ArticleRepository;
import project.dailynail.services.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;
    private final ServiceLayerValidationUtil serviceLayerValidationUtil;
    private final Gson gson;
    private final UserService userService;
    private final CloudinaryService cloudinaryService;
    private final CategoryService categoryService;
    private final SubcategoryService subcategoryService;

    public ArticleServiceImpl(ArticleRepository articleRepository, ModelMapper modelMapper, ServiceLayerValidationUtil serviceLayerValidationUtil, Gson gson, UserService userService, CloudinaryService cloudinaryService, CategoryService categoryService, SubcategoryService subcategoryService) {
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
        this.serviceLayerValidationUtil = serviceLayerValidationUtil;
        this.gson = gson;
        this.userService = userService;
        this.cloudinaryService = cloudinaryService;
        this.categoryService = categoryService;
        this.subcategoryService = subcategoryService;
    }

    @Override
    public void createArticle(ArticleCreateServiceModel articleCreateServiceModel) throws IOException {
        serviceLayerValidationUtil.validate(articleCreateServiceModel);
        UserServiceModel principal = userService.getPrincipal();
        String imageUrl = uploadImageAndGetCloudinaryUrl(articleCreateServiceModel.getImageUrl(), articleCreateServiceModel.getImageFile());
        boolean activated = articleCreateServiceModel.getPosted() != null;
        ArticleServiceModel articleServiceModel = modelMapper.map(articleCreateServiceModel, ArticleServiceModel.class)
                .setAuthor(principal)
                .setUrl(getTitleUrl(articleCreateServiceModel.getTitle()))
                .setImageUrl(imageUrl)
                .setCreated(LocalDateTime.now())
                .setActivated(activated)
                .setDisabledComments(articleCreateServiceModel.getDisabledComments() != null)
                .setSeen(0)
                .setComments(new HashSet<>());

        String categoryName = articleCreateServiceModel.getCategoryName();
        categoryName = categoryName.toUpperCase().replace(" ", "_");
        if (categoryName.contains(" - ")) {
            categoryName = categoryName.replace(" - ", "");
            articleServiceModel.setSubcategory(SubcategoryNameEnum.valueOf(categoryName));
        } else {
            articleServiceModel.setCategory(CategoryNameEnum.valueOf(categoryName));
        }

        ArticleEntity articleEntity = modelMapper.map(articleServiceModel, ArticleEntity.class);
        if (articleEntity.getCategory() != null) {
            CategoryEntity categoryEntity = modelMapper.map(categoryService.findByCategoryName(articleServiceModel.getCategory()), CategoryEntity.class);
            articleEntity.setCategory(categoryEntity);
        } else {
            SubcategoryEntity subcategoryEntity = modelMapper.map(subcategoryService.findBySubcategoryNameEnum(articleServiceModel.getSubcategory()), SubcategoryEntity.class);
            CategoryEntity categoryEntity = modelMapper.map(categoryService.findByCategoryName(subcategoryEntity.getCategory().getCategoryName()), CategoryEntity.class);
            articleEntity.setSubcategory(subcategoryEntity)
                    .setCategory(categoryEntity);
        }
        System.out.println();
    }

    private String getTitleUrl(String title) {
        String titleUrl = title.toLowerCase().replaceAll("[^a-zA-Z0-9]+", "-");
        if (titleUrl.charAt(0) == '-') {
            titleUrl = titleUrl.substring(1);
        }
        if (titleUrl.charAt(titleUrl.length() - 1) == '-') {
            titleUrl = titleUrl.substring(0, titleUrl.lastIndexOf('-'));
        }
        return titleUrl;
    }

    private String uploadImageAndGetCloudinaryUrl(String imageUrl, MultipartFile imageFile) throws IOException {
        MultipartFile file = imageFile;
        if (!imageUrl.isEmpty()) {
            file = getMultipartFileFromImageUrl(imageUrl);
        }
        return cloudinaryService.uploadImage(file);
    }

    private MultipartFile getMultipartFileFromImageUrl(String imageUrl) throws IOException {
        BufferedImage image = ImageIO.read(new URL(imageUrl));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);
        byteArrayOutputStream.flush();
        String fileName = RandomString.make() + new Date().getTime() + ".jpg";
        MultipartFile multipartFile = new MockMultipartFile(fileName,fileName, "image/jpg",byteArrayOutputStream.toByteArray());
        return multipartFile;
    }
}
