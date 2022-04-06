package project.dailynail.services;

import project.dailynail.models.binding.CommentCreateBindingModel;
import project.dailynail.models.service.CommentServiceModel;

public interface CommentService {

    void add(CommentServiceModel commentServiceModel, String id);

    void delete(String id);
}
