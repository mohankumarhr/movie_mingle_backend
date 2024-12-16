package com.movie_recomendation.movie_mingle.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class HomeController {

    @RequestMapping("/")
    public String show(){
        return "Hello World";
    }

}
