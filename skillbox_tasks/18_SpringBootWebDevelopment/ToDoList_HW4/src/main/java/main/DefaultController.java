package main;

import main.model.Doing;
import main.model.DoingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Controller
public class DefaultController {

    @Autowired
    DoingRepository doingRepository;

    @GetMapping("/home")
    public String index() {

        return "home";
    }
}

