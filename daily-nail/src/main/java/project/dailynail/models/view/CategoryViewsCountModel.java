package project.dailynail.models.view;

public class CategoryViewsCountModel {
    private String categoryNameEnum;
    private int views;
    private int totalViews;

    public CategoryViewsCountModel() {
    }

    public String getCategoryNameEnum() {
        return categoryNameEnum;
    }

    public CategoryViewsCountModel setCategoryNameEnum(String categoryNameEnum) {
        this.categoryNameEnum = categoryNameEnum;
        return this;
    }

    public int getViews() {
        return views;
    }

    public CategoryViewsCountModel setViews(int views) {
        this.views = views;
        return this;
    }

    public int getTotalViews() {
        return totalViews;
    }

    public CategoryViewsCountModel setTotalViews(int totalViews) {
        this.totalViews = totalViews;
        return this;
    }

    public String getWidthPercentage() {
        double percentage = 100.0 * views / totalViews;
        return String.format("width: %.2f%%", percentage).replace(",", ".");
    }
}
