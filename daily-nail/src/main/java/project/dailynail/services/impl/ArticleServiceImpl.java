package project.dailynail.services.impl;

import com.google.gson.Gson;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import project.dailynail.models.view.ArticlesAllViewModel;
import project.dailynail.repositories.ArticleRepository;
import project.dailynail.services.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

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
        categoryName = categoryName.toUpperCase().replace(" 19", "_19");
        if (categoryName.contains(" - ")) {
            categoryName = categoryName.replace(" - ", "");
            articleServiceModel.setSubcategory(subcategoryService.findBySubcategoryNameStr(categoryName));
        } else {
            articleServiceModel.setCategory(categoryService.findByCategoryNameStr(categoryName));
        }

        ArticleEntity articleEntity = modelMapper.map(articleServiceModel, ArticleEntity.class);
        if (articleEntity.getCategory() != null) {
            CategoryEntity categoryEntity = modelMapper.map(categoryService.findByCategoryName(articleServiceModel.getCategory().getCategoryName()), CategoryEntity.class);
            articleEntity.setCategory(categoryEntity);
        } else {
            SubcategoryEntity subcategoryEntity = modelMapper.map(subcategoryService.findBySubcategoryNameEnum(articleServiceModel.getSubcategory().getSubcategoryName()), SubcategoryEntity.class);
            CategoryEntity categoryEntity = modelMapper.map(categoryService.findByCategoryName(subcategoryEntity.getCategory().getCategoryName()), CategoryEntity.class);
            articleEntity.setSubcategory(subcategoryEntity)
                    .setCategory(categoryEntity);
        }

        articleRepository.save(articleEntity);
    }

    @Override
    public Page<ArticlesAllViewModel> getAllArticlesForAdminPanel(Integer page, Integer pageSize) {
        List<ArticlesAllViewModel> articles = articleRepository
                .findAll(PageRequest.of(page, pageSize))
                .stream()
                .map(entity -> modelMapper.map(entity, ArticlesAllViewModel.class)
                        .setAuthor(entity.getAuthor().getFullName())
                        .setCategory(getCategoryName(entity.getCategory(), entity.getSubcategory()))
                        .setCreated(getTimeAsString(entity.getCreated()))
                        .setPosted(getTimeAsString(entity.getPosted())))
                .collect(Collectors.toList());

        return new PageImpl<>(articles);
    }

    @Override
    public List<String> getTimePeriods() {
        return List.of("Today", "Yesterday", "Last five days", "Last month", "Last year");
    }

    @Override
    public List<String> getArticleStatuses() {
        return List.of("Activated", "Waiting");
    }

    private String getCategoryName(CategoryEntity category, SubcategoryEntity subcategory) {
        if (category == null) {
            return subcategory.getSubcategoryName().name();
        }
        return category.getCategoryName().name();
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

    private String getTimeAsString(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return "-";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
        return localDateTime.format(formatter);
    }
}
