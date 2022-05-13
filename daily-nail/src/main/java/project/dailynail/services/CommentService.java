package project.dailynail.services;

import project.dailynail.models.binding.CommentCreateBindingModel;
import project.dailynail.models.dtos.json.CommentEntityExportDto;
import project.dailynail.models.service.CommentServiceModel;

import java.io.FileNotFoundException;
import java.util.List;

public interface CommentService {

    void add(CommentServiceModel commentServiceModel, String id);

    void delete(String id);

    List<CommentEntityExportDto> exportComments();

    void seedComments() throws FileNotFoundException;
}
