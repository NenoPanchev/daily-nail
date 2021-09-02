package project.dailynail.models.service;

public class ImageServiceModel extends BaseServiceModel {
    private String url;

    public ImageServiceModel() {
    }

    public String getUrl() {
        return url;
    }

    public ImageServiceModel setUrl(String url) {
        this.url = url;
        return this;
    }
}
