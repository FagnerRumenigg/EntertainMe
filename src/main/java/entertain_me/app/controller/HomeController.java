package entertain_me.app.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Home")
@RestController
public class HomeController {

    @RequestMapping("/")
    public String homeHello(){
        return "Pode ir jogar um aram, amigo!";
    }
}