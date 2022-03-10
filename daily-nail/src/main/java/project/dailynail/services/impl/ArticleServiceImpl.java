package project.dailynail.services.impl;

import com.google.gson.Gson;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.dailynail.exceptions.ObjectNotFoundException;
import project.dailynail.models.binding.ArticleEditBindingModel;
import project.dailynail.models.binding.ArticleSearchBindingModel;
import project.dailynail.models.entities.ArticleEntity;
import project.dailynail.models.entities.CategoryEntity;
import project.dailynail.models.entities.SubcategoryEntity;
import project.dailynail.models.entities.enums.CategoryNameEnum;
import project.dailynail.models.entities.enums.SubcategoryNameEnum;
import project.dailynail.models.service.ArticleCreateServiceModel;
import project.dailynail.models.service.ArticleServiceModel;
import project.dailynail.models.service.UserServiceModel;
import project.dailynail.models.validators.ServiceLayerValidationUtil;
import project.dailynail.models.view.ArticlePreViewModel;
import project.dailynail.models.view.ArticlesAllViewModel;
import project.dailynail.models.view.ArticlesPageViewModel;
import project.dailynail.repositories.ArticleRepository;
import project.dailynail.services.*;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    private static final Integer ARTICLES_PER_PAGE = 4;
    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;
    private final ServiceLayerValidationUtil serviceLayerValidationUtil;
    private final Gson gson;
    private final UserService userService;
    private final CloudinaryService cloudinaryService;
    private final CategoryService categoryService;
    private final SubcategoryService subcategoryService;
    private final TopArticlesServiceImpl topArticlesService;

    public ArticleServiceImpl(ArticleRepository articleRepository, ModelMapper modelMapper, ServiceLayerValidationUtil serviceLayerValidationUtil, Gson gson, UserService userService, CloudinaryService cloudinaryService, CategoryService categoryService, SubcategoryService subcategoryService, @Lazy TopArticlesServiceImpl topArticlesService) {
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
        this.serviceLayerValidationUtil = serviceLayerValidationUtil;
        this.gson = gson;
        this.userService = userService;
        this.cloudinaryService = cloudinaryService;
        this.categoryService = categoryService;
        this.subcategoryService = subcategoryService;
        this.topArticlesService = topArticlesService;
    }

    @Override
    public void createArticle(ArticleCreateServiceModel articleCreateServiceModel) throws IOException {
        serviceLayerValidationUtil.validate(articleCreateServiceModel);
        StringBuilder sb = new StringBuilder();
        sb.append(LocalTime.now().toString()).append(" - ").append("After service validation").append(System.lineSeparator());
        UserServiceModel principal = userService.getPrincipal();
        String imageUrl = uploadImageAndGetCloudinaryUrl(articleCreateServiceModel.getImageUrl(), articleCreateServiceModel.getImageFile());
        sb.append(LocalTime.now().toString()).append(" - ").append("After uploading to Cloudinary").append(System.lineSeparator());
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

        if (articleCreateServiceModel.getTop() == null || articleCreateServiceModel.getTop().equals("No")) {
            articleServiceModel.setTop(false);
        } else {
            articleServiceModel.setTop(true);
        }
        sb.append(LocalTime.now().toString()).append(" - ").append("After creating articleServiceModel").append(System.lineSeparator());

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
        sb.append(LocalTime.now().toString()).append(" - ").append("After mapping to entity").append(System.lineSeparator());



        articleRepository.save(articleEntity);
        String createdArticleId = getIdOfLastCreatedArticle();

        if (articleServiceModel.isTop()) {
            topArticlesService.add(createdArticleId);
        }

        sb.append(LocalTime.now().toString()).append(" - ").append("After saving").append(System.lineSeparator());
        System.out.println(sb.toString());
    }

    @Override
    public ArticlesPageViewModel getAllArticlesForAdminPanel() {
        return getAllArticlesForAdminPanel(1);
    }

    @Override
    public ArticlesPageViewModel getAllArticlesForAdminPanel(Integer page) {
        Pageable pageable = PageRequest.of(page - 1, ARTICLES_PER_PAGE);
        Page<ArticleEntity> entities = articleRepository.findAllByOrderByCreatedDesc(pageable);


        List<ArticlesAllViewModel> articles = entities
                .stream()
                .map(entity -> modelMapper.map(entity, ArticlesAllViewModel.class)
                        .setAuthor(entity.getAuthor().getFullName())
                        .setCategory(getCategoryName(entity.getCategory(), entity.getSubcategory()))
                        .setCreated(getTimeAsString(entity.getCreated()))
                        .setComments(entity.getComments().size())
                        .setPosted(getTimeAsString(entity.getPosted())))
                .collect(Collectors.toList());
        ArticlesPageViewModel articlesPageViewModel = new ArticlesPageViewModel()
                .setContent(articles)
                .setTotalElements(entities.getTotalElements())
                .setTotalPages(entities.getTotalPages());

        return articlesPageViewModel;
    }

    @Override
    public ArticlesPageViewModel getFilteredArticles(ArticleSearchBindingModel articleSearchBindingModel) {
        String category = articleSearchBindingModel.getCategory().toUpperCase().replace(" 19", "_19").replace(" - ", "");
        String activated = "e";
        if (articleSearchBindingModel.getArticleStatus().equals("Activated")) {
            activated = "true";
        } else if(articleSearchBindingModel.getArticleStatus().equals("Waiting")) {
            activated = "false";
        }
        int days = 1000000;

        switch (articleSearchBindingModel.getTimePeriod()) {
            case "Today": days = 0; break;
            case "Last three days": days = 3; break;
            case "Last week": days = 7; break;
            case "Last month": days = 30; break;
            case "Last year": days = 365; break;
            default:
        }

        Page<String> articleIds = articleRepository.findAllArticleIdBySearchFilter(articleSearchBindingModel.getKeyWord(), category,
                articleSearchBindingModel.getAuthorName(), activated, days, PageRequest.of(articleSearchBindingModel.getPage() - 1, ARTICLES_PER_PAGE));

        List<ArticlesAllViewModel> articles = articleIds
                .stream()
                .map(articleRepository::findById)
                .map(opt -> opt.orElse(null))
                .map(entity -> modelMapper.map(entity, ArticlesAllViewModel.class)
                        .setAuthor(entity.getAuthor().getFullName())
                        .setCategory(getCategoryName(entity.getCategory(), entity.getSubcategory()))
                        .setCreated(getTimeAsString(entity.getCreated()))
                        .setPosted(getTimeAsString(entity.getPosted())))
                .collect(Collectors.toList());

        ArticlesPageViewModel articlesPageViewModel = new ArticlesPageViewModel()
                .setContent(articles)
                .setTotalPages(articleIds.getTotalPages())
                .setTotalElements(articleIds.getTotalElements());

        return articlesPageViewModel;
    }

    @Override
    public ArticleEditBindingModel getArticleEditBindingModelById(String id) {
        ArticleEntity articleEntity = articleRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
        String categoryName = getCategoryName(articleEntity.getCategory(), articleEntity.getSubcategory()).replace("_", " ");
        categoryName = categoryName.charAt(0) + categoryName.substring(1).toLowerCase() + " ";
        ArticleEditBindingModel articleEditBindingModel = new ArticleEditBindingModel();
        articleEditBindingModel
                .setTitle(articleEntity.getTitle())
                .setText(articleEntity.getText())
                .setImageUrl("")
                .setPosted(articleEntity.getPosted())
                .setActivated(articleEntity.getPosted() != null)
                .setCategoryName(categoryName)
                .setTop(articleEntity.isTop() ? "Yes" : "No")
                .setDisabledComments(articleEntity.isDisabledComments() ? "Yes" : "No");
         return articleEditBindingModel;
    }

    @Override
    public void deleteArticle(String id) {
        articleRepository.deleteById(id);
        topArticlesService.remove(id);
    }

    @Override
    public void editArticle(ArticleEditBindingModel articleEditBindingModel) throws IOException {
        ArticleEntity articleEntity = articleRepository
                .findById(articleEditBindingModel.getId()).orElseThrow(ObjectNotFoundException::new);

         if (!articleEditBindingModel.getCategoryName().equals("Select Category")) {
             String fixedCategoryName = articleEditBindingModel.getCategoryName().replace(" 19", "_19").replace(" - ", "").toUpperCase();

             CategoryEntity tryCategory = null;
             SubcategoryEntity trySubCategory = null;

             try {
                 tryCategory = modelMapper
                         .map(categoryService.findByCategoryNameStr(fixedCategoryName), CategoryEntity.class);
             } catch (IllegalArgumentException ignored) {}

             try {
                 trySubCategory = modelMapper
                         .map(subcategoryService.findBySubcategoryNameStr(fixedCategoryName), SubcategoryEntity.class);
             } catch (IllegalArgumentException ignored) {}


            if (tryCategory != null) {
                articleEntity.setCategory(tryCategory);
                if (!tryCategory.getCategoryName().equals(CategoryNameEnum.SPORTS)) {
                    articleEntity.setSubcategory(null);
                }
            }
            if (trySubCategory != null) {
                articleEntity.setSubcategory(trySubCategory);
                if (trySubCategory.getSubcategoryName().equals(SubcategoryNameEnum.FOOTBALL)
                        || trySubCategory.getSubcategoryName().equals(SubcategoryNameEnum.VOLLEYBALL)
                        || trySubCategory.getSubcategoryName().equals(SubcategoryNameEnum.TENNIS)
                        || trySubCategory.getSubcategoryName().equals(SubcategoryNameEnum.OTHER)) {
                    articleEntity.setCategory(modelMapper.map(categoryService.findByCategoryName(CategoryNameEnum.SPORTS), CategoryEntity.class));
                }
            }
        }



        if (!articleEntity.getTitle().equals(articleEditBindingModel.getTitle())) {
            articleEntity.setTitle(articleEditBindingModel.getTitle());
            articleEntity.setUrl(getTitleUrl(articleEntity.getTitle()));
        }

        if (!articleEntity.getText().equals(articleEditBindingModel.getText())) {
            articleEntity.setText(articleEditBindingModel.getText());
        }

        if (!articleEditBindingModel.getImageUrl().equals("") || !articleEditBindingModel.getImageFile().getContentType().equals("application/octet-stream")) {
            articleEntity.setImageUrl(uploadImageAndGetCloudinaryUrl(articleEditBindingModel.getImageUrl(), articleEditBindingModel.getImageFile()));
        }

        if (!articleEntity.isDisabledComments() && articleEditBindingModel.getDisabledComments().equals("Yes")) {
            articleEntity.setDisabledComments(true);
        }

        if (articleEntity.isDisabledComments() && articleEditBindingModel.getDisabledComments().equals("No")) {
            articleEntity.setDisabledComments(false);
        }

        if (!articleEntity.isActivated() && articleEditBindingModel.isActivated() && articleEditBindingModel.getPosted() != null) {
            articleEntity.setActivated(true);
            articleEntity.setPosted(articleEditBindingModel.getPosted());
        }

        if (articleEntity.isActivated() && !articleEditBindingModel.isActivated()) {
            articleEntity.setActivated(false);
            articleEntity.setPosted(null);
        }

        if (articleEntity.isTop() && !articleEditBindingModel.getTop().equals("Yes")) {
            topArticlesService.remove(articleEntity.getId());
            articleEntity.setTop(false);
        }

        if (!articleEntity.isTop() && articleEditBindingModel.getTop().equals("Yes")) {
            topArticlesService.add(articleEntity.getId());
            articleEntity.setTop(true);
        }

        articleRepository.save(articleEntity);
    }

    @Override
    public ArticlePreViewModel getNewestArticleByCategoryName(CategoryNameEnum categoryNameEnum, LocalDateTime now) {
        ArticlePreViewModel articlePreViewModel = modelMapper.map(articleRepository.findById(articleRepository.findFirstByCategoryNameOrderByPostedDesc(categoryNameEnum, now)).orElseThrow(), ArticlePreViewModel.class);
        articlePreViewModel.setText(articlePreViewModel.getText().substring(0, 128) + "...");
        return articlePreViewModel;


    }

    @Override
    public List<ArticlePreViewModel> getFourArticlesByCategoryName(CategoryNameEnum categoryNameEnum, LocalDateTime now) {
        return articleRepository
                .findFourByCategoryNameOrderByPostedDesc(categoryNameEnum, now)
                .stream()
                .map(str -> articleRepository.findById(str).orElseThrow())
                .map(entity -> modelMapper.map(entity, ArticlePreViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticlePreViewModel> getLatestFiveArticles(LocalDateTime now) {
        return articleRepository.findLatestArticles(5, now)
                .stream()
                .map(str -> articleRepository.findById(str).orElseThrow())
                .map(entity -> modelMapper.map(entity, ArticlePreViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticlePreViewModel> getLatestNineArticles(LocalDateTime now) {
        return articleRepository.findLatestArticles(9, now)
                .stream()
                .map(str -> articleRepository.findById(str).orElseThrow())
                .map(entity -> modelMapper.map(entity, ArticlePreViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void setTopFalse(String poppedOutId) {
//        ArticleEntity articleEntity = articleRepository.findById(poppedOutId).orElseThrow();
//                articleEntity.setTop(false);
//        articleRepository.saveAndFlush(articleEntity);
        articleRepository.updateTop(poppedOutId, false);
    }

    @Override
    @Transactional
    public void setTopTrue(String id) {
//        ArticleEntity articleEntity = articleRepository.findById(id).orElseThrow();
//                articleEntity.setTop(true);
//        articleRepository.saveAndFlush(articleEntity);
        articleRepository.updateTop(id, true);
    }

    @Override
    public List<ArticlePreViewModel> getTopArticles(LocalDateTime now) {
        return articleRepository.findAllById(topArticlesService.getTopArticlesIds())
        .stream()
                .map(entity -> modelMapper.map(entity, ArticlePreViewModel.class)
                        .setText(entity.getText().substring(0, 128) + "..."))
        .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllTopArticlesIds(LocalDateTime now) {
        return articleRepository.findAllByTopIsTrue(now);
    }


    @Override
    public List<String> getTimePeriods() {
        return List.of("Today", "Last three days", "Last week", "Last month", "Last year");
    }

    @Override
    public List<String> getArticleStatuses() {
        return List.of("Activated", "Waiting");
    }

    private String getCategoryName(CategoryEntity category, SubcategoryEntity subcategory) {
        if (subcategory == null) {
            return category.getCategoryName().name();
        }
        return subcategory.getSubcategoryName().name();
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

    private String getIdOfLastCreatedArticle() {
        return articleRepository.getIdOfLastCreatedArticle();
    }
}
