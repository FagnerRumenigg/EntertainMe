package entertain_me.app.controller;

import entertain_me.app.service.LibreTranslateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "libreTranslate",  produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
@RestController
public class LibreTranslateController {

    @Autowired
    LibreTranslateService libreTranslateService;

    @GetMapping("translate")
    public void translate(){
        libreTranslateService.processTranslateRequest();
    }
}
