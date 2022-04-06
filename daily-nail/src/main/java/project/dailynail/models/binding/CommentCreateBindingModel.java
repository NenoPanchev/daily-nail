package project.dailynail.models.binding;

import javax.validation.constraints.Size;

public class CommentCreateBindingModel {
    private String text;

    public CommentCreateBindingModel() {
    }

    @Size(min = 4, max = 250, message = "Comment must be between 4 and 250 characters.")
    public String getText() {
        return text;
    }

    public CommentCreateBindingModel setText(String text) {
        this.text = text;
        return this;
    }
}
