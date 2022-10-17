package project.dailynail.services.impl;

import com.google.gson.Gson;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.dailynail.constants.GlobalConstants;
import project.dailynail.exceptions.ObjectNotFoundException;
import project.dailynail.models.binding.ArticleEditBindingModel;
import project.dailynail.models.binding.ArticleSearchBindingModel;
import project.dailynail.models.dtos.json.ArticleEntityExportDto;
import project.dailynail.models.entities.ArticleEntity;
import project.dailynail.models.entities.CategoryEntity;
import project.dailynail.models.entities.SubcategoryEntity;
import project.dailynail.models.entities.UserEntity;
import project.dailynail.models.entities.enums.CategoryNameEnum;
import project.dailynail.models.entities.enums.SubcategoryNameEnum;
import project.dailynail.models.service.*;
import project.dailynail.models.validators.ServiceLayerValidationUtil;
import project.dailynail.models.view.*;
import project.dailynail.repositories.ArticleRepository;
import project.dailynail.services.*;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    private static final Integer ARTICLES_PER_PAGE = 6;
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
                .setComments(new ArrayList<>());

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
    @Transactional
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
    @Transactional
    public ArticlePageVModel getAllArticlesByCategory(String category, LocalDateTime now) {
        return getAllArticlesByCategory(category, now,1);
    }

    @Override
    @Transactional
    public ArticlePageVModel getAllArticlesByCategory(String category, LocalDateTime now, Integer page) {
        Pageable pageable = PageRequest.of(page - 1, ARTICLES_PER_PAGE);
        CategoryServiceModel categoryServiceModel = categoryService.findByCategoryNameStr(category.toUpperCase());
        SubcategoryServiceModel subcategoryServiceModel = subcategoryService.findBySubcategoryNameStr(category.toUpperCase());
        SubcategoryEntity subcategoryEntity = null;
        CategoryEntity categoryEntity = null;

        if (subcategoryServiceModel != null) {
           subcategoryEntity = modelMapper.map(subcategoryServiceModel, SubcategoryEntity.class);
        }
        if (categoryServiceModel != null) {
            categoryEntity = modelMapper.map(categoryServiceModel, CategoryEntity.class);
        }
        Page<ArticleEntity> entities;
        if (subcategoryEntity == null) {
            entities = articleRepository.findAllByCategoryNameOrderByPostedDesc(categoryEntity.getCategoryName(), now, pageable);
        } else {
            entities = articleRepository.findAllBySubcategoryNameOrderByPostedDesc(subcategoryEntity.getSubcategoryName(), now, pageable);
        }

        List<ArticleViewModel> articleViewModels = entities
                .stream()
                .map(entity -> modelMapper.map(entity, ArticleViewModel.class)
                .setText(entity.getText().replaceAll("&[^;]*;", "").replaceAll("<[^>]*>", "").substring(0, 128) + "...")
                .setPosted(getTimeAsStringForCategoryArticles(entity.getPosted())))
                .collect(Collectors.toList());

        ArticlePageVModel articlePageVModel = new ArticlePageVModel()
                .setContent(articleViewModels)
                .setTotalElements(entities.getTotalElements())
                .setTotalPages(entities.getTotalPages());

        return articlePageVModel;
    }

    @Override
    @Transactional
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

        List<ArticlesAllViewModel> articles = articleRepository.findAllByIdInJoinWithComments(articleIds.getContent())
                .stream()
                .map(entity -> modelMapper.map(entity, ArticlesAllViewModel.class)
                        .setAuthor(entity.getAuthor().getFullName())
                        .setCategory(getCategoryName(entity.getCategory(), entity.getSubcategory()))
                        .setCreated(getTimeAsString(entity.getCreated()))
                        .setPosted(getTimeAsString(entity.getPosted()))
                        .setComments(entity.getComments().size()))
                .collect(Collectors.toList());

        ArticlesPageViewModel articlesPageViewModel = new ArticlesPageViewModel()
                .setContent(articles)
                .setTotalPages(articleIds.getTotalPages())
                .setTotalElements(articleIds.getTotalElements());

        return articlesPageViewModel;
    }

    @Override
    @Transactional
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
        articlePreViewModel.setText(articlePreViewModel.getText().replaceAll("&[^;]*;", "").replaceAll("<[^>]*>", "").substring(0, 128) + "...");
        return articlePreViewModel;


    }

    @Override
    public List<ArticlePreViewModel> getFourArticlesByCategoryName(CategoryNameEnum categoryNameEnum, LocalDateTime now) {
        List<String> articleIds = articleRepository.findFourByCategoryNameOrderByPostedDesc(categoryNameEnum, now);
        List<ArticlePreViewModel> articlePreViewModels = articleRepository.findAllByIdIn(articleIds)
                .stream()
                .map(entity -> modelMapper.map(entity, ArticlePreViewModel.class))
                .collect(Collectors.toList());

        return articlePreViewModels;
    }

    @Override
    public List<ArticlePreViewModel> getLatestFiveArticles(LocalDateTime now) {
        Pageable pageable = PageRequest.of(0, 5);
        List<ArticleEntity> entities = articleRepository.findLatestArticles(now, pageable).getContent();
        return entities
                .stream()
                .map(entity -> modelMapper.map(entity, ArticlePreViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticlePreViewModel> getLatestNineArticles(LocalDateTime now) {
        Pageable pageable = PageRequest.of(0, 9);
        List<ArticleEntity> entities = articleRepository.findLatestArticles(now, pageable).getContent();
        return entities
                .stream()
                .map(entity -> modelMapper.map(entity, ArticlePreViewModel.class)
                .setImageUrl(entity.getImageUrl().replaceAll("upload/", "upload/" + "c_scale,h_20,w_25/")))
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticlePreViewModel> getFiveMostPopular() {
        Pageable pageable = PageRequest.of(0, 5);
        List<ArticleEntity> entities = articleRepository.getFiveMostPopular(pageable);
        return entities
                .stream()
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
    @Transactional
    public List<ArticlePreViewModel> getTopArticles(LocalDateTime now) {
        List<String> topArticlesIds = topArticlesService.getTopArticlesIds().stream().toList();
        return articleRepository.findAllByIdIn(topArticlesIds)
        .stream()
                .map(entity -> modelMapper.map(entity, ArticlePreViewModel.class)
                        .setText(entity.getText().replaceAll("<[^>]*>", "").substring(0, 128) + "..."))
        .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllTopArticlesIds(LocalDateTime now) {
        return articleRepository.findAllByTopIsTrue(now);
    }

    @Override
    @Transactional
    public ArticleViewModel getArticleViewModelByUrl(String url) {
        ArticleEntity articleEntity = articleRepository.findFirstByUrlOrderByCreatedDesc(url);
        ArticleServiceModel articleServiceModel = modelMapper.map(articleEntity, ArticleServiceModel.class);
        articleServiceModel.setComments(articleEntity
                .getComments()
                .stream()
                .sorted((a, b) -> b.getTimePosted().compareTo(a.getTimePosted()))
                .map(entity -> modelMapper.map(entity, CommentServiceModel.class))
                .collect(Collectors.toList()));
        ArticleViewModel articleViewModel = modelMapper.map(articleServiceModel, ArticleViewModel.class)
                .setPosted(getTimeAsStringForView(articleEntity.getPosted()));
        articleViewModel.setComments(articleServiceModel
                .getComments()
        .stream()
        .map(csm -> modelMapper.map(csm, CommentViewModel.class)
                    .setTimePosted(getTimeAsStringForView(csm.getTimePosted())))
        .collect(Collectors.toList()));
        return articleViewModel;
    }

    @Override
    @Transactional
    public ArticleServiceModel getArticleById(String id) {
        return articleRepository.findById(id)
                .map(e -> modelMapper.map(e, ArticleServiceModel.class))
                .orElseThrow();
    }

    @Override
    public String getArticleUrlById(String id) {
        return articleRepository.findById(id).map(ArticleEntity::getUrl).orElse(null);
    }

    @Override
    public String getArticleUrlByCommentId(String id) {
        return articleRepository.findArticleEntityUrlByCommentId(id);
    }

    @Override
    @Transactional
    public List<ArticleEntityExportDto> exportArticles() {
        List<ArticleEntityExportDto> articleEntityExportDtos = articleRepository.findAll()
                .stream()
                .map(entity -> modelMapper.map(entity, ArticleEntityExportDto.class)
                .setCreated(entity.getCreated().toString().substring(0, 16)))
                .collect(Collectors.toList());

        return articleEntityExportDtos;

    }

    @Override
    public void seedArticles() throws FileNotFoundException {
        ArticleEntityExportDto[] articleEntityExportDtos = gson
                .fromJson(new FileReader(GlobalConstants.ARTICLES_FILE_PATH), ArticleEntityExportDto[].class);


        List<ArticleEntity> articleEntities = Arrays.stream(articleEntityExportDtos)

                .map(dto -> modelMapper.map(dto, ArticleEntity.class)
                        .setAuthor(modelMapper.map(userService.findByEmail(dto.getAuthorEmail()), UserEntity.class))
                        .setCategory(categoryService.findByCategoryNameStr(dto.getCategoryName()) == null ? null
                                : modelMapper.map(categoryService.findByCategoryNameStr(dto.getCategoryName()), CategoryEntity.class))
                        .setSubcategory(subcategoryService.findBySubcategoryNameStr(dto.getSubcategoryName()) == null ? null
                                : modelMapper.map(subcategoryService.findBySubcategoryNameStr(dto.getSubcategoryName()), SubcategoryEntity.class))
                .setCreated(getLocalDateTimeFromString(dto.getCreated()))
                .setPosted(getLocalDateTimeFromString(dto.getPosted())))
                .collect(Collectors.toList());

            articleRepository.saveAllAndFlush(articleEntities);

            getAllTopArticlesIds(LocalDateTime.now())
                    .forEach(topArticlesService::add);
    }

    @Override
    @Transactional
    public ArticleServiceModel getArticleByUrl(String url) {
        return articleRepository.findByUrl(url)
                .map(entity -> modelMapper.map(entity, ArticleServiceModel.class))
                .orElseThrow();
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

    private String getTimeAsStringForView(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return "-";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy | HH:mm");
        return localDateTime.format(formatter);
    }

    private String getTimeAsStringForCategoryArticles(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm | dd.MM.yyyy");
        return localDateTime.format(formatter);
    }

    @Override
    public LocalDateTime getLocalDateTimeFromString(String time) {
        if (time == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return LocalDateTime.parse(time, formatter);
    }

    @Override
    public boolean hasArticles() {
        return articleRepository.count() > 0;
    }

    @Transactional
    @Async
    @Override
    public void increaseSeenByOne(String id, Integer seen) {
        articleRepository.increaseSeen(id, seen + 1);
    }

    @Override
    public List<CategoryViewsCountModel> getCategoryViews() {
        List<CategoryViewsCountModel> categoryViews = new ArrayList<>();
        int totalViews = articleRepository.getTotalArticleViews();
        for (CategoryNameEnum categoryNameEnum : CategoryNameEnum.values()) {
            CategoryViewsCountModel countModel = new CategoryViewsCountModel()
                    .setCategoryNameEnum(categoryNameEnum.name())
                    .setViews(articleRepository.getTotalViewsByCategoryNameEnum(categoryNameEnum) == null ? 0 : articleRepository.getTotalViewsByCategoryNameEnum(categoryNameEnum))
                    .setTotalViews(totalViews);
            categoryViews.add(countModel);
        }
        return categoryViews;
    }

    private String getIdOfLastCreatedArticle() {
        return articleRepository.getIdOfLastCreatedArticle();
    }
}
