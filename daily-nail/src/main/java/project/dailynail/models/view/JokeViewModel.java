package project.dailynail.models.view;

public class JokeViewModel {
    private String id;
    private String text;

    public JokeViewModel() {
    }

    public String getId() {
        return id;
    }

    public JokeViewModel setId(String id) {
        this.id = id;
        return this;
    }

    public String getText() {
        return text;
    }

    public JokeViewModel setText(String text) {
        this.text = text;
        return this;
    }
}
