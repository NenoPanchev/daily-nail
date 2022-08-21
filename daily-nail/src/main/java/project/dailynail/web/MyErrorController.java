package project.dailynail.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import project.dailynail.exceptions.ObjectNotFoundException;

@Controller
public class MyErrorController implements ErrorController {
    @RequestMapping("/error")
    public String error() {
        return "error";
    }

    @RequestMapping("/access-denied")
    public String error403() {
        return "403";
    }

    @RequestMapping("/404")
    public String error404() {
        return "404";
    }

    @RequestMapping("/throw")
    public String throwing() {
        throw new ObjectNotFoundException();
    }
}
