package project.dailynail.models.entities;

import project.dailynail.models.entities.enums.CategoryNameEnum;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity {
    private CategoryNameEnum categoryName;
    private Set<SubcategoryEntity> subcategories;

    public CategoryEntity() {
    }

    @Enumerated(EnumType.STRING)
    public CategoryNameEnum getCategoryName() {
        return categoryName;
    }

    public CategoryEntity setCategoryName(CategoryNameEnum categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    @OneToMany(mappedBy = "category", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    public Set<SubcategoryEntity> getSubcategories() {
        return subcategories;
    }

    public CategoryEntity setSubcategories(Set<SubcategoryEntity> subcategories) {
        this.subcategories = subcategories;
        return this;
    }
}
