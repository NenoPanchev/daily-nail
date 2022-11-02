package project.dailynail.models.dtos.json;

import com.google.gson.annotations.Expose;

public class JokeEntityExportDto {
    @Expose
    private String authorEmail;
    @Expose
    private String text;
    @Expose
    private String created;

    public JokeEntityExportDto() {
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public JokeEntityExportDto setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
        return this;
    }

    public String getText() {
        return text;
    }

    public JokeEntityExportDto setText(String text) {
        this.text = text;
        return this;
    }

    public String getCreated() {
        return created;
    }

    public JokeEntityExportDto setCreated(String created) {
        this.created = created;
        return this;
    }
}
