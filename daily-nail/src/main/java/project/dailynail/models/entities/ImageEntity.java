package project.dailynail.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "images")
public class ImageEntity extends BaseEntity {
    private String url;

    public ImageEntity() {
    }

    @Column(unique = true, nullable = false)
    public String getUrl() {
        return url;
    }

    public ImageEntity setUrl(String url) {
        this.url = url;
        return this;
    }

}

