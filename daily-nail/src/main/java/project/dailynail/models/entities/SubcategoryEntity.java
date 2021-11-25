package project.dailynail.models.entities;

import project.dailynail.models.entities.enums.SubcategoryNameEnum;

import javax.persistence.*;

@Entity
@Table(name = "subcategories")
public class SubcategoryEntity extends BaseEntity{
    private SubcategoryNameEnum subcategoryName;
    private CategoryEntity category;

    public SubcategoryEntity() {
    }

    @Enumerated(EnumType.STRING)
    public SubcategoryNameEnum getSubcategoryName() {
        return subcategoryName;
    }

    public SubcategoryEntity setSubcategoryName(SubcategoryNameEnum subcategoryName) {
        this.subcategoryName = subcategoryName;
        return this;
    }

    @ManyToOne
    public CategoryEntity getCategory() {
        return category;
    }

    public SubcategoryEntity setCategory(CategoryEntity category) {
        this.category = category;
        return this;
    }
}
